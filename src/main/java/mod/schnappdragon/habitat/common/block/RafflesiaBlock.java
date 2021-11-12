package mod.schnappdragon.habitat.common.block;

import com.google.common.collect.Lists;
import com.mojang.math.Vector3f;
import mod.schnappdragon.habitat.common.block.state.properties.HabitatBlockStateProperties;
import mod.schnappdragon.habitat.common.block.entity.RafflesiaBlockEntity;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeBlock;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Random;

public class RafflesiaBlock extends BushBlock implements IForgeBlock, BonemealableBlock, EntityBlock {
    protected static final VoxelShape DEFAULT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    protected static final VoxelShape COOLDOWN_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    public static final BooleanProperty ON_COOLDOWN = HabitatBlockStateProperties.ON_COOLDOWN;
    public static final BooleanProperty HAS_STEW = HabitatBlockStateProperties.HAS_STEW;

    public RafflesiaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ON_COOLDOWN, false).setValue(HAS_STEW, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON_COOLDOWN, HAS_STEW);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(ON_COOLDOWN))
            return COOLDOWN_SHAPE;
        else
            return DEFAULT_SHAPE;
    }

    /*
     * Position Validity Method
     */

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).is(HabitatBlockTags.RAFFLESIA_PLANTABLE_ON);
    }

    /*
     * Tile Entity Methods
     */

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RafflesiaBlockEntity(pos, state);
    }
    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, Level worldIn, BlockPos pos, Random rand) {
        BlockEntity tile = worldIn.getBlockEntity(pos);
        if (tile instanceof RafflesiaBlockEntity && rand.nextInt(128) == 0 && !state.getValue(ON_COOLDOWN)) {
            RafflesiaBlockEntity rafflesia = (RafflesiaBlockEntity) tile;
            double X = (double) pos.getX() + 0.5D;
            double Z = (double) pos.getZ() + 0.5D;
            worldIn.addParticle(getParticle(rafflesia.Effects), X + (2 * rand.nextDouble() - 1.0F) / 3.0D, pos.getY() + 0.25F + rand.nextDouble() / 2, Z + (2 * rand.nextDouble() - 1.0F) / 3.0D, 0.0D, 0.01D, 0.0D);
        }
    }

    /*
     * Cloud and Particle Helper Methods
     */

    private void createCloud(Level worldIn, BlockPos pos, ListTag effects)
    {
        AreaEffectCloud cloud = new AreaEffectCloud(worldIn, pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        cloud.setDuration(50);
        cloud.setRadius(1.0F);
        cloud.setParticle(getParticle(effects));

        for(int i = 0; i < effects.size(); ++i) {
            int j = 160;
            CompoundTag tag = effects.getCompound(i);

            if (tag.contains("EffectDuration", 3)) {
                j = tag.getInt("EffectDuration");
            }

            MobEffect effect = MobEffect.byId(tag.getByte("EffectId"));
            if (effect != null) {
                cloud.addEffect(new MobEffectInstance(effect, j));
            }
        }

        worldIn.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_SPEW.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
        worldIn.addFreshEntity(cloud);
    }

    private ParticleOptions getParticle(ListTag effects)
    {
        Collection<MobEffectInstance> effectInstances = Lists.newArrayList();
        for(int i = 0; i < effects.size(); ++i) {
            int j = 160;
            CompoundTag tag = effects.getCompound(i);

            MobEffect effect = MobEffect.byId(tag.getByte("EffectId"));
            if (effect != null) {
                effectInstances.add(new MobEffectInstance(effect, j));
            }
        }

        int color = PotionUtils.getColor(effectInstances);
        return new DustParticleOptions(new Vector3f((float) FastColor.ARGB32.red(color) / 255, (float) FastColor.ARGB32.green(color) / 255, (float) FastColor.ARGB32.blue(color) / 255), 1.0F);
    }

    /*
     * Rafflesia Function Methods
     */

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isClientSide) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof RafflesiaBlockEntity rafflesia && !state.getValue(ON_COOLDOWN)) {
                createCloud(worldIn, pos, rafflesia.Effects);
                worldIn.setBlockAndUpdate(pos, state.setValue(ON_COOLDOWN, true).setValue(HAS_STEW, false));
                ListTag Effects = new ListTag();
                CompoundTag tag = new CompoundTag();
                tag.putByte("EffectId", (byte) 19);
                tag.putInt("EffectDuration", 240);
                Effects.add(tag);
                rafflesia.Effects = Effects;
                rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
            }
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(handIn);

        if (!worldIn.isClientSide && stack.getItem() == Items.SUSPICIOUS_STEW) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof RafflesiaBlockEntity rafflesia && !state.getValue(HAS_STEW)) {
                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains("Effects", 9)) {
                    rafflesia.Effects = tag.getList("Effects", 10);
                }
                worldIn.setBlockAndUpdate(pos, state.setValue(HAS_STEW, true));
                rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                player.setItemInHand(handIn, player.getAbilities().instabuild ? stack : new ItemStack(Items.BOWL));
                worldIn.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_SLURP.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    /*
     * Growth Methods
     */

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(ON_COOLDOWN);
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (state.getValue(ON_COOLDOWN) && random.nextInt(2) == 0)
            cooldownReset(worldIn, pos, state);
    }

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !state.getValue(HAS_STEW) || state.getValue(ON_COOLDOWN);
    }

    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.getValue(ON_COOLDOWN))
            cooldownReset(worldIn, pos, state);
        else if (!state.getValue(HAS_STEW)) {
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            for (int j = 0; j < 8; ++j) {
                blockpos$mutable.setWithOffset(pos, Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2));

                if ((worldIn.isEmptyBlock(blockpos$mutable) || worldIn.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) && worldIn.getBlockState(blockpos$mutable.below()).is(HabitatBlockTags.RAFFLESIA_PLANTABLE_ON)) {
                    worldIn.setBlock(blockpos$mutable, state, 3);
                    break;
                }
            }
        }
    }

    private void cooldownReset(ServerLevel worldIn, BlockPos pos, BlockState state) {
        worldIn.setBlockAndUpdate(pos, state.setValue(ON_COOLDOWN, false));
        worldIn.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_POP.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
    }

    /*
     * Comparator Methods
     */

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return state.getValue(HAS_STEW);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level worldIn, BlockPos pos) {
        return state.getValue(HAS_STEW) ?  1 : 0;
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DANGER_OTHER;
    }
}
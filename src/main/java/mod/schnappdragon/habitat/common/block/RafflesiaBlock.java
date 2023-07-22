package mod.schnappdragon.habitat.common.block;

import com.google.common.collect.Lists;
import mod.schnappdragon.habitat.common.block.entity.RafflesiaBlockEntity;
import mod.schnappdragon.habitat.common.block.state.properties.HabitatBlockStateProperties;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeBlock;

import javax.annotation.Nullable;
import java.util.Collection;

public class RafflesiaBlock extends BushBlock implements IForgeBlock, BonemealableBlock, EntityBlock {
    protected static final VoxelShape DEFAULT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static final VoxelShape READY_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    protected static final AABB TOUCH_AABB = new AABB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);

    public static final BooleanProperty READY = HabitatBlockStateProperties.READY;
    public static final BooleanProperty HAS_STEW = HabitatBlockStateProperties.HAS_STEW;

    public RafflesiaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(READY, false).setValue(HAS_STEW, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(READY, HAS_STEW);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(READY))
            return READY_SHAPE;
        else
            return DEFAULT_SHAPE;
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).is(HabitatBlockTags.RAFFLESIA_PLACEABLE_ON);
    }

    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState state1, boolean isMoving) {
        if (!state1.is(state.getBlock()) && world.hasNeighborSignal(pos)) {
            this.cooldownReset(world, pos, state);
        } else {
            world.scheduleTick(pos, this, getCooldown(world.getRandom()));
        }
    }

    private static int getCooldown(RandomSource rand) {
        return rand.nextInt(400, 600);
    }

    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (world.hasNeighborSignal(pos))
            this.cooldownReset(world, pos, state);
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
        if (!this.canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        } else {
            this.cooldownReset(world, pos, state);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RafflesiaBlockEntity(pos, state);
    }

    public void animateTick(BlockState state, Level worldIn, BlockPos pos, RandomSource rand) {
        if (state.getValue(READY) && rand.nextInt(8) == 0) {
            BlockEntity tile = worldIn.getBlockEntity(pos);

            if (tile instanceof RafflesiaBlockEntity rafflesia )
                worldIn.addParticle(getParticle(rafflesia.Effects), pos.getX() + 0.5D + (2 * rand.nextDouble() - 1.0F) / 3.0D, pos.getY() + 0.25F + rand.nextDouble() / 2, pos.getZ() + 0.5D + (2 * rand.nextDouble() - 1.0F) / 3.0D, rand.nextGaussian() * 0.01D, 0.002D, rand.nextGaussian() * 0.01D);
        }
    }

    private void createCloud(Level worldIn, BlockPos pos, ListTag effects, @Nullable LivingEntity owner) {
        AreaEffectCloud cloud = new AreaEffectCloud(worldIn, pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        cloud.setDuration(50);
        cloud.setRadius(1.0F);
        cloud.setParticle(getParticle(effects));
        cloud.setOwner(owner);

        for (int i = 0; i < effects.size(); ++i) {
            int j = 160;
            CompoundTag tag = effects.getCompound(i);

            if (tag.contains("EffectDuration", 3)) {
                j = tag.getInt("EffectDuration");
            }

            MobEffect effect = MobEffect.byId(tag.getByte("EffectId"));
            if (effect != null)
                cloud.addEffect(new MobEffectInstance(effect, j));
        }

        worldIn.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_SPEW.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
        worldIn.addFreshEntity(cloud);
    }

    public static ParticleOptions getParticle(ListTag effects) {
        Collection<MobEffectInstance> effectInstances = Lists.newArrayList();
        for (int i = 0; i < effects.size(); ++i) {
            CompoundTag tag = effects.getCompound(i);

            MobEffect effect = MobEffect.byId(tag.getByte("EffectId"));
            if (effect != null)
                effectInstances.add(new MobEffectInstance(effect, 160));
        }

        return new DustParticleOptions(Vec3.fromRGB24(PotionUtils.getColor(effectInstances)).toVector3f(), 1.0F);
    }

    @Override
    public void entityInside(BlockState state, Level worldin, BlockPos pos, Entity entity) {
        if (!worldin.isClientSide) {
            BlockEntity tile = worldin.getBlockEntity(pos);
            if (tile instanceof RafflesiaBlockEntity rafflesia && state.getValue(READY) && worldin.getEntities(null, TOUCH_AABB.move(pos)).contains(entity)) {
                worldin.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(state));
                Entity owner = entity instanceof Projectile projectile ? projectile.getOwner() : entity;
                createCloud(worldin, pos, rafflesia.Effects, owner instanceof LivingEntity living ? living : null);
                worldin.setBlockAndUpdate(pos, state.setValue(READY, false).setValue(HAS_STEW, false));
                worldin.scheduleTick(pos, this, getCooldown(worldin.getRandom()));
                rafflesia.Effects = getDefault();
                rafflesia.onChange(worldin, worldin.getBlockState(pos));
            }
        }

        super.entityInside(state, worldin, pos, entity);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() == Items.SUSPICIOUS_STEW && !state.getValue(HAS_STEW)) {
            if (!world.isClientSide && world.getBlockEntity(pos) instanceof RafflesiaBlockEntity rafflesia) {
                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains("Effects", 9))
                    rafflesia.Effects = tag.getList("Effects", 10);
                world.setBlockAndUpdate(pos, state.setValue(HAS_STEW, true));
                rafflesia.onChange(world, world.getBlockState(pos));
                player.setItemInHand(hand, player.getAbilities().instabuild ? stack : new ItemStack(Items.BOWL));
                world.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_SLURP.get(), SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
                ((ServerLevel) world).sendParticles(getParticle(rafflesia.Effects), pos.getX() + 0.5D + (2 * world.random.nextDouble() - 1.0F) / 3.0D, pos.getY() + 0.25F + world.random.nextDouble() / 2, pos.getZ() + 0.5D + (2 * world.random.nextDouble() - 1.0F) / 3.0D, 0, 0.0D, 0.1D, 0.0D, 1.0D);
            }
            world.gameEvent(player, GameEvent.FLUID_PLACE, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else if (stack.getItem() == Items.BOWL && state.getValue(HAS_STEW)) {
            if (!world.isClientSide && world.getBlockEntity(pos) instanceof RafflesiaBlockEntity rafflesia) {
                ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
                rafflesia.Effects.forEach(tag -> {
                    CompoundTag compound = (CompoundTag) tag;
                    SuspiciousStewItem.saveMobEffect(stew, MobEffect.byId(compound.getByte("EffectId")), compound.getInt("EffectDuration"));
                });
                world.setBlockAndUpdate(pos, state.setValue(HAS_STEW, false));
                rafflesia.Effects = getDefault();
                rafflesia.onChange(world, world.getBlockState(pos));
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, stew, false));
                world.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_FILL_BOWL.get(), SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            }
            world.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    public static ListTag getDefault() {
        ListTag Effects = new ListTag();
        CompoundTag tag = new CompoundTag();
        tag.putByte("EffectId", (byte) 19);
        tag.putInt("EffectDuration", 240);
        Effects.add(tag);
        return Effects;
    }

    public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !(state.getValue(HAS_STEW) && state.getValue(READY));
    }

    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        if (!state.getValue(READY))
            this.cooldownReset(worldIn, pos, state);
        else if (!state.getValue(HAS_STEW)) {
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            for (int j = 0; j < 8; ++j) {
                blockpos$mutable.setWithOffset(pos, Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2));

                if (worldIn.isEmptyBlock(blockpos$mutable) && worldIn.getBlockState(blockpos$mutable.below()).is(HabitatBlockTags.RAFFLESIA_PLACEABLE_ON)) {
                    worldIn.setBlock(blockpos$mutable, state, 3);
                    break;
                }
            }
        }
    }

    private void cooldownReset(Level worldIn, BlockPos pos, BlockState state) {
        worldIn.gameEvent(GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(state));
        worldIn.setBlockAndUpdate(pos, state.setValue(READY, true));
        worldIn.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_POP.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return state.getValue(HAS_STEW);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level worldIn, BlockPos pos) {
        return 1;
    }

    @Nullable
    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DANGER_OTHER;
    }
}
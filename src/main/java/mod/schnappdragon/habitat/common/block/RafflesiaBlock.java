package mod.schnappdragon.habitat.common.block;

import com.google.common.collect.Lists;
import mod.schnappdragon.habitat.common.state.properties.HabitatBlockStateProperties;
import mod.schnappdragon.habitat.common.tileentity.RafflesiaTileEntity;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeBlock;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Random;

public class RafflesiaBlock extends BushBlock implements IForgeBlock, IGrowable {
    protected static final VoxelShape DEFAULT_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    protected static final VoxelShape COOLDOWN_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    public static final BooleanProperty ON_COOLDOWN = HabitatBlockStateProperties.ON_COOLDOWN;
    public static final BooleanProperty HAS_STEW = HabitatBlockStateProperties.HAS_STEW;

    public RafflesiaBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(ON_COOLDOWN, false).with(HAS_STEW, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ON_COOLDOWN, HAS_STEW);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(ON_COOLDOWN))
            return COOLDOWN_SHAPE;
        else
            return DEFAULT_SHAPE;
    }

    /*
     * Position Validity Method
     */

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isIn(HabitatBlockTags.RAFFLESIA_PLANTABLE_ON);
    }

    /*
     * Tile Entity Methods
     */

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        return new RafflesiaTileEntity();
    }

    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof RafflesiaTileEntity && rand.nextInt(128) == 0 && !state.get(ON_COOLDOWN)) {
            RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
            double X = (double) pos.getX() + 0.5D;
            double Z = (double) pos.getZ() + 0.5D;
            worldIn.addParticle(getParticle(rafflesia.Effects), X + (2 * rand.nextDouble() - 1.0F) / 3.0D, pos.getY() + 0.25F + rand.nextDouble() / 2, Z + (2 * rand.nextDouble() - 1.0F) / 3.0D, 0.0D, 0.01D, 0.0D);
        }
    }

    /*
     * Cloud and Particle Helper Methods
     */

    private void createCloud(World worldIn, BlockPos pos, BlockState state, ListNBT effects)
    {
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        cloud.setDuration(50);
        cloud.setRadius(1.0F);
        cloud.setParticleData(getParticle(effects));

        for(int i = 0; i < effects.size(); ++i) {
            int j = 160;
            CompoundNBT tag = effects.getCompound(i);

            if (tag.contains("EffectDuration", 3)) {
                j = tag.getInt("EffectDuration");
            }

            Effect effect = Effect.get(tag.getByte("EffectId"));
            if (effect != null) {
                cloud.addEffect(new EffectInstance(effect, j));
            }
        }

        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_RAFFLESIA_SPEW.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
        worldIn.addEntity(cloud);
    }

    private IParticleData getParticle(ListNBT effects)
    {
        Collection<EffectInstance> effectInstances = Lists.newArrayList();
        for(int i = 0; i < effects.size(); ++i) {
            int j = 160;
            CompoundNBT tag = effects.getCompound(i);

            Effect effect = Effect.get(tag.getByte("EffectId"));
            if (effect != null) {
                effectInstances.add(new EffectInstance(effect, j));
            }
        }

        int color = PotionUtils.getPotionColorFromEffectList(effectInstances);
        return new RedstoneParticleData((float) ColorHelper.PackedColor.getRed(color) / 255, (float) ColorHelper.PackedColor.getGreen(color) / 255, (float) ColorHelper.PackedColor.getBlue(color) / 255, 1.0F);
    }

    /*
     * Rafflesia Function Methods
     */

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote && (entityIn instanceof LivingEntity || entityIn instanceof ProjectileEntity || entityIn instanceof FallingBlockEntity || entityIn instanceof BoatEntity || entityIn instanceof TNTEntity || entityIn instanceof AbstractMinecartEntity)) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof RafflesiaTileEntity && !state.get(ON_COOLDOWN)) {
                RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
                createCloud(worldIn, pos, state, rafflesia.Effects);
                worldIn.setBlockState(pos, state.with(ON_COOLDOWN, true).with(HAS_STEW, false));
                ListNBT Effects = new ListNBT();
                CompoundNBT tag = new CompoundNBT();
                tag.putByte("EffectId", (byte) 19);
                tag.putInt("EffectDuration", 240);
                Effects.add(tag);
                rafflesia.Effects = Effects;
                rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
            }
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);

        if (!worldIn.isRemote && stack.getItem() == Items.SUSPICIOUS_STEW) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof RafflesiaTileEntity && !state.get(HAS_STEW)) {
                RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
                CompoundNBT tag = stack.getTag();
                if (tag != null && tag.contains("Effects", 9)) {
                    rafflesia.Effects = tag.getList("Effects", 10);
                }
                worldIn.setBlockState(pos, state.with(HAS_STEW, true));
                rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                player.setHeldItem(handIn, player.abilities.isCreativeMode ? stack : new ItemStack(Items.BOWL));
                worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_RAFFLESIA_SLURP.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return state.get(ON_COOLDOWN);
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (state.get(ON_COOLDOWN) && random.nextInt(2) == 0)
            cooldownReset(worldIn, pos, state);
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(HAS_STEW) || state.get(ON_COOLDOWN);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(ON_COOLDOWN))
            cooldownReset(worldIn, pos, state);
        else if (!state.get(HAS_STEW)) {
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for (int j = 0; j < 8; ++j) {
                blockpos$mutable.setAndOffset(pos, MathHelper.nextInt(rand, 1, 2) - MathHelper.nextInt(rand, 1, 2), MathHelper.nextInt(rand, 1, 2) - MathHelper.nextInt(rand, 1, 2), MathHelper.nextInt(rand, 1, 2) - MathHelper.nextInt(rand, 1, 2));

                if ((worldIn.isAirBlock(blockpos$mutable) || worldIn.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) && worldIn.getBlockState(blockpos$mutable.down()).isIn(HabitatBlockTags.RAFFLESIA_PLANTABLE_ON)) {
                    worldIn.setBlockState(blockpos$mutable, state, 3);
                    break;
                }
            }
        }
    }

    private void cooldownReset(ServerWorld worldIn, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state.with(ON_COOLDOWN, false));
        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_RAFFLESIA_POP.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
    }

    /*
     * Block Flammability Methods
     */

    @Override
    public int getFlammability(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        return 60;
    }

    /*
     * Comparator Methods
     */

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return state.get(HAS_STEW);
    }

    @Override
    public int getComparatorInputOverride(BlockState state, World worldIn, BlockPos pos) {
        return state.get(HAS_STEW) ?  1 : 0;
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.DANGER_OTHER;
    }
}
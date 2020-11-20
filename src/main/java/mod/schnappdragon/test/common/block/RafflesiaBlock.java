package mod.schnappdragon.test.common.block;

import com.google.common.collect.Lists;
import mod.schnappdragon.test.core.registry.ModBlocks;
import mod.schnappdragon.test.common.tileentity.RafflesiaTileEntity;
import mod.schnappdragon.test.core.registry.ModItems;
import mod.schnappdragon.test.core.registry.ModSoundEvents;
import mod.schnappdragon.test.core.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.extensions.IForgeBlock;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.Color;
import java.util.Collection;
import java.util.Random;

public class RafflesiaBlock extends BushBlock implements IForgeBlock, IGrowable {
    protected static final VoxelShape AGE_0_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D); // PLACEHOLDER SHAPE
    protected static final VoxelShape AGE_1_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D); // PLACEHOLDER SHAPE
    protected static final VoxelShape DEFAULT_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    protected static final VoxelShape COOLDOWN_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_2;
    public static final BooleanProperty COOLDOWN = BooleanProperty.create("cooldown");
    public static final BooleanProperty STEW = BooleanProperty.create("stew");
    public static final BooleanProperty POLLINATED = BooleanProperty.create("pollinated");


    public RafflesiaBlock() {
        super(AbstractBlock.Properties
                .create(Material.PLANTS)
                .hardnessAndResistance(0.0F, 0.0F)
                .sound(SoundType.PLANT)
                .doesNotBlockMovement()
                .notSolid()
                .setAllowsSpawn((a,b,c,d) -> false));

        this.setDefaultState(
                this.stateContainer.getBaseState()
                        .with(HORIZONTAL_FACING, Direction.NORTH)
                        .with(AGE, 0)
                        .with(COOLDOWN, false)
                        .with(STEW, false)
                        .with(POLLINATED, false)
        );
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, AGE, COOLDOWN, STEW, POLLINATED);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        if(state.get(AGE) == 2) {
            if (state.get(COOLDOWN))
                return COOLDOWN_SHAPE;
            else
                return DEFAULT_SHAPE;
        }
        else if (state.get(AGE) == 1)
            return AGE_1_SHAPE;
        else
            return AGE_0_SHAPE;
    }

    @ParametersAreNonnullByDefault
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    /*
     * Position Validity Methods
     */

    @ParametersAreNonnullByDefault
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if(state.get(AGE) == 2) {
            return hasValidSurroundings(state, worldIn, pos);
        }

        BlockPos blockpos = pos.down();
        return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    private boolean hasValidSurroundings(BlockState state, IWorldReader worldIn, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
            Material material = blockstate.getMaterial();
            if (material.isSolid() || worldIn.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA) || blockstate.getBlock().matchesBlock(ModBlocks.RAFFLESIA_BLOCK.get())) {
                return false;
            }
        }

        BlockPos blockpos = pos.down();
        return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    @ParametersAreNonnullByDefault
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(ModTags.RAFFLESIA_PLANTABLE_ON);
    }

    /*
     * Tile Entity Methods
     */

    public boolean hasTileEntity(BlockState state) {
        return state.get(AGE) == 2;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new RafflesiaTileEntity();
    }

    /*
     * Particle Animation Method
     */

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5;
        double d1 = (double) pos.getZ() + 0.5;

        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof RafflesiaTileEntity) {
            RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
            if (rand.nextInt(128) == 0 && !stateIn.get(COOLDOWN) && stateIn.get(AGE) == 2) {
                worldIn.addParticle(getParticle(rafflesia.Effects), d0 + rand.nextDouble() / 3.0D, (double) pos.getY() + (0.8D - rand.nextDouble()), d1 + rand.nextDouble() / 3.0D, 0.0D, 0.05D, 0.0D);
            }
            if (stateIn.get(POLLINATED)) {
                worldIn.addParticle(getParticle(rafflesia.Effects), d0 + rand.nextDouble() / 3.0D, (double) pos.getY() + (0.8D - rand.nextDouble()), d1 + rand.nextDouble() / 3.0D, 0.0D, 0.05D, 0.0D);
            }
        }
    }

    /*
     * Cloud and Particle Helper Methods
     */

    private void createCloud(World worldIn, BlockPos pos, ListNBT effects)
    {
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5);
        cloud.setDuration(50);
        cloud.setRadius(1.6F);
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

        worldIn.playSound(null, pos, ModSoundEvents.RAFFLESIA_CLOUD.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        worldIn.addEntity(cloud);
        checkPollinationConditions(worldIn, pos);
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

        Color color = new Color(PotionUtils.getPotionColorFromEffectList(effectInstances));
        return new RedstoneParticleData((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 1.0F);
    }

    /*
     * Rafflesia Function Methods
     */

    @Override
    @ParametersAreNonnullByDefault
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote && (entityIn instanceof LivingEntity || entityIn instanceof ProjectileEntity) && state.get(AGE) == 2) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof RafflesiaTileEntity && !state.get(COOLDOWN) && !state.get(POLLINATED)) {
                RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
                createCloud(worldIn, pos, rafflesia.Effects);
                worldIn.setBlockState(pos, state.with(COOLDOWN, true).with(STEW, false));
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
    @ParametersAreNonnullByDefault
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);
        if (!(state.get(AGE) == 2) && stack.getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        }
        if (!worldIn.isRemote && stack.getItem() instanceof SuspiciousStewItem && state.get(AGE) == 2) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof RafflesiaTileEntity && !state.get(STEW) && !state.get(POLLINATED)) {
                RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
                CompoundNBT tag = stack.getTag();
                if (tag != null && tag.contains("Effects", 9)) {
                    rafflesia.Effects = tag.getList("Effects", 10);
                }
                worldIn.setBlockState(pos, state.with(STEW, true));
                rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                player.setHeldItem(handIn, player.abilities.isCreativeMode ? stack : new ItemStack(Items.BOWL));
                worldIn.playSound(null, pos, ModSoundEvents.RAFFLESIA_STEW.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    /*
     * Pollination Methods
     */

    private void checkPollinationConditions(World worldIn, BlockPos pos) {
        BlockPos A = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
        BlockPos B = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() - 1);
        BlockPos C = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() + 1);
        BlockPos D = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
        BlockState Astate = worldIn.getBlockState(A);
        BlockState Bstate = worldIn.getBlockState(B);
        BlockState Cstate = worldIn.getBlockState(C);
        BlockState Dstate = worldIn.getBlockState(D);
        if (Astate.getBlock() instanceof RafflesiaBlock)
        {
            attemptPollinate(worldIn, Astate, A);
        }
        if (Bstate.getBlock() instanceof RafflesiaBlock)
        {
            attemptPollinate(worldIn, Bstate, B);
        }
        if (Cstate.getBlock() instanceof RafflesiaBlock)
        {
            attemptPollinate(worldIn, Cstate, C);
        }
        if (Dstate.getBlock() instanceof RafflesiaBlock)
        {
            attemptPollinate(worldIn, Dstate, D);
        }
    }

    private void attemptPollinate(World worldIn, BlockState state, BlockPos pos) {
        Random rand = new Random();
        if (!state.get(COOLDOWN) && !state.get(STEW) && rand.nextInt(10) == 0) {
            worldIn.setBlockState(pos, state.with(POLLINATED, true).with(COOLDOWN, true));
        }
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return state.get(AGE) < 2 || state.get(COOLDOWN);
    }

    @ParametersAreNonnullByDefault
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (state.get(AGE) < 2 && isValidGrowthPosition(state, worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(5) == 0)) {
            worldIn.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
        else if (state.get(COOLDOWN) && ForgeHooks.onCropsGrowPre(worldIn, pos, state,true)) {
            cooldownReset(worldIn, random, pos, state);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    @ParametersAreNonnullByDefault
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 2 && isValidGrowthPosition(state, (World) worldIn, pos) || state.get(COOLDOWN);
    }

    @ParametersAreNonnullByDefault
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @ParametersAreNonnullByDefault
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(COOLDOWN) && state.get(AGE) == 2)
            cooldownReset(worldIn, rand, pos, state);
        else {
            worldIn.setBlockState(pos, state.with(AGE, Math.min(2, state.get(AGE) + 1)), 2);
        }
    }

    private void cooldownReset(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(POLLINATED)) {
            ItemEntity item = new ItemEntity(worldIn, pos.getX() + 0.5F, pos.getY() + 0.25F, pos.getZ() + 0.5F, new ItemStack(ModItems.RAFFLESIA_SEED_POD.get()));
            item.setDefaultPickupDelay();
            worldIn.addEntity(item);
        }

        worldIn.setBlockState(pos, state.with(COOLDOWN, false).with(POLLINATED, false));
        worldIn.playSound(null, pos, ModSoundEvents.RAFFLESIA_POP.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private boolean isValidGrowthPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(AGE) == 1) {
            return hasValidSurroundings(state, worldIn, pos);
        }

        return true;
    }

    /*
     * Block Flammability Methods
     */

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 60;
    }
}
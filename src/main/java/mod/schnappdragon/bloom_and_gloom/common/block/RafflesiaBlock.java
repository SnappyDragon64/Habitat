package mod.schnappdragon.bloom_and_gloom.common.block;

import com.google.common.collect.Lists;
import mod.schnappdragon.bloom_and_gloom.common.state.properties.BGBlockStateProperties;
import mod.schnappdragon.bloom_and_gloom.common.tileentity.RafflesiaTileEntity;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGSoundEvents;
import mod.schnappdragon.bloom_and_gloom.core.misc.BGTags;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.extensions.IForgeBlock;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.Collection;
import java.util.Random;

public class RafflesiaBlock extends BushBlock implements IForgeBlock, IGrowable {
    protected static final VoxelShape AGE_0_SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    protected static final VoxelShape AGE_1_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    protected static final VoxelShape DEFAULT_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    protected static final VoxelShape COOLDOWN_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_2;
    public static final BooleanProperty ON_COOLDOWN = BGBlockStateProperties.ON_COOLDOWN;
    public static final BooleanProperty HAS_STEW = BGBlockStateProperties.HAS_STEW;
    public static final BooleanProperty POLLINATED = BGBlockStateProperties.POLLINATED;


    public RafflesiaBlock() {
        super(AbstractBlock.Properties
                .create(Material.PLANTS)
                .zeroHardnessAndResistance()
                .sound(SoundType.PLANT)
                .doesNotBlockMovement()
                .notSolid()
                .setAllowsSpawn((a,b,c,d) -> false)
        );

        this.setDefaultState(
                this.stateContainer.getBaseState()
                        .with(AGE, 0)
                        .with(ON_COOLDOWN, false)
                        .with(HAS_STEW, false)
                        .with(POLLINATED, false)
        );
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, ON_COOLDOWN, HAS_STEW, POLLINATED);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(AGE) == 0)
            return AGE_0_SHAPE;
        else if (state.get(AGE) == 1)
            return AGE_1_SHAPE;
        else {
            if (state.get(ON_COOLDOWN))
                return COOLDOWN_SHAPE;
            else
                return DEFAULT_SHAPE;
        }
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }

    /*
     * Position Validity Method
     */

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return isValidGround(state, worldIn, pos.down());
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).isIn(BGTags.RAFFLESIA_PLANTABLE_ON);
    }

    /*
     * Tile Entity Methods
     */

    public boolean hasTileEntity(BlockState state) {
        return state.get(AGE) == 2;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        return new RafflesiaTileEntity();
    }

    /*
     * Particle Animation Method
     */

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5;
        double d1 = (double) pos.getZ() + 0.5;

        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof RafflesiaTileEntity) {
            RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
            if (rand.nextInt(128) == 0 && !state.get(ON_COOLDOWN) && state.get(AGE) == 2) {
                worldIn.addParticle(getParticle(rafflesia.Effects), d0 + rand.nextDouble() / 3.0D, (double) pos.getY() + (0.8D - rand.nextDouble()), d1 + rand.nextDouble() / 3.0D, 0.0D, 0.05D, 0.0D);
            }
            if (state.get(POLLINATED)) {
                worldIn.addParticle(getParticle(rafflesia.Effects), d0 + rand.nextDouble() / 3.0D, (double) pos.getY() + (0.8D - rand.nextDouble()), d1 + rand.nextDouble() / 3.0D, 0.0D, 0.05D, 0.0D);
            }
        }
    }

    /*
     * Cloud and Particle Helper Methods
     */

    private void createCloud(World worldIn, BlockPos pos, BlockState state, ListNBT effects)
    {
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5);
        cloud.setDuration(50);
        cloud.setRadius(1F);
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

        worldIn.playSound(null, pos, BGSoundEvents.BLOCK_RAFFLESIA_SPEW.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
        worldIn.addEntity(cloud);
        if (!state.get(HAS_STEW))
            attemptPollination(worldIn, pos);
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
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote && (entityIn instanceof LivingEntity || entityIn instanceof ProjectileEntity || entityIn instanceof FallingBlockEntity || entityIn instanceof BoatEntity || entityIn instanceof TNTEntity || entityIn instanceof AbstractMinecartEntity) && state.get(AGE) == 2) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof RafflesiaTileEntity && !state.get(ON_COOLDOWN) && !state.get(POLLINATED)) {
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
        if (!(state.get(AGE) == 2) && stack.getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        }
        if (!worldIn.isRemote && stack.getItem() == Items.SUSPICIOUS_STEW && state.get(AGE) == 2) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof RafflesiaTileEntity && !state.get(HAS_STEW) && !state.get(POLLINATED)) {
                RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
                CompoundNBT tag = stack.getTag();
                if (tag != null && tag.contains("Effects", 9)) {
                    rafflesia.Effects = tag.getList("Effects", 10);
                }
                worldIn.setBlockState(pos, state.with(HAS_STEW, true));
                rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                player.setHeldItem(handIn, player.abilities.isCreativeMode ? stack : new ItemStack(Items.BOWL));
                worldIn.playSound(null, pos, BGSoundEvents.BLOCK_RAFFLESIA_SLURP.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    /*
     * Pollination Method
     */

    private void attemptPollination(World worldIn, BlockPos pos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = worldIn.getBlockState(pos.offset(direction));
            if (state.getBlock().matchesBlock(BGBlocks.RAFFLESIA.get())) {
                Random rand = new Random();
                if (!state.get(HAS_STEW) && rand.nextInt(10) == 0) {
                    worldIn.setBlockState(pos.offset(direction), state.with(POLLINATED, true).with(ON_COOLDOWN, true));
                }
            }
        }
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return state.get(AGE) < 2 || state.get(ON_COOLDOWN);
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (state.get(AGE) < 2 && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(5) == 0)) {
            worldIn.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
        else if (state.get(ON_COOLDOWN) && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(2) == 0)) {
            cooldownReset(worldIn, random, pos, state);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 2 || state.get(ON_COOLDOWN);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(ON_COOLDOWN) && state.get(AGE) == 2)
            cooldownReset(worldIn, rand, pos, state);
        else {
            worldIn.setBlockState(pos, state.with(AGE, Math.min(2, state.get(AGE) + 1)), 2);
        }
    }

    private void cooldownReset(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(POLLINATED)) {
            ItemEntity item = new ItemEntity(worldIn, pos.getX() + 0.5F, pos.getY() + 0.25F, pos.getZ() + 0.5F, new ItemStack(BGItems.RAFFLESIA_SEED.get()));
            item.setDefaultPickupDelay();
            worldIn.addEntity(item);
        }

        worldIn.setBlockState(pos, state.with(ON_COOLDOWN, false).with(POLLINATED, false));
        worldIn.playSound(null, pos, BGSoundEvents.BLOCK_RAFFLESIA_POP.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
    }

    /*
     * Plant Method
     */

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return getDefaultState();
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.get("jungle");
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
     * Pathfinding Method
     */

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return (((entity instanceof CreatureEntity) && !(entity instanceof MonsterEntity) && !(entity instanceof WaterMobEntity)) || (entity instanceof AbstractRaiderEntity) || (entity instanceof PiglinEntity) || (entity instanceof EndermanEntity)) ? PathNodeType.DAMAGE_OTHER : PathNodeType.WALKABLE;
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
}
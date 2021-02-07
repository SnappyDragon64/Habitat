package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.common.misc.BallCactusColor;
import mod.schnappdragon.bloom_and_gloom.common.state.properties.BGBlockStateProperties;
import mod.schnappdragon.bloom_and_gloom.core.tags.BGBlockTags;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGSoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
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
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class BallCactusBlock extends BushBlock implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    public static final BooleanProperty FLOWERING = BGBlockStateProperties.FLOWERING;
    private final BallCactusColor color;

    public BallCactusBlock(BallCactusColor color, Properties properties) {
        super(properties);
        this.color = color;
        this.setDefaultState(this.stateContainer.getBaseState().with(FLOWERING, false));
    }

    public BallCactusColor getColor() {
        return color;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FLOWERING);
    }

    /*
     * Position Validity Methods
     */

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return isValidGround(state, worldIn, pos.down());
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).isIn(BGBlockTags.BALL_CACTUS_PLANTABLE_ON);
    }

    /*
     * Shearing Method
     */

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(FLOWERING) && player.getHeldItem(handIn).getItem() instanceof ShearsItem) {
            spawnAsEntity(worldIn, pos, new ItemStack(color.getFlower()));
            player.getHeldItem(handIn).damageItem(1, player, (playerIn) -> {
                playerIn.sendBreakAnimation(handIn);
            });
            worldIn.setBlockState(pos, color.getBallCactus().getDefaultState(), 2);
            worldIn.playSound(null, pos, BGSoundEvents.BLOCK_FLOWERING_BALL_CACTUS_SHEAR.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return !state.get(FLOWERING);
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!state.get(FLOWERING) && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(10) == 0)) {
            worldIn.setBlockState(pos, color.getBallCactus().getDefaultState().with(FLOWERING, true));
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(FLOWERING);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return !state.get(FLOWERING);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, color.getBallCactus().getDefaultState().with(FLOWERING, true));
    }

    /*
     * Entity Collision Method
     */

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.getType() != EntityType.BEE) {
            entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.DAMAGE_CACTUS;
    }
}

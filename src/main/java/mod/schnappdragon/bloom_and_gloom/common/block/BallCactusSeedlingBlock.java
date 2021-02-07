package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.common.misc.BallCactusColor;
import mod.schnappdragon.bloom_and_gloom.common.state.properties.BGBlockStateProperties;
import mod.schnappdragon.bloom_and_gloom.core.misc.BGBlockTags;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGEffects;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class BallCactusSeedlingBlock extends FlowerBlock implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D);
    public static final BooleanProperty GERMINATED = BGBlockStateProperties.GERMINATED;
    private final BallCactusColor color;

    public BallCactusSeedlingBlock(BallCactusColor color, Properties properties) {
        super(Effects.REGENERATION, 5, properties);
        this.color = color;
        this.setDefaultState(this.stateContainer.getBaseState().with(GERMINATED, false));
    }

    public BallCactusColor getColor() {
        return color;
    }

    @Override
    public Effect getStewEffect() {
        return BGEffects.PRICKLING.get();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GERMINATED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(GERMINATED) ? VoxelShapes.empty() : SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.get(GERMINATED) ? VoxelShapes.empty() : SHAPE;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
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
        return worldIn.getBlockState(pos).isIn(BGBlockTags.BALL_CACTUS_FLOWER_PLACEABLE_ON) || worldIn.getBlockState(pos).isIn(BGBlockTags.BALL_CACTUS_PLANTABLE_ON);
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (canGrow(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(10) == 0)) {
            if (state.get(GERMINATED))
                worldIn.setBlockState(pos, color.getBallCactus().getDefaultState());
            else
                worldIn.setBlockState(pos, state.with(GERMINATED, true));
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return canGrow((World) worldIn, pos);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return canGrow(worldIn, pos);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(GERMINATED))
            worldIn.setBlockState(pos, color.getBallCactus().getDefaultState());
        else
            worldIn.setBlockState(pos, state.with(GERMINATED, true));
    }

    private boolean canGrow(World worldIn, BlockPos pos) {
        return !worldIn.getBlockState(pos.down()).isIn(BGBlockTags.BALL_CACTUS_FLOWER_PLACEABLE_ON);
    }

    /*
     * Entity Collision Method
     */

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.getType() != EntityType.BEE) {
            if (state.get(BallCactusSeedlingBlock.GERMINATED))
                entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return state.get(GERMINATED) ? PathNodeType.DAMAGE_CACTUS : null;
    }
}
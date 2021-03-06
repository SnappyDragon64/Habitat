package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.misc.BallCactusColor;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class BallCactusFlowerBlock extends HabitatFlowerBlock implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D);
    private final BallCactusColor color;

    public BallCactusFlowerBlock(BallCactusColor color, Supplier<Effect> stewEffect, int stewEffectDuration, Properties properties) {
        super(stewEffect, stewEffectDuration, properties);
        this.color = color;
    }

    public BallCactusColor getColor() {
        return color;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    /*
     * Position Validity Method
     */

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isIn(HabitatBlockTags.BALL_CACTUS_FLOWER_PLACEABLE_ON) || worldIn.getBlockState(pos.down()).isIn(HabitatBlockTags.BALL_CACTUS_PLANTABLE_ON);
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (canGrow(worldIn, pos) && random.nextInt(10) == 0)
            worldIn.setBlockState(pos, color.getGrowingBallCactus().getDefaultState());
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return canGrow((World) worldIn, pos);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return canGrow(worldIn, pos);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, color.getGrowingBallCactus().getDefaultState());
    }

    private boolean canGrow(World worldIn, BlockPos pos) {
        return !worldIn.getBlockState(pos.down()).isIn(HabitatBlockTags.BALL_CACTUS_FLOWER_PLACEABLE_ON);
    }
}

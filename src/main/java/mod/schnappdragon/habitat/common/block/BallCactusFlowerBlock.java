package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

import java.util.function.Supplier;

public class BallCactusFlowerBlock extends HabitatFlowerBlock implements BonemealableBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D);
    private final BallCactusColor color;

    public BallCactusFlowerBlock(BallCactusColor colorIn, Supplier<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
        super(stewEffect, stewEffectDuration, properties);
        this.color = colorIn;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.0F;
    }

    /*
     * Position Validity Method
     */

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).is(HabitatBlockTags.BALL_CACTUS_FLOWER_PLACEABLE) || worldIn.getBlockState(pos.below()).is(HabitatBlockTags.BALL_CACTUS_GROWS_ON);
    }

    /*
     * Growth Methods
     */

    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (canGrow(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(10) == 0)) {
            worldIn.setBlockAndUpdate(pos, color.getGrowingBallCactus().defaultBlockState());
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return canGrow((Level) worldIn, pos);
    }

    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return canGrow(worldIn, pos);
    }

    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        worldIn.setBlockAndUpdate(pos, (rand.nextBoolean() ? color.getGrowingBallCactus() : color.getBallCactus()).defaultBlockState());
    }

    public boolean canGrow(Level worldIn, BlockPos pos) {
        return !worldIn.getBlockState(pos.below()).is(HabitatBlockTags.BALL_CACTUS_FLOWER_PLACEABLE);
    }
}

package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.misc.BallCactusColor;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class BallCactusBlock extends AbstractBallCactusBlock implements IGrowable {
    public BallCactusBlock(BallCactusColor color, Properties properties) {
        super(color, properties);
    }

    /*
     * Flower Adding Method
     */

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.getHeldItem(handIn).getItem() == getColor().getFlower()) {
            if (!player.abilities.isCreativeMode)
                player.getHeldItem(handIn).shrink(1);
            worldIn.setBlockState(pos, getColor().getFloweringBallCactus().getDefaultState(), 2);
            worldIn.playSound(null, pos, SoundType.PLANT.getPlaceSound(), SoundCategory.BLOCKS, SoundType.PLANT.getVolume() + 1.0F / 2.0F, SoundType.PLANT.getPitch() * 0.8F);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0)
            worldIn.setBlockState(pos, getColor().getFloweringBallCactus().getDefaultState());
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, getColor().getFloweringBallCactus().getDefaultState());
    }
}

package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.misc.BallCactusColor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class BallCactusBlock extends AbstractBallCactusBlock implements BonemealableBlock {
    public BallCactusBlock(BallCactusColor colorIn, Properties properties) {
        super(colorIn, properties);
    }

    /*
     * Flower Adding Method
     */

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() == getColor().getFlower()) {
            if (!player.getAbilities().instabuild)
                player.getItemInHand(handIn).shrink(1);
            worldIn.setBlock(pos, getColor().getFloweringBallCactus().defaultBlockState(), 2);
            worldIn.playSound(null, pos, SoundType.GRASS.getPlaceSound(), SoundSource.BLOCKS, SoundType.GRASS.getVolume() + 1.0F / 2.0F, SoundType.GRASS.getPitch() * 0.8F);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    /*
     * Growth Methods
     */

    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0)
            worldIn.setBlockAndUpdate(pos, getColor().getFloweringBallCactus().defaultBlockState());
    }

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockAndUpdate(pos, getColor().getFloweringBallCactus().defaultBlockState());
    }
}

package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FloweringBallCactusBlock extends AbstractBallCactusBlock {
    public FloweringBallCactusBlock(BallCactusColor colorIn, Properties properties) {
        super(colorIn, properties);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return new ItemStack(getColor().getBallCactus());
    }

    /*
     * Shearing Method
     */

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() instanceof ShearsItem) {
            popResource(worldIn, pos, new ItemStack(getColor().getFlower()));
            player.getItemInHand(handIn).hurtAndBreak(1, player, (playerIn) -> {
                playerIn.broadcastBreakEvent(handIn);
            });
            worldIn.setBlock(pos, getColor().getBallCactus().defaultBlockState(), 2);
            worldIn.playSound(null, pos, HabitatSoundEvents.FLOWERING_BALL_CACTUS_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}

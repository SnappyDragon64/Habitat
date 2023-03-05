package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolActions;

public class HugeFloweringBallCactusBlock extends HugeBallCactusBlock {
    private final BallCactusColor color;

    public HugeFloweringBallCactusBlock(BallCactusColor colorIn, Properties properties) {
        super(properties);
        this.color = colorIn;
    }

    /*
     * Shearing Method
     */

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).canPerformAction(ToolActions.SHEARS_HARVEST)) {
            int i = 2 + worldIn.random.nextInt(2);
            popResource(worldIn, pos, new ItemStack(color.getFlower(), i));
            player.getItemInHand(handIn).hurtAndBreak(1, player, (playerIn) -> {
                playerIn.broadcastBreakEvent(handIn);
            });
            worldIn.gameEvent(player, GameEvent.SHEAR, pos);
            worldIn.setBlock(pos, HabitatBlocks.BALL_CACTUS_BLOCK.get().defaultBlockState(), 2);
            worldIn.playSound(null, pos, HabitatSoundEvents.FLOWERING_BALL_CACTUS_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}

package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.common.misc.BallCactusColor;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGSoundEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class FloweringBallCactusBlock extends AbstractBallCactusBlock {
    public FloweringBallCactusBlock(BallCactusColor color, AbstractBlock.Properties properties) {
        super(color, properties);
    }

    /*
     * Shearing Method
     */

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.getHeldItem(handIn).getItem() instanceof ShearsItem) {
            spawnAsEntity(worldIn, pos, new ItemStack(color.getFlower(), 1));
            player.getHeldItem(handIn).damageItem(1, player, (playerIn) -> {
                playerIn.sendBreakAnimation(handIn);
            });
            worldIn.setBlockState(pos, color.getBallCactus().getDefaultState(), 2);
            worldIn.playSound(null, pos, BGSoundEvents.BLOCK_KABLOOM_BUSH_SHEAR.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
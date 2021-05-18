package mod.schnappdragon.habitat.common.block.wood;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class LogBlock extends RotatedPillarBlock {
        private final Supplier<Block> strippedBlock;

    public LogBlock(Supplier<Block> strippedBlock, Properties properties) {
        super(properties);
        this.strippedBlock = strippedBlock;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        if (toolType == ToolType.AXE && strippedBlock.get() instanceof RotatedPillarBlock)
            return strippedBlock.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS));
        return super.getToolModifiedState(state, world, pos, player, stack, toolType);
    }
}

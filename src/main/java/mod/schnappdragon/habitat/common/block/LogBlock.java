package mod.schnappdragon.habitat.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

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
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (ToolActions.AXE_STRIP.equals(toolAction) && strippedBlock.get() instanceof RotatedPillarBlock)
            return strippedBlock.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}

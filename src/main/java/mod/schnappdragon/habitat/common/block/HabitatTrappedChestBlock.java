package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.misc.ChestVariant;
import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HabitatTrappedChestBlock extends ChestBlock implements IChestVariant {
    private final ChestVariant variant;

    public HabitatTrappedChestBlock(ChestVariant variantIn, Properties properties) {
        super(properties, HabitatBlockEntityTypes.TRAPPED_CHEST::get);
        this.variant = variantIn;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return HabitatBlockEntityTypes.TRAPPED_CHEST.get().create(pos, state);
    }

    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    public boolean isSignalSource(BlockState state) {
        return true;
    }

    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(world, pos), 0, 15);
    }

    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return direction == Direction.UP ? state.getSignal(world, pos, direction) : 0;
    }

    @Override
    public ChestVariant getVariant() {
        return variant;
    }
}
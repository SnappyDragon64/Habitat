package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HabitatChestBlock extends ChestBlock implements VariantChest {
    private final ChestVariant variant;

    public HabitatChestBlock(ChestVariant variantIn, Properties properties) {
        super(properties, HabitatBlockEntityTypes.CHEST::get);
        this.variant = variantIn;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return HabitatBlockEntityTypes.CHEST.get().create(pos, state);
    }

    @Override
    public ChestVariant getVariant() {
        return variant;
    }
}
package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.misc.ChestVariant;
import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;

public class HabitatChestBlock extends ChestBlock implements IChestVariant {
    private final ChestVariant variant;

    public HabitatChestBlock(ChestVariant variantIn, Properties properties) {
        super(properties, HabitatBlockEntityTypes.CHEST::get);
        this.variant = variantIn;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return HabitatBlockEntityTypes.CHEST.get().create();
    }

    @Override
    public ChestVariant getVariant() {
        return variant;
    }
}
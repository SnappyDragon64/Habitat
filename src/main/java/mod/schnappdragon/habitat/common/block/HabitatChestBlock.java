package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.misc.ChestVariant;
import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HabitatChestBlock extends ChestBlock implements IChestVariant {
    private final ChestVariant variant;

    public HabitatChestBlock(ChestVariant variantIn, Properties properties) {
        super(properties, HabitatTileEntityTypes.CHEST::get);
        this.variant = variantIn;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return HabitatTileEntityTypes.CHEST.get().create();
    }

    @Override
    public ChestVariant getVariant() {
        return variant;
    }
}
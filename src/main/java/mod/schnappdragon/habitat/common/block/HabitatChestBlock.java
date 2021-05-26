package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.misc.ChestVariant;
import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

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
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return HabitatTileEntityTypes.CHEST.get().create();
    }

    @Override
    public ChestVariant getVariant() {
        return variant;
    }
}
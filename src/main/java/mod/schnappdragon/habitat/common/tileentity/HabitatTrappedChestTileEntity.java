package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.tileentity.TileEntityType;

public class HabitatTrappedChestTileEntity extends HabitatChestTileEntity {
    public HabitatTrappedChestTileEntity() {
        super(HabitatTileEntityTypes.TRAPPED_CHEST.get());
    }

    protected void signalOpenCount() {
        super.signalOpenCount();
        this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
    }
}
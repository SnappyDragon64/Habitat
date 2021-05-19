package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.tileentity.TileEntityType;

public class HabitatTrappedChestTileEntity extends HabitatChestTileEntity {
    public HabitatTrappedChestTileEntity() {
        super(HabitatTileEntityTypes.TRAPPED_CHEST.get());
    }

    protected void onOpenOrClose() {
        super.onOpenOrClose();
        this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockState().getBlock());
    }
}
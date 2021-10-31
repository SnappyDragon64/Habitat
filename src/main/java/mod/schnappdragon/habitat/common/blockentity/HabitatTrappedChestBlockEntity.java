package mod.schnappdragon.habitat.common.blockentity;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.tileentity.TileEntityType;

public class HabitatTrappedChestBlockEntity extends HabitatChestBlockEntity {
    public HabitatTrappedChestBlockEntity() {
        super(HabitatBlockEntityTypes.TRAPPED_CHEST.get());
    }

    protected void signalOpenCount() {
        super.signalOpenCount();
        this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
    }
}
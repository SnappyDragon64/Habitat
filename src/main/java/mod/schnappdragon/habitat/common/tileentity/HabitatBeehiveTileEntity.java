package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class HabitatBeehiveTileEntity extends BeehiveTileEntity {
    @Override
    public TileEntityType<?> getType() {
        return HabitatTileEntityTypes.BEEHIVE.get();
    }
}
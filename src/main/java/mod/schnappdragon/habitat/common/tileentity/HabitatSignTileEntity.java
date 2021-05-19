package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class HabitatSignTileEntity extends SignTileEntity {
    @Override
    public TileEntityType<?> getType() {
        return HabitatTileEntityTypes.SIGN.get();
    }
}
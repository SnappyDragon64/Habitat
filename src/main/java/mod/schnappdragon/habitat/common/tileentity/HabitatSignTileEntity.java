package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class HabitatSignTileEntity extends SignBlockEntity {
    @Override
    public BlockEntityType<?> getType() {
        return HabitatTileEntityTypes.SIGN.get();
    }
}
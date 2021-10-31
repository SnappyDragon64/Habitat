package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class HabitatBeehiveTileEntity extends BeehiveBlockEntity {
    @Override
    public BlockEntityType<?> getType() {
        return HabitatTileEntityTypes.BEEHIVE.get();
    }
}
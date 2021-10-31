package mod.schnappdragon.habitat.common.blockentity;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class HabitatBeehiveBlockEntity extends BeehiveBlockEntity {
    @Override
    public BlockEntityType<?> getType() {
        return HabitatBlockEntityTypes.BEEHIVE.get();
    }
}
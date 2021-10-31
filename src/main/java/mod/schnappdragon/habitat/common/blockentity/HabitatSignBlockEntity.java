package mod.schnappdragon.habitat.common.blockentity;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class HabitatSignBlockEntity extends SignBlockEntity {
    @Override
    public BlockEntityType<?> getType() {
        return HabitatBlockEntityTypes.SIGN.get();
    }
}
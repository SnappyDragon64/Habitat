package mod.schnappdragon.habitat.common.blockentity;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;

public class HabitatChestBlockEntity extends ChestBlockEntity {
    protected HabitatChestBlockEntity(BlockEntityType<?> typeIn) {
        super(typeIn);
    }

    public HabitatChestBlockEntity() {
        super(HabitatBlockEntityTypes.CHEST.get());
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 2, worldPosition.getZ() + 2);
    }
}
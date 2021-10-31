package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;

public class HabitatChestTileEntity extends ChestBlockEntity {
    protected HabitatChestTileEntity(BlockEntityType<?> typeIn) {
        super(typeIn);
    }

    public HabitatChestTileEntity() {
        super(HabitatTileEntityTypes.CHEST.get());
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 2, worldPosition.getZ() + 2);
    }
}
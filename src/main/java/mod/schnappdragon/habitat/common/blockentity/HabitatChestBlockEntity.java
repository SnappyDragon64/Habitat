package mod.schnappdragon.habitat.common.blockentity;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class HabitatChestBlockEntity extends ChestBlockEntity {
    protected HabitatChestBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    public HabitatChestBlockEntity(BlockPos pos, BlockState state) {
        super(HabitatBlockEntityTypes.CHEST.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 2, worldPosition.getZ() + 2);
    }
}
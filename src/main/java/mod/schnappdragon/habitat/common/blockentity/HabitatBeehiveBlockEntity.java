package mod.schnappdragon.habitat.common.blockentity;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HabitatBeehiveBlockEntity extends BeehiveBlockEntity {
    public HabitatBeehiveBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return HabitatBlockEntityTypes.BEEHIVE.get();
    }
}
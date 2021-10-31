package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class HabitatStandingSignBlock extends StandingSignBlock {
    public HabitatStandingSignBlock(Properties properties, WoodType woodTypeIn) {
        super(properties, woodTypeIn);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return HabitatBlockEntityTypes.SIGN.get().create(pos, state);
    }
}
package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DreadbudBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    private final Supplier<Block> nextStage;
    private final float threshold;

    public DreadbudBlock(@Nullable Supplier<Block> nextStage, float threshold, Properties properties) {
        super(properties);
        this.nextStage = nextStage;
        this.threshold = threshold;
    }

    @Override
    public Item asItem() {
        return HabitatItems.DREADBUD.get();
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(world, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return this.nextStage != null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getCurrentDifficultyAt(pos).getEffectiveDifficulty() >= this.threshold)
            world.setBlockAndUpdate(pos, this.nextStage.get().defaultBlockState());
    }
}

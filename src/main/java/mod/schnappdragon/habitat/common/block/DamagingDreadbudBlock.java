package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.misc.HabitatDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DamagingDreadbudBlock extends DreadbudBlock {
    private final float damage;

    public DamagingDreadbudBlock(float damage, @Nullable Supplier<Block> nextStage, float threshold, Properties properties) {
        super(nextStage, threshold, properties);
        this.damage = damage;
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && entity instanceof LivingEntity && (entity.xOld != entity.getX() || entity.zOld != entity.getZ()) && (Math.abs(entity.getX() - entity.xOld) >= 0.003D || Math.abs(entity.getZ() - entity.zOld) >= 0.003D)) {
            entity.hurt(HabitatDamageSources.dread(world), this.damage);
        }
    }

    @Nullable
    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DAMAGE_OTHER;
    }
}

package mod.schnappdragon.habitat.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;

public class HugeBallCactusBlock extends Block {
    public HugeBallCactusBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE) {
            entityIn.hurt(DamageSource.CACTUS, 1.0F);
        }

        super.stepOn(level, pos, state, entityIn);
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DAMAGE_CACTUS;
    }
}

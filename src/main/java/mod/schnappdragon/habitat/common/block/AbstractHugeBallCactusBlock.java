package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import mod.schnappdragon.habitat.core.tags.HabitatEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;

public abstract class AbstractHugeBallCactusBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private final BallCactusColor color;

    public AbstractHugeBallCactusBlock(BallCactusColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public BallCactusColor getColor() {
        return color;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        BlockState plant = plantable.getPlant(world, pos.relative(facing));
        if (plant.is(HabitatBlockTags.BALL_CACTUS_BLOCKS_CAN_SUSTAIN))
            return true;

        return super.canSustainPlant(state, world, pos, facing, plantable);
    }

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityIn) {
        if (entityIn instanceof LivingEntity && !entityIn.getType().is(HabitatEntityTypeTags.POLLINATORS)) {
            entityIn.hurt(DamageSource.CACTUS, 1.0F);
        }

        super.stepOn(level, pos, state, entityIn);
    }

    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && !entityIn.getType().is(HabitatEntityTypeTags.POLLINATORS)) {
            entityIn.hurt(DamageSource.CACTUS, 1.0F);
        }

        super.entityInside(state, worldIn, pos, entityIn);
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

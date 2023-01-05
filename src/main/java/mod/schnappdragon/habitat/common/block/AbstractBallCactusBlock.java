package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public abstract class AbstractBallCactusBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    protected static final AABB TOUCH_AABB = new AABB(0.125D, 0, 0.125D, 0.875D, 0.4375D, 0.875D);
    private final BallCactusColor color;

    public AbstractBallCactusBlock(BallCactusColor colorIn, Properties properties) {
        super(properties);
        this.color = colorIn;
    }

    public BallCactusColor getColor() {
        return color;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /*
     * Position Validity Method
     */

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).is(HabitatBlockTags.BALL_CACTUS_GROWS_ON);
    }

    /*
     * Entity Collision Method
     */

    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE && worldIn.getEntities(null, TOUCH_AABB.move(pos)).contains(entityIn)) {
            entityIn.hurt(DamageSource.CACTUS, 1.0F);
        }
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DAMAGE_OTHER;
    }
}
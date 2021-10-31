package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public abstract class AbstractSlimeFernBlock extends Block implements BonemealableBlock {
    public AbstractSlimeFernBlock(Properties properties) {
        super(properties);
    }

    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, Level worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(10) == 0) {
            VoxelShape voxelshape = this.getShape(state, worldIn, pos, CollisionContext.empty());
            Vec3 vector3d = voxelshape.bounds().getCenter();
            double X = (double) pos.getX() + vector3d.x;
            double Y = (double) pos.getY() + vector3d.y;
            double Z = (double) pos.getZ() + vector3d.z;
            worldIn.addParticle(HabitatParticleTypes.FALLING_SLIME.get(), X + (2 * rand.nextDouble() - 1.0F) / 2.5D, Y - rand.nextDouble() / 5, Z + (2 * rand.nextDouble() - 1.0F) / 2.5D, 0.0D, 0.0D, 0.0D);
        }
    }

    /*
     * Entity Walk Method
     */

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.getType() != EntityType.SLIME) {
            double motionY = Math.abs(entityIn.getDeltaMovement().y);
            if (motionY < 0.1D && !entityIn.isSteppingCarefully()) {
                double slowedMotion = 0.4D + motionY * 0.2D;
                entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(slowedMotion, 1.0D, slowedMotion));
            }
        }
    }

    /*
     * IGrowable Methods
     */

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        if (!isClient) {
            ChunkPos chunkPos = new ChunkPos(pos);
            return WorldgenRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, ((WorldGenLevel) worldIn).getSeed(), 987234911L).nextInt(10) == 0;
        }
        return false;
    }

    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};

        for (int j = 0; j < 3; ++j) {
            blockpos$mutable.setWithOffset(pos, Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2));

            if (worldIn.isEmptyBlock(blockpos$mutable) || worldIn.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) {
                for (Direction dir : directions) {
                    if (worldIn.getBlockState(blockpos$mutable.relative(dir)).isFaceSturdy(worldIn, blockpos$mutable, dir.getOpposite())) {
                        BlockState state1 = HabitatBlocks.SLIME_FERN.get().defaultBlockState();

                        if (dir == Direction.UP)
                            state1 = state1.setValue(SlimeFernBlock.ON_CEILING, true);
                        else if (dir != Direction.DOWN)
                            state1 = HabitatBlocks.WALL_SLIME_FERN.get().defaultBlockState().setValue(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite());

                        worldIn.setBlock(blockpos$mutable, state1, 3);
                        break;
                    }
                }
            }
        }
    }

    /*
     * Piston Push Reaction Method
     */

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}

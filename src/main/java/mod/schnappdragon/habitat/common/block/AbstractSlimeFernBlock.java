package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public abstract class AbstractSlimeFernBlock extends Block implements IGrowable {
    public AbstractSlimeFernBlock(Properties properties) {
        super(properties);
    }

    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(10) == 0) {
            VoxelShape voxelshape = this.getShape(state, worldIn, pos, ISelectionContext.dummy());
            Vector3d vector3d = voxelshape.getBoundingBox().getCenter();
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
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.getType() != EntityType.SLIME) {
            double motionY = Math.abs(entityIn.getMotion().y);
            if (motionY < 0.1D && !entityIn.isSteppingCarefully()) {
                double slowedMotion = 0.4D + motionY * 0.2D;
                entityIn.setMotion(entityIn.getMotion().mul(slowedMotion, 1.0D, slowedMotion));
            }
        }
    }

    /*
     * IGrowable Methods
     */

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        if (!isClient) {
            ChunkPos chunkPos = new ChunkPos(pos);
            return SharedSeedRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, ((ISeedReader) worldIn).getSeed(), 987234911L).nextInt(10) == 0;
        }
        return false;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};

        for (int j = 0; j < 3; ++j) {
            blockpos$mutable.setAndOffset(pos, MathHelper.nextInt(rand, 1, 2) - MathHelper.nextInt(rand, 1, 2), MathHelper.nextInt(rand, 1, 2) - MathHelper.nextInt(rand, 1, 2), MathHelper.nextInt(rand, 1, 2) - MathHelper.nextInt(rand, 1, 2));

            if (worldIn.isAirBlock(blockpos$mutable) || worldIn.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) {
                for (Direction dir : directions) {
                    if (worldIn.getBlockState(blockpos$mutable.offset(dir)).isSolidSide(worldIn, blockpos$mutable, dir.getOpposite())) {
                        BlockState state1 = HabitatBlocks.SLIME_FERN.get().getDefaultState();

                        if (dir == Direction.UP)
                            state1 = state1.with(SlimeFernBlock.ON_CEILING, true);
                        else if (dir != Direction.DOWN)
                            state1 = HabitatBlocks.WALL_SLIME_FERN.get().getDefaultState().with(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite());

                        worldIn.setBlockState(blockpos$mutable, state1, 3);
                        break;
                    }
                }
            }
        }
    }

    /*
     * Piston Push Reaction Method
     */

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    /*
     * Block Flammability Methods
     */

    @Override
    public int getFlammability(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        return 60;
    }
}

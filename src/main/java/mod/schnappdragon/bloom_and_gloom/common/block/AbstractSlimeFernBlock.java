package mod.schnappdragon.bloom_and_gloom.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public abstract class AbstractSlimeFernBlock extends Block implements IGrowable {

    public AbstractSlimeFernBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }

    /*
     * Particle Animation Methods
     */

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        for (int i = 0; i < rand.nextInt(1) + 1; ++i) {
            this.addHoneyParticle(worldIn, pos, stateIn);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void addHoneyParticle(World world, BlockPos pos, BlockState state) {
        if (state.getFluidState().isEmpty() && !(world.rand.nextFloat() < 0.3F)) {
            VoxelShape voxelshape = state.getCollisionShape(world, pos);
            double d0 = voxelshape.getEnd(Direction.Axis.Y);
            if (d0 >= 1.0D && !state.isIn(BlockTags.IMPERMEABLE)) {
                double d1 = voxelshape.getStart(Direction.Axis.Y);
                if (d1 > 0.0D) {
                    this.addHoneyParticle(world, pos, voxelshape, (double)pos.getY() + d1 - 0.05D);
                } else {
                    BlockPos blockpos = pos.down();
                    BlockState blockstate = world.getBlockState(blockpos);
                    VoxelShape voxelshape1 = blockstate.getCollisionShape(world, blockpos);
                    double d2 = voxelshape1.getEnd(Direction.Axis.Y);
                    if ((d2 < 1.0D || !blockstate.hasOpaqueCollisionShape(world, blockpos)) && blockstate.getFluidState().isEmpty()) {
                        this.addHoneyParticle(world, pos, voxelshape, (double)pos.getY() - 0.05D);
                    }
                }
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    private void addHoneyParticle(World world, BlockPos pos, VoxelShape shape, double y) {
        this.addHoneyParticle(world, (double)pos.getX() + shape.getStart(Direction.Axis.X), (double)pos.getX() + shape.getEnd(Direction.Axis.X), (double)pos.getZ() + shape.getStart(Direction.Axis.Z), (double)pos.getZ() + shape.getEnd(Direction.Axis.Z), y);
    }

    @OnlyIn(Dist.CLIENT)
    private void addHoneyParticle(World particleData, double x1, double x2, double z1, double z2, double y) {
        particleData.addParticle(ParticleTypes.DRIPPING_HONEY, MathHelper.lerp(particleData.rand.nextDouble(), x1, x2), y, MathHelper.lerp(particleData.rand.nextDouble(), z1, z2), 0.0D, 0.0D, 0.0D);
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
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        spawnAsEntity(worldIn, pos, new ItemStack(this));
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

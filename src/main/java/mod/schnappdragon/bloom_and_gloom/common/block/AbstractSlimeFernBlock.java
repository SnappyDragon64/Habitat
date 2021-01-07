package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGParticleTypes;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
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
        VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, ISelectionContext.dummy());
        Vector3d vector3d = voxelshape.getBoundingBox().getCenter();
        double X = (double) pos.getX() + vector3d.x;
        double Y = (double) pos.getY() + vector3d.y;
        double Z = (double) pos.getZ() + vector3d.z;

        for(int i = 0; i < 2; ++i) {
            if (rand.nextBoolean())
                worldIn.addParticle(BGParticleTypes.DRIPPING_SLIME.get(), X + rand.nextDouble() / 2.5D * (rand.nextBoolean() ? 1 : -1), Y - rand.nextDouble() / 5, Z + rand.nextDouble() / 2.5D * (rand.nextBoolean() ? 1 : -1), 0.0D, 0.0D, 0.0D);
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

package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ParticleEmittingBlock extends Block {
    private final IParticleData particle;

    public ParticleEmittingBlock(IParticleData particle, Properties properties) {
        super(properties);
        this.particle = particle;
    }

    /*
     * Particle Animation Method
     */

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(8) == 0) {
            VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, ISelectionContext.dummy());
            Vector3d vector3d = voxelshape.getBoundingBox().getCenter();
            double X = (double) pos.getX() + vector3d.x;
            double Y = (double) pos.getY() + vector3d.y;
            double Z = (double) pos.getZ() + vector3d.z;
            boolean dirX = rand.nextBoolean();
            boolean dirY = rand.nextBoolean();
            boolean dirZ = rand.nextBoolean();
            worldIn.addParticle(particle, X + rand.nextDouble() / 4 * (dirX ? 1 : -1) + (dirX ? 0.5D : -0.5D), Y - rand.nextDouble() / 4 * (dirY ? 1 : -1) + (dirY ? 0.5D : -0.5D), Z + rand.nextDouble() / 4 * (dirZ ? 1 : -1) + (dirZ ? 0.5D : -0.5D), 0.0D, 0.0D, 0.0D);
        }
    }
}
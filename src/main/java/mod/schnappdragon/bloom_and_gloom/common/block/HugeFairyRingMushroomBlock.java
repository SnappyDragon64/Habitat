package mod.schnappdragon.bloom_and_gloom.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
import java.util.function.Supplier;

public class HugeFairyRingMushroomBlock extends HugeMushroomBlock {
    private final Supplier<BasicParticleType> spore;

    public HugeFairyRingMushroomBlock(Supplier<BasicParticleType> spore, Properties properties) {
        super(properties);
        this.spore = spore;
    }

    /*
     * Particle Animation Method
     */

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(8) == 0) {
            VoxelShape voxelshape = this.getShape(state, worldIn, pos, ISelectionContext.dummy());
            Vector3d vector3d = voxelshape.getBoundingBox().getCenter();
            double X = (double) pos.getX() + vector3d.x;
            double Y = (double) pos.getY() + vector3d.y;
            double Z = (double) pos.getZ() + vector3d.z;
            boolean dirX = rand.nextBoolean();
            boolean dirY = rand.nextBoolean();
            boolean dirZ = rand.nextBoolean();
            worldIn.addParticle(spore.get(), X + rand.nextDouble() / 4 * (dirX ? 1 : -1) + (dirX ? 0.5D : -0.5D), Y - rand.nextDouble() / 4 * (dirY ? 1 : -1) + (dirY ? 0.5D : -0.5D), Z + rand.nextDouble() / 4 * (dirZ ? 1 : -1) + (dirZ ? 0.5D : -0.5D), rand.nextGaussian() * 0.02D, 0.0D, rand.nextGaussian() * 0.02D);
        }
    }
}
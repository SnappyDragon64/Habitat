package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGParticleTypes;
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

    public HugeFairyRingMushroomBlock(Properties properties) {
        super(properties);
    }

    /*
     * Particle Animation Method
     */

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(8) == 0) {
            double X = (double) pos.getX() + 0.5D;
            double Y = (double) pos.getY() + 0.5D;
            double Z = (double) pos.getZ() + 0.5D;
            worldIn.addParticle(BGParticleTypes.FAIRY_RING_SPORE.get(), X + rand.nextGaussian() / 2, Y + rand.nextGaussian() / 2, Z + rand.nextGaussian() / 2, rand.nextGaussian() * 0.01D, 0.0D, rand.nextGaussian() * 0.01D);
        }
    }
}
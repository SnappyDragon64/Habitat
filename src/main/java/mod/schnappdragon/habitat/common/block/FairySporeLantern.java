package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class FairySporeLantern extends LanternBlock {
    public FairySporeLantern(Properties properties) {
        super(properties);
    }

    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(5) == 0)
            worldIn.addParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), pos.getX() + 0.3125D + rand.nextDouble() * 0.375D, pos.getY() + (state.get(LanternBlock.HANGING) ? 0.0625D : 0) + rand.nextDouble() * 0.4375D, pos.getZ() + 0.3125D + rand.nextDouble() * 0.375D, rand.nextGaussian() * 0.01D, 0.0D, rand.nextGaussian() * 0.01D);
    }
}
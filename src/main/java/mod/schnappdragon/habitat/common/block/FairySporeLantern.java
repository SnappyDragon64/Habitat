package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Lantern;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FairySporeLantern extends Lantern {
    public FairySporeLantern(Properties properties) {
        super(properties);
    }

    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, Level worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(5) == 0)
            worldIn.addParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), rand.nextGaussian() * 0.01D, 0.0D, rand.nextGaussian() * 0.01D);
    }
}
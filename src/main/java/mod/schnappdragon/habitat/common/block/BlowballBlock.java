package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BlowballBlock extends FlowerBlock {
    public BlowballBlock(Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
        super(effectSupplier, pEffectDuration, pProperties);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource rand) {
        if (rand.nextInt(8) == 0) {
            double x = pos.getX() + 0.5D + (rand.nextDouble() * 1.5 - 0.75D);
            double y = pos.getY() + 0.5D + rand.nextDouble();
            double z = pos.getZ() + 0.5D + (rand.nextDouble() * 1.5 - 0.75D);

            double xSpeed = Math.abs(rand.nextGaussian()) * 0.032D;
            double ySpeed = rand.nextDouble() * 0.024D;
            double zSpeed = Math.abs(rand.nextGaussian()) * 0.032D;

            world.addParticle(HabitatParticleTypes.BLOWBALL_PUFF.get(), x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}

package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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
        if (rand.nextInt(24) == 0) {
            double xSpeed = Mth.abs((float) rand.nextGaussian()) * 0.01D;
            double ySpeed = Mth.abs((float) rand.nextGaussian()) * 0.01D;
            double zSpeed = Mth.abs((float) rand.nextGaussian()) * 0.01D;

            world.addParticle(HabitatParticleTypes.BLOWBALL_PUFF.get(), pos.getX() + rand.nextDouble() / 2.0F, pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble() / 2.0F, xSpeed, ySpeed, zSpeed);
        }
    }
}

package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class HabitatDamageSources {
    public static DamageSource causeKabloomDamage(KabloomFruit kabloom, @Nullable Entity indirectEntityIn) {
        return new IndirectEntityDamageSource("habitat.kabloom", kabloom, indirectEntityIn);
    }
}
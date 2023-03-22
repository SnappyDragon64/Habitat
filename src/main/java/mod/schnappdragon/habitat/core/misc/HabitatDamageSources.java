package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.projectile.ThrownKabloomFruit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class HabitatDamageSources {
    public static final DamageSource DREADBUD = new DamageSource("habitat.dreadbud");

    public static DamageSource causeKabloomDamage(ThrownKabloomFruit kabloom, @Nullable Entity indirectEntityIn, boolean isExplosion) {
        IndirectEntityDamageSource source = new IndirectEntityDamageSource("habitat.kabloom", kabloom, indirectEntityIn);
        if (isExplosion) source.setExplosion();
        return source;
    }
}
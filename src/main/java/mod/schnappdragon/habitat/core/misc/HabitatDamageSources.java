package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class HabitatDamageSources {
    public static DamageSource causeKabloomDamage(KabloomFruitEntity kabloom, @Nullable Entity indirectEntityIn) {
        return new IndirectEntityDamageSource("habitat.kabloom", kabloom, indirectEntityIn);
    }
}
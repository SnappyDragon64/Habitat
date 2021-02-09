package mod.schnappdragon.bloom_and_gloom.core.misc;

import mod.schnappdragon.bloom_and_gloom.common.entity.projectile.KabloomFruitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class BGDamageSources {
    public static DamageSource causeKabloomDamage(KabloomFruitEntity kabloom, @Nullable Entity indirectEntityIn) {
        return new IndirectEntityDamageSource("bloom_and_gloom.kabloom", kabloom, indirectEntityIn);
    }
}
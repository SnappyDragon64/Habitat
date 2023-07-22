package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.projectile.ThrownKabloomFruit;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class HabitatDamageSources {
    private static DamageSource source(ResourceKey<DamageType> damageType, Level level) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType));
    }

    private DamageSource source(ResourceKey<DamageType> damageType, @Nullable Entity entity, Level level) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), entity);
    }

    private static DamageSource source(ResourceKey<DamageType> damageType, @Nullable Entity entity, @Nullable Entity indirectEntity, Level level) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), entity, indirectEntity);
    }

    public static DamageSource dread(Level level) {
        return source(HabitatDamageTypes.DREAD, level);
    }

    public static DamageSource kabloom(ThrownKabloomFruit kabloom, @Nullable Entity indirectEntity, Level level, boolean isExplosion) {
        return source(isExplosion ? HabitatDamageTypes.KABLOOM : HabitatDamageTypes.KABLOOM_NO_EXPLOSION,
                kabloom, indirectEntity, level);
    }

    public static class HabitatDamageTypes {
        public static ResourceKey<DamageType> KABLOOM = create("kabloom");
        public static ResourceKey<DamageType> KABLOOM_NO_EXPLOSION = create("kabloom_no_explosion");
        public static ResourceKey<DamageType> DREAD = create("dread");

        private static ResourceKey<DamageType> create(String id){
            return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Habitat.MODID, id));
        }
    }
}

package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Habitat.MOD_ID);

    public static final RegistryObject<EntityType<KabloomFruitEntity>> KABLOOM_FRUIT = ENTITY_TYPES.register("kabloom_fruit",
            () -> EntityType.Builder.<KabloomFruitEntity>create(KabloomFruitEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10).build("bloom_and_gloom:kabloom_fruit"));
}
package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, BloomAndGloom.MOD_ID);

    public static final RegistryObject<EntityType<KabloomFruitEntity>> KABLOOM_FRUIT = ENTITY_TYPES.register("kabloom_fruit",
            () -> EntityType.Builder.<KabloomFruitEntity>create(KabloomFruitEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).build("kabloom_fruit"));
}
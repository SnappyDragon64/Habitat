package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BloomAndGloom.MOD_ID);

    public static final RegistryObject<EntityType<KabloomFruitEntity>> KABLOOM_FRUIT = ENTITIES.register("kabloom_fruit",
            () -> EntityType.Builder.create(KabloomFruitEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).build("kabloom_fruit"));
}
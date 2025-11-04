package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.common.entity.animal.Pooka;
import mod.schnappdragon.habitat.common.entity.projectile.ThrownKabloomFruit;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.misc.HabitatMobCategories;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Habitat.MODID);

    public static final RegistryObject<EntityType<ThrownKabloomFruit>> KABLOOM_FRUIT = ENTITY_TYPES.register("kabloom_fruit", () -> EntityType.Builder.<ThrownKabloomFruit>of(ThrownKabloomFruit::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("habitat:kabloom_fruit"));
    public static final RegistryObject<EntityType<Pooka>> POOKA = ENTITY_TYPES.register("pooka", () -> EntityType.Builder.of(Pooka::new, MobCategory.CREATURE).sized(0.67F, 0.83F).clientTrackingRange(8).build("habitat:pooka"));
    public static final RegistryObject<EntityType<Passerine>> PASSERINE = ENTITY_TYPES.register("passerine", () -> EntityType.Builder.of(Passerine::new, HabitatMobCategories.PASSERINE).sized(0.5F, 0.5F).clientTrackingRange(8).build("habitat:passerine"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(POOKA.get(), Pooka.createAttributes().build());
        event.put(PASSERINE.get(), Passerine.createAttributes().build());
    }
}
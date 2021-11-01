package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.entity.vehicle.HabitatBoat;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruit;
import mod.schnappdragon.habitat.common.item.HabitatSpawnEggItem;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Habitat.MODID);

    public static final RegistryObject<EntityType<KabloomFruit>> KABLOOM_FRUIT = ENTITY_TYPES.register("kabloom_fruit", () -> EntityType.Builder.<KabloomFruit>of(KabloomFruit::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("habitat:kabloom_fruit"));
    public static final RegistryObject<EntityType<HabitatBoat>> BOAT = ENTITY_TYPES.register("boat", () -> EntityType.Builder.<HabitatBoat>of(HabitatBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("habitat:boat"));
    public static final RegistryObject<EntityType<Pooka>> POOKA = ENTITY_TYPES.register("pooka", () -> EntityType.Builder.<Pooka>of(Pooka::new, MobCategory.MONSTER).sized(0.4F, 0.5F).clientTrackingRange(8).build("habitat:pooka"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(POOKA.get(), Pooka.registerAttributes().build());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        HabitatSpawnEggItem.registerSpawnEggs();
    }
}
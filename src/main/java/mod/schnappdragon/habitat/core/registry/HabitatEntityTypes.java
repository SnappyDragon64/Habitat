package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.entity.item.HabitatBoatEntity;
import mod.schnappdragon.habitat.common.entity.monster.PookaEntity;
import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Habitat.MOD_ID);

    public static final RegistryObject<EntityType<KabloomFruitEntity>> KABLOOM_FRUIT = ENTITY_TYPES.register("kabloom_fruit", () -> EntityType.Builder.<KabloomFruitEntity>create(KabloomFruitEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10).build("habitat:kabloom_fruit"));
    public static final RegistryObject<EntityType<HabitatBoatEntity>> BOAT = ENTITY_TYPES.register("boat", () -> EntityType.Builder.<HabitatBoatEntity>create(HabitatBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).trackingRange(10).build("habitat:boat"));
    public static final RegistryObject<EntityType<PookaEntity>> POOKA = ENTITY_TYPES.register("pooka", () -> EntityType.Builder.<PookaEntity>create(PookaEntity::new, EntityClassification.MONSTER).size(0.4F, 0.5F).trackingRange(8).build("habitat:pooka"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(POOKA.get(), PookaEntity.registerAttributes().create());
    }
}
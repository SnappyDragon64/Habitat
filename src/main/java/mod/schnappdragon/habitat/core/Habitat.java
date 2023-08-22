package mod.schnappdragon.habitat.core;

import mod.schnappdragon.habitat.core.api.conditions.RecipeConditions;
import mod.schnappdragon.habitat.core.dispenser.HabitatDispenseItemBehavior;
import mod.schnappdragon.habitat.core.misc.*;
import mod.schnappdragon.habitat.core.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Habitat.MODID)
public class Habitat {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "habitat";
    public static final boolean DEV = !FMLLoader.isProduction();

    public Habitat() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        HabitatBlocks.BLOCKS.register(modEventBus);
        HabitatItems.ITEMS.register(modEventBus);
        HabitatBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        HabitatSoundEvents.SOUND_EVENTS.register(modEventBus);
        HabitatEntityTypes.ENTITY_TYPES.register(modEventBus);
        HabitatEffects.EFFECTS.register(modEventBus);
        HabitatPotions.POTIONS.register(modEventBus);
        HabitatRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        HabitatFeatures.FEATURES.register(modEventBus);
        HabitatParticleTypes.PARTICLE_TYPES.register(modEventBus);
        HabitatPoiTypes.POI_TYPES.register(modEventBus);

        RecipeConditions.registerSerializers();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            HabitatCriterionTriggers.registerCriteriaTriggers();
            HabitatLootConditionTypes.registerLootConditionTypes();
            HabitatSpawns.registerSpawns();

            HabitatPlacementModifierTypes.registerPlacementModifierTypes();

            HabitatDispenseItemBehavior.registerDispenserBehaviors();
            HabitatComposterChances.registerComposterChances();
            HabitatFlowerPots.addPlantsToFlowerPot();
            HabitatFlammables.registerFlammables();

            HabitatBrewingMixes.registerBrewingMixes();
        });
    }
}
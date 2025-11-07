package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.common.entity.animal.Pooka;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatSpawns {

    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        registerSpawn(event, HabitatEntityTypes.POOKA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pooka::checkPookaSpawnRules);
        registerSpawn(event, HabitatEntityTypes.PASSERINE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, Passerine::checkPasserineSpawnRules);
    }

    private static <T extends Mob> void registerSpawn(SpawnPlacementRegisterEvent event, EntityType<T> entityType, SpawnPlacements.Type placementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
        event.register(entityType, placementType, heightmapType, predicate, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.common.levelgen.feature.structure.FairyRingStructure;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatStructures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MODID)
public class HabitatSpawns {
    public static void registerSpawns() {
        registerSpawn(HabitatEntityTypes.POOKA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pooka::checkPookaSpawnRules);
        registerSpawn(HabitatEntityTypes.PASSERINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Passerine::checkPasserineSpawnRules);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void setupStructureSpawns(StructureSpawnListGatherEvent event) {
        if (event.getStructure() == HabitatStructures.FAIRY_RING.get())
            event.addEntitySpawns(MobCategory.MONSTER, FairyRingStructure.STRUCTURE_MONSTERS.get());
    }

    private static <T extends Mob> void registerSpawn(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType, decoratorType, heightMapType, predicate);
    }
}
package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.common.entity.animal.Pooka;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MODID)
public class HabitatSpawns {
    public static void registerSpawns() {
        registerSpawn(HabitatEntityTypes.POOKA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pooka::checkPookaSpawnRules);
        registerSpawn(HabitatEntityTypes.PASSERINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Passerine::checkPasserineSpawnRules);
    }

    private static <T extends Mob> void registerSpawn(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType, decoratorType, heightMapType, predicate);
    }
}
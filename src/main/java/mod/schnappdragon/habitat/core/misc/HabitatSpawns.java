package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class HabitatSpawns {
    public static void registerSpawns() {
        SpawnPlacements.register(HabitatEntityTypes.POOKA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pooka::checkPookaSpawnRules);
    }
}
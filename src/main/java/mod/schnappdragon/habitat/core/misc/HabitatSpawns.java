package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.entity.monster.PookaEntity;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;

public class HabitatSpawns {
    public static void registerSpawns() {
        EntitySpawnPlacementRegistry.register(HabitatEntityTypes.POOKA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PookaEntity::canPookaSpawn);
    }
}
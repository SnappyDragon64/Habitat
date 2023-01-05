package mod.schnappdragon.habitat.core.registry;

import com.google.common.collect.ImmutableSet;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HabitatPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Habitat.MODID);

    public static final RegistryObject<PoiType> BEEHIVE = POI_TYPES.register("beehive", () -> new PoiType(ImmutableSet.copyOf(HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE.get().getStateDefinition().getPossibleStates()), 0, 1));
}

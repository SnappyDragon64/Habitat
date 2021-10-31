package mod.schnappdragon.habitat.core.misc;

import com.google.common.collect.Sets;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.Map;

public class HabitatPOI {
    public static void addBeehivePOI() {
        PoiType.BEEHIVE.matchingStates = Sets.newHashSet(PoiType.BEEHIVE.matchingStates);
        Map<BlockState, PoiType> statePointOfInterestMap = ObfuscationReflectionHelper.getPrivateValue(PoiType.class, null, "TYPE_BY_STATE");
        if (statePointOfInterestMap != null) {
            HabitatBlockTags.BEEHIVES.getValues().forEach(block -> {
                block.getStateDefinition().getPossibleStates().forEach(state -> {
                    statePointOfInterestMap.put(state, PoiType.BEEHIVE);
                    PoiType.BEEHIVE.matchingStates.add(state);
                });
            });
        }
    }
}
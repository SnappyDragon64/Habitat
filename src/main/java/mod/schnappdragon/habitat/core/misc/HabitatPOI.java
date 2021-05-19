package mod.schnappdragon.habitat.core.misc;

import com.google.common.collect.Sets;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;

public class HabitatPOI {
    public static void addBeehivePOI() {
        PointOfInterestType.BEEHIVE.blockStates = Sets.newHashSet(PointOfInterestType.BEEHIVE.blockStates);
        Map<BlockState, PointOfInterestType> statePointOfInterestMap = ObfuscationReflectionHelper.getPrivateValue(PointOfInterestType.class, null, "field_221073_u");
        if (statePointOfInterestMap != null) {
            HabitatBlockTags.BEEHIVE.getAllElements().forEach(block -> {
                block.getStateContainer().getValidStates().forEach(state -> {
                    statePointOfInterestMap.put(state, PointOfInterestType.BEEHIVE);
                    PointOfInterestType.BEEHIVE.blockStates.add(state);
                });
            });
        }
    }
}
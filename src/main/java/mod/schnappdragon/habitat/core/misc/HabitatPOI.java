package mod.schnappdragon.habitat.core.misc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.Map;
import java.util.Set;

public class HabitatPOI {
    private static final Set<BlockState> BEEHIVES = ImmutableList.of(HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE).stream().flatMap(beehive -> beehive.get().getStateDefinition().getPossibleStates().stream()).collect(ImmutableSet.toImmutableSet());

    public static void addBeehivePOI() {
        PoiType.BEEHIVE.matchingStates = Sets.newHashSet(PoiType.BEEHIVE.matchingStates);
        Map<BlockState, PoiType> statePointOfInterestMap = ObfuscationReflectionHelper.getPrivateValue(PoiType.class, null, "f_27323_");
        if (statePointOfInterestMap != null) {
            PoiType.BEEHIVE.matchingStates.addAll(BEEHIVES);
            BEEHIVES.forEach(state -> statePointOfInterestMap.put(state, PoiType.BEEHIVE));
        }
    }
}
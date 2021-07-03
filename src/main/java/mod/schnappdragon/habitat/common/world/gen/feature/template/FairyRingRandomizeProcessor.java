package mod.schnappdragon.habitat.common.world.gen.feature.template;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.core.registry.HabitatStructureProcessors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class FairyRingRandomizeProcessor extends StructureProcessor {
   public static final Codec<FairyRingRandomizeProcessor> CODEC;
   public static final FairyRingRandomizeProcessor PROCESSOR = new FairyRingRandomizeProcessor();

   private FairyRingRandomizeProcessor() {
   }

   public Template.BlockInfo func_230386_a_(IWorldReader reader, BlockPos pos1, BlockPos pos2, Template.BlockInfo blockInfo1, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
      return blockInfo1;
   }

   protected IStructureProcessorType<?> getType() {
      return HabitatStructureProcessors.FAIRY_RING_RANDOMIZE;
   }

   static {
      CODEC = Codec.unit(() -> PROCESSOR);
   }
}
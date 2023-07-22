package mod.schnappdragon.habitat.core.mixin;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LakeFeature.class)
public class NoLakesInStructuresMixin {
    @Inject(
            method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void habitat_noLakesInStructures(FeaturePlaceContext<BlockStateConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
        if (!(context.level() instanceof WorldGenRegion))
            return;

        Registry<Structure> structureRegistry = context.level().registryAccess().registryOrThrow(Registries.STRUCTURE);
        StructureManager structureManager = ((WorldGenRegionAccessor) context.level()).getStructureManager();

        Optional<Structure> optionalFairyRing = structureRegistry.getOptional(new ResourceLocation(Habitat.MODID, "fairy_ring"));
        if (optionalFairyRing.isPresent() && structureManager.getStructureAt(context.origin(), optionalFairyRing.get()).isValid())
            cir.setReturnValue(false);
    }
}
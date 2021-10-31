package mod.schnappdragon.habitat.core.mixin;

import mod.schnappdragon.habitat.core.registry.HabitatStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LakeFeature.class)
public class NoLakesInStructuresMixin {
    @Inject(
            method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/core/BlockPos;below(I)Lnet/minecraft/core/BlockPos;"),
            cancellable = true
    )
    private void habitat_noLakesInStructures(FeaturePlaceContext<BlockStateConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
        if (context.level().startsForFeature(SectionPos.of(context.origin()), HabitatStructures.FAIRY_RING_STRUCTURE.get()).findAny().isPresent()) {
            cir.setReturnValue(false);
        }
    }
}
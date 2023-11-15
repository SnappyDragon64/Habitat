package mod.schnappdragon.habitat.core.mixin;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class AddItemUseEffectsMixin extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity {
    public AddItemUseEffectsMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(
            method = "triggerItemUseEffects(Lnet/minecraft/world/item/ItemStack;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isUsingItem()Z", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void habitat_addItemUseEffectsMixin(ItemStack stack, int amount, CallbackInfo ci) {
        if (stack.is(HabitatItems.BLOWBALL.get())) {
            Vec3 viewVector = this.getViewVector(1.0F).normalize();
            Vec3 posVector = viewVector.scale(0.6F).add(this.getX(), this.getEyeY(), this.getZ());
            Vec3 speedVector = viewVector.scale(0.1F).add(this.random.triangle(0.0D, 0.04D), this.random.triangle(0.0D, 0.04D), this.random.triangle(0.0D, 0.04D));

            this.level().addParticle(HabitatParticleTypes.BLOWBALL_PUFF.get(), posVector.x(), posVector.y(), posVector.z(), speedVector.x(), speedVector.y(), speedVector.z());
        }
    }
}
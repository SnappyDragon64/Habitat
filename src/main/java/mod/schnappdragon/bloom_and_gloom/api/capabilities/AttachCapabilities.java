package mod.schnappdragon.bloom_and_gloom.api.capabilities;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.api.capabilities.classes.ConsumedFairyRingMushroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class AttachCapabilities {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof MooshroomEntity) {
            event.addCapability(new ResourceLocation(BloomAndGloom.MOD_ID, "consumed_fairy_ring_mushroom"), ConsumedFairyRingMushroom.PROVIDER);
        }
    }
}
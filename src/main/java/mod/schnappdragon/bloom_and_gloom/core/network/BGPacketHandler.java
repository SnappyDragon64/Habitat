package mod.schnappdragon.bloom_and_gloom.core.network;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.network.packet.ConsumedFairyRingMushroomPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BGPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(BloomAndGloom.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerMessages() {
        int ID = 0;
        INSTANCE.registerMessage(++ID, ConsumedFairyRingMushroomPacket.class, ConsumedFairyRingMushroomPacket::encode, ConsumedFairyRingMushroomPacket::new, ConsumedFairyRingMushroomPacket::handle);
    }
}
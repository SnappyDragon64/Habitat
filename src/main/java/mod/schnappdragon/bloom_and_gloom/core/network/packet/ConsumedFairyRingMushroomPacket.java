package mod.schnappdragon.bloom_and_gloom.core.network.packet;

import mod.schnappdragon.bloom_and_gloom.core.capabilities.classes.ConsumedFairyRingMushroom;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ConsumedFairyRingMushroomPacket {
    private final int entityId;
    private final boolean consumedFairyRingMushroom;

    public ConsumedFairyRingMushroomPacket(final PacketBuffer packetBuffer) {
        this.entityId = packetBuffer.readInt();
        this.consumedFairyRingMushroom = packetBuffer.readBoolean();
    }

    public ConsumedFairyRingMushroomPacket(int entityId, boolean value) {
        this.entityId = entityId;
        this.consumedFairyRingMushroom = value;
    }

    public void encode(final PacketBuffer packetBuffer) {
        packetBuffer.writeInt(this.entityId);
        packetBuffer.writeBoolean(this.consumedFairyRingMushroom);
    }

    public static void handle(ConsumedFairyRingMushroomPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            ctx.get().enqueueWork(() -> {
                Entity entity = Objects.requireNonNull(Minecraft.getInstance().world.getEntityByID(msg.entityId));
                if (entity instanceof MooshroomEntity) {
                    MooshroomEntity mooshroom = (MooshroomEntity) entity;
                    mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().setConsumedFairyRingMushroom(msg.consumedFairyRingMushroom);
                    if (msg.consumedFairyRingMushroom)
                        mooshroom.effectDuration *= 2;
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
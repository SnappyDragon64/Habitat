package mod.schnappdragon.bloom_and_gloom.api.capabilities.classes;

import mod.schnappdragon.bloom_and_gloom.api.capabilities.interfaces.IConsumedFairyRingMushroom;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class ConsumedFairyRingMushroom implements IConsumedFairyRingMushroom {
    private boolean consumedFairyRingMushroom = false;
    private static final ConsumedFairyRingMushroom INSTANCE = new ConsumedFairyRingMushroom();
    public static Storage STORAGE = new Storage();
    public static Provider PROVIDER = new Provider();
    public static Factory FACTORY = new Factory();

    @Override
    public void setConsumedFairyRingMushroom(boolean value) {
        consumedFairyRingMushroom = value;
    }

    @Override
    public boolean getConsumedFairyRingMushroom() {
        return consumedFairyRingMushroom;
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {
        @CapabilityInject(IConsumedFairyRingMushroom.class)
        public final static Capability<ConsumedFairyRingMushroom> CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY = null;

        @Override
        public CompoundNBT serializeNBT() {
            return (CompoundNBT) CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY.getStorage().writeNBT(CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY, ConsumedFairyRingMushroom.INSTANCE, null);
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY.getStorage().readNBT(CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY, ConsumedFairyRingMushroom.INSTANCE, null, nbt);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> ConsumedFairyRingMushroom.INSTANCE));
        }
    }

    private static class Storage implements Capability.IStorage<IConsumedFairyRingMushroom> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IConsumedFairyRingMushroom> capability, IConsumedFairyRingMushroom instance, Direction
                side) {
            CompoundNBT compound = new CompoundNBT();
            compound.putBoolean("ConsumedFairyRingMushroom", instance.getConsumedFairyRingMushroom());

            return compound;
        }

        @Override
        public void readNBT(Capability<IConsumedFairyRingMushroom> capability, IConsumedFairyRingMushroom instance, Direction side, INBT nbt) {
            instance.setConsumedFairyRingMushroom(((CompoundNBT) nbt).getBoolean("ConsumedFairyRingMushroom"));
        }
    }

    private static class Factory implements Callable<IConsumedFairyRingMushroom> {
        @Override
        public IConsumedFairyRingMushroom call() throws Exception {
            return new ConsumedFairyRingMushroom();
        }
    }
}
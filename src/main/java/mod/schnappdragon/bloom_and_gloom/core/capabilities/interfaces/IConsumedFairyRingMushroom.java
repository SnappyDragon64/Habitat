package mod.schnappdragon.bloom_and_gloom.core.capabilities.interfaces;

public interface IConsumedFairyRingMushroom {
    default void setConsumedFairyRingMushroom(boolean value) {}

    default boolean getConsumedFairyRingMushroom() {
        return false;
    }
}
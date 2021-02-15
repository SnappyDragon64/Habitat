package mod.schnappdragon.bloom_and_gloom.api.capabilities.interfaces;

public interface IConsumedFairyRingMushroom {
    default void setConsumedFairyRingMushroom(boolean value) {}

    default boolean getConsumedFairyRingMushroom() {
        return false;
    }
}
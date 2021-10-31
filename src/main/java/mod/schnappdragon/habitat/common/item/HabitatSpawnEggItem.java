package mod.schnappdragon.habitat.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HabitatSpawnEggItem extends SpawnEggItem {
    public static final List<HabitatSpawnEggItem> HABITAT_EGGS = new ArrayList<>();
    private final Supplier<? extends EntityType<?>> entityTypeSupplier;

    public HabitatSpawnEggItem(RegistryObject<? extends EntityType<?>> entityTypeSupplier, int baseColor, int spotColor, Properties properties) {
        super(null, baseColor, spotColor, properties);
        this.entityTypeSupplier = entityTypeSupplier;
        HABITAT_EGGS.add(this);
    }

    @Override
    public EntityType<?> getType(CompoundTag compound) {
        return entityTypeSupplier.get();
    }

    public static void registerSpawnEggs() {
        Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "BY_ID");
        for (SpawnEggItem egg : HABITAT_EGGS) {
            EGGS.put(egg.getType(null), egg);
        }
    }
}
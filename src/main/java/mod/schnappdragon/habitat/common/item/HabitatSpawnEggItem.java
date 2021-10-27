package mod.schnappdragon.habitat.common.item;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

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
    public EntityType<?> getType(CompoundNBT compound) {
        return entityTypeSupplier.get();
    }

    public static void registerSpawnEggs() {
        Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "field_195987_b");
        for (SpawnEggItem egg : HABITAT_EGGS) {
            EGGS.put(egg.getType(null), egg);
        }
    }
}
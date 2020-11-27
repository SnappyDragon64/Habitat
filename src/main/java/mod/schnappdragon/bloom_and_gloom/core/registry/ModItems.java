package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<Item> RAFFLESIA_SEED = ITEMS.register("rafflesia_seed",
            () -> new BlockNamedItem(ModBlocks.RAFFLESIA_BLOCK.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
}
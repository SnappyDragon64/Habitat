package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.item.FairyRingMushroomStewItem;
import mod.schnappdragon.habitat.common.item.KabloomFruitItem;
import mod.schnappdragon.habitat.common.item.WallOrBaseItem;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.misc.HabitatFoods;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Habitat.MOD_ID);

    public static final RegistryObject<Item> RAFFLESIA_SEED = ITEMS.register("rafflesia_seed",
            () -> new BlockNamedItem(HabitatBlocks.RAFFLESIA.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Item> KABLOOM_SEEDS = ITEMS.register("kabloom_seeds",
            () -> new BlockNamedItem(HabitatBlocks.KABLOOM_BUSH.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> KABLOOM_FRUIT = ITEMS.register("kabloom_fruit",
            () -> new KabloomFruitItem(new Item.Properties().maxStackSize(16).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> CANDIED_KABLOOM_FRUIT = ITEMS.register("candied_kabloom_fruit",
            () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(HabitatFoods.CANDIED_KABLOOM_FRUIT)));

    public static final RegistryObject<Item> SLIME_FERN = ITEMS.register("slime_fern",
            () -> new WallOrBaseItem(HabitatBlocks.SLIME_FERN.get(), HabitatBlocks.WALL_SLIME_FERN.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Item> ORANGE_BALL_CACTUS_FLOWER = ITEMS.register("orange_ball_cactus_flower",
            () -> new BlockItem(HabitatBlocks.ORANGE_BALL_CACTUS_FLOWER.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> PINK_BALL_CACTUS_FLOWER = ITEMS.register("pink_ball_cactus_flower",
            () -> new BlockItem(HabitatBlocks.PINK_BALL_CACTUS_FLOWER.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> RED_BALL_CACTUS_FLOWER = ITEMS.register("red_ball_cactus_flower",
            () -> new BlockItem(HabitatBlocks.RED_BALL_CACTUS_FLOWER.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> YELLOW_BALL_CACTUS_FLOWER = ITEMS.register("yellow_ball_cactus_flower",
            () -> new BlockItem(HabitatBlocks.YELLOW_BALL_CACTUS_FLOWER.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> ORANGE_BALL_CACTUS = ITEMS.register("orange_ball_cactus",
            () -> new BlockItem(HabitatBlocks.ORANGE_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> PINK_BALL_CACTUS = ITEMS.register("pink_ball_cactus",
            () -> new BlockItem(HabitatBlocks.PINK_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> RED_BALL_CACTUS = ITEMS.register("red_ball_cactus",
            () -> new BlockItem(HabitatBlocks.RED_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> YELLOW_BALL_CACTUS = ITEMS.register("yellow_ball_cactus",
            () -> new BlockItem(HabitatBlocks.YELLOW_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> ROASTED_CACTUS = ITEMS.register("roasted_cactus",
            () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(HabitatFoods.ROASTED_CACTUS)));

    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM = ITEMS.register("fairy_ring_mushroom",
            () -> new BlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BLOCK = ITEMS.register("fairy_ring_mushroom_block",
            () -> new BlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_STEM = ITEMS.register("fairy_ring_mushroom_stem",
            () -> new BlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> FAIRYLIGHT = ITEMS.register("fairylight",
            () -> new BlockItem(HabitatBlocks.FAIRYLIGHT.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_STEW = ITEMS.register("fairy_ring_mushroom_stew",
            () -> new FairyRingMushroomStewItem(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1).food(HabitatFoods.FAIRY_RING_MUSHROOM_STEW)));
}
package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.item.BallCactusFlowerItem;
import mod.schnappdragon.bloom_and_gloom.common.item.KabloomFruitItem;
import mod.schnappdragon.bloom_and_gloom.common.item.KabloomSeedsItem;
import mod.schnappdragon.bloom_and_gloom.common.item.WallOrBaseItem;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.misc.BGFoods;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<Item> RAFFLESIA_SEED = ITEMS.register("rafflesia_seed",
            () -> new BlockNamedItem(BGBlocks.RAFFLESIA.get(), new Item.Properties().group(ItemGroup.MATERIALS)));

    public static final RegistryObject<Item> KABLOOM_SEEDS = ITEMS.register("kabloom_seeds",
            () -> new KabloomSeedsItem(BGBlocks.KABLOOM_BUSH.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> KABLOOM_FRUIT = ITEMS.register("kabloom_fruit",
            () -> new KabloomFruitItem((new Item.Properties()).maxStackSize(16).group(ItemGroup.MISC)));
    public static final RegistryObject<Item> CANDIED_KABLOOM_FRUIT = ITEMS.register("candied_kabloom_fruit",
            () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(BGFoods.CANDIED_KABLOOM_FRUIT)));

    public static final RegistryObject<Item> SLIME_FERN = ITEMS.register("slime_fern",
            () -> new WallOrBaseItem(BGBlocks.SLIME_FERN.get(), BGBlocks.WALL_SLIME_FERN.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Item> PINK_BALL_CACTUS_FLOWER = ITEMS.register("pink_ball_cactus_flower",
            () -> new BallCactusFlowerItem(BGBlocks.PINK_BALL_CACTUS_SEEDLING.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> RED_BALL_CACTUS_FLOWER = ITEMS.register("red_ball_cactus_flower",
            () -> new BallCactusFlowerItem(BGBlocks.RED_BALL_CACTUS_SEEDLING.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> ORANGE_BALL_CACTUS_FLOWER = ITEMS.register("orange_ball_cactus_flower",
            () -> new BallCactusFlowerItem(BGBlocks.ORANGE_BALL_CACTUS_SEEDLING.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> YELLOW_BALL_CACTUS_FLOWER = ITEMS.register("yellow_ball_cactus_flower",
            () -> new BallCactusFlowerItem(BGBlocks.YELLOW_BALL_CACTUS_SEEDLING.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> PINK_BALL_CACTUS = ITEMS.register("pink_ball_cactus",
            () -> new BlockItem(BGBlocks.PINK_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> RED_BALL_CACTUS = ITEMS.register("red_ball_cactus",
            () -> new BlockItem(BGBlocks.RED_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> ORANGE_BALL_CACTUS = ITEMS.register("orange_ball_cactus",
            () -> new BlockItem(BGBlocks.ORANGE_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> YELLOW_BALL_CACTUS = ITEMS.register("yellow_ball_cactus",
            () -> new BlockItem(BGBlocks.YELLOW_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> ROASTED_CACTUS = ITEMS.register("roasted_cactus",
            () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(BGFoods.ROASTED_CACTUS)));
}
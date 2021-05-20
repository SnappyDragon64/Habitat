package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.item.FairyRingMushroomStewItem;
import mod.schnappdragon.habitat.common.item.FuelBlockItem;
import mod.schnappdragon.habitat.common.item.KabloomFruitItem;
import mod.schnappdragon.habitat.common.item.WallOrBaseItem;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.misc.HabitatFoods;
import mod.schnappdragon.habitat.core.util.CompatHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Habitat.MOD_ID);

    public static final RegistryObject<Item> RAFFLESIA = ITEMS.register("rafflesia",
            () -> new BlockNamedItem(HabitatBlocks.RAFFLESIA.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Item> KABLOOM_PULP = ITEMS.register("kabloom_pulp",
            () -> new BlockNamedItem(HabitatBlocks.KABLOOM_BUSH.get(), new Item.Properties().group(ItemGroup.FOOD).food(HabitatFoods.KABLOOM_PULP)));
    public static final RegistryObject<Item> KABLOOM_FRUIT = ITEMS.register("kabloom_fruit",
            () -> new KabloomFruitItem(new Item.Properties().maxStackSize(16).group(ItemGroup.COMBAT)));

    public static final RegistryObject<Item> SLIME_FERN = ITEMS.register("slime_fern",
            () -> new WallOrBaseItem(HabitatBlocks.SLIME_FERN.get(), HabitatBlocks.WALL_SLIME_FERN.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<Item> ORANGE_BALL_CACTUS_FLOWER = registerBlockItem("orange_ball_cactus_flower", HabitatBlocks.ORANGE_BALL_CACTUS_FLOWER.get(), ItemGroup.DECORATIONS);
    public static final RegistryObject<Item> PINK_BALL_CACTUS_FLOWER = registerBlockItem("pink_ball_cactus_flower", HabitatBlocks.PINK_BALL_CACTUS_FLOWER.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> RED_BALL_CACTUS_FLOWER = registerBlockItem("red_ball_cactus_flower", HabitatBlocks.RED_BALL_CACTUS_FLOWER.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> YELLOW_BALL_CACTUS_FLOWER = registerBlockItem("yellow_ball_cactus_flower", HabitatBlocks.YELLOW_BALL_CACTUS_FLOWER.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> ORANGE_BALL_CACTUS = registerBlockItem("orange_ball_cactus", HabitatBlocks.ORANGE_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> PINK_BALL_CACTUS = registerBlockItem("pink_ball_cactus", HabitatBlocks.PINK_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> RED_BALL_CACTUS = registerBlockItem("red_ball_cactus", HabitatBlocks.RED_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> YELLOW_BALL_CACTUS = registerBlockItem("yellow_ball_cactus", HabitatBlocks.YELLOW_BALL_CACTUS.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> DRIED_BALL_CACTUS = ITEMS.register("dried_ball_cactus",
            () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(HabitatFoods.DRIED_BALL_CACTUS)));

    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM = registerBlockItem("fairy_ring_mushroom", HabitatBlocks.FAIRY_RING_MUSHROOM.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BLOCK = registerBlockItem("fairy_ring_mushroom_block", HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_STEM = registerBlockItem("fairy_ring_mushroom_stem", HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> FAIRYLIGHT = registerBlockItem("fairylight", HabitatBlocks.FAIRYLIGHT.get(), new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_STEW = ITEMS.register("fairy_ring_mushroom_stew",
            () -> new FairyRingMushroomStewItem(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1).food(HabitatFoods.FAIRY_RING_MUSHROOM_STEW)));

    public static final RegistryObject<Item> STRIPPED_FAIRY_RING_MUSHROOM_STEM = registerBlockItem("stripped_fairy_ring_mushroom_stem", HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_STEM.get(), CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms"));
    public static final RegistryObject<Item> ENHANCED_FAIRY_RING_MUSHROOM_STEM = registerBlockItem("enhanced_fairy_ring_mushroom_stem", HabitatBlocks.ENHANCED_FAIRY_RING_MUSHROOM_STEM.get(), CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms"));
    public static final RegistryObject<Item> STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE = registerBlockItem("stripped_fairy_ring_mushroom_hyphae", HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE.get(), CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_HYPHAE = registerBlockItem("fairy_ring_mushroom_hyphae", HabitatBlocks.FAIRY_RING_MUSHROOM_HYPHAE.get(), CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_PLANKS = registerBlockItem("fairy_ring_mushroom_planks", HabitatBlocks.FAIRY_RING_MUSHROOM_PLANKS.get(), CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_SLAB = registerBlockItem("fairy_ring_mushroom_slab", HabitatBlocks.FAIRY_RING_MUSHROOM_SLAB.get(), CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_STAIRS = registerBlockItem("fairy_ring_mushroom_stairs", HabitatBlocks.FAIRY_RING_MUSHROOM_STAIRS.get(), CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_PRESSURE_PLATE = registerBlockItem("fairy_ring_mushroom_pressure_plate", HabitatBlocks.FAIRY_RING_MUSHROOM_PRESSURE_PLATE.get(), CompatHelper.compatItemGroupProperty(ItemGroup.REDSTONE, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_FENCE = ITEMS.register("fairy_ring_mushroom_fence",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE.get(), 300, CompatHelper.compatItemGroupProperty(ItemGroup.DECORATIONS, "enhanced_mushrooms")));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_FENCE_GATE = ITEMS.register("fairy_ring_mushroom_fence_gate",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE_GATE.get(), 300, CompatHelper.compatItemGroupProperty(ItemGroup.REDSTONE, "enhanced_mushrooms")));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BUTTON = registerBlockItem("fairy_ring_mushroom_button", HabitatBlocks.FAIRY_RING_MUSHROOM_BUTTON.get(), CompatHelper.compatItemGroupProperty(ItemGroup.REDSTONE, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_TRAPDOOR = registerBlockItem("fairy_ring_mushroom_trapdoor", HabitatBlocks.FAIRY_RING_MUSHROOM_TRAPDOOR.get(), CompatHelper.compatItemGroupProperty(ItemGroup.REDSTONE, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_DOOR = registerBlockItem("fairy_ring_mushroom_door", HabitatBlocks.FAIRY_RING_MUSHROOM_DOOR.get(), CompatHelper.compatItemGroupProperty(ItemGroup.REDSTONE, "enhanced_mushrooms"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_SIGN = ITEMS.register("fairy_ring_mushroom_sign",
            () -> new SignItem(CompatHelper.compatItemGroupProperty(ItemGroup.DECORATIONS, "enhanced_mushrooms").maxStackSize(16), HabitatBlocks.FAIRY_RING_MUSHROOM_SIGN.get(), HabitatBlocks.FAIRY_RING_MUSHROOM_WALL_SIGN.get()));

    public static final RegistryObject<Item> VERTICAL_FAIRY_RING_MUSHROOM_PLANKS = registerBlockItem("vertical_fairy_ring_mushroom_planks", HabitatBlocks.VERTICAL_FAIRY_RING_MUSHROOM_PLANKS.get(), CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms", "quark"));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_VERTICAL_SLAB = ITEMS.register("fairy_ring_mushroom_vertical_slab",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_VERTICAL_SLAB.get(), 150, CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms", "quark")));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BOOKSHELF = ITEMS.register("fairy_ring_mushroom_bookshelf",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_BOOKSHELF.get(), 300, CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms", "quark")));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_LADDER = ITEMS.register("fairy_ring_mushroom_ladder",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_LADDER.get(), 300, CompatHelper.compatItemGroupProperty(ItemGroup.DECORATIONS, "enhanced_mushrooms", "quark")));
    public static final RegistryObject<Item> STRIPPED_FAIRY_RING_MUSHROOM_POST = ITEMS.register("stripped_fairy_ring_mushroom_post",
            () -> new FuelBlockItem(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_POST.get(), 300, CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms", "quark")));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_POST = ITEMS.register("fairy_ring_mushroom_post",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_POST.get(), 300, CompatHelper.compatItemGroupProperty(ItemGroup.BUILDING_BLOCKS, "enhanced_mushrooms", "quark")));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_CHEST = ITEMS.register("fairy_ring_mushroom_chest",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_CHEST.get(), 300, CompatHelper.compatItemGroupProperty(ItemGroup.DECORATIONS, "enhanced_mushrooms", "quark")));
    public static final RegistryObject<Item> TRAPPED_FAIRY_RING_MUSHROOm_CHEST = ITEMS.register("trapped_fairy_ring_mushroom_chest",
            () -> new FuelBlockItem(HabitatBlocks.TRAPPED_FAIRY_RING_MUSHROOM_CHEST.get(), 300, CompatHelper.compatItemGroupProperty(ItemGroup.REDSTONE, "enhanced_mushrooms", "quark")));

    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BEEHIVE = registerBlockItem("fairy_ring_mushroom_beehive", HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE.get(), CompatHelper.compatItemGroupProperty(ItemGroup.DECORATIONS, "enhanced_mushrooms", "buzzier_bees"));

    private static RegistryObject<Item> registerBlockItem(String name, Block block, ItemGroup group) {
        return registerBlockItem(name, block, new Item.Properties().group(group));
    }

    private static RegistryObject<Item> registerBlockItem(String name, Block block, Item.Properties properties) {
        return ITEMS.register(name, () -> new BlockItem(block, properties));
    }
}
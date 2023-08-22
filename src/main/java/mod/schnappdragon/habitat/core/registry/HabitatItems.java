package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.entity.vehicle.HabitatBoat;
import mod.schnappdragon.habitat.common.item.*;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.misc.HabitatFoods;
import mod.schnappdragon.habitat.core.util.CompatHelper;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class HabitatItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Habitat.MODID);

    public static final RegistryObject<Item> RAFFLESIA = registerBlockItem("rafflesia", HabitatBlocks.RAFFLESIA);

    public static final RegistryObject<Item> KABLOOM_PULP = ITEMS.register("kabloom_pulp",
            () -> new ItemNameBlockItem(HabitatBlocks.KABLOOM_BUSH.get(), new Item.Properties().food(HabitatFoods.KABLOOM_PULP)));
    public static final RegistryObject<Item> KABLOOM_FRUIT = ITEMS.register("kabloom_fruit",
            () -> new KabloomFruitItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> KABLOOM_FRUIT_PILE = registerBlockItem("kabloom_fruit_pile", HabitatBlocks.KABLOOM_FRUIT_PILE);
    public static final RegistryObject<Item> KABLOOM_PULP_BLOCK = registerBlockItem("kabloom_pulp_block", HabitatBlocks.KABLOOM_PULP_BLOCK);

    public static final RegistryObject<Item> SLIME_FERN = registerBlockItem("slime_fern", HabitatBlocks.SLIME_FERN);

    public static final RegistryObject<Item> ORANGE_BALL_CACTUS_FLOWER = registerBlockItem("orange_ball_cactus_flower", HabitatBlocks.ORANGE_BALL_CACTUS_FLOWER);
    public static final RegistryObject<Item> PINK_BALL_CACTUS_FLOWER = registerBlockItem("pink_ball_cactus_flower", HabitatBlocks.PINK_BALL_CACTUS_FLOWER);
    public static final RegistryObject<Item> RED_BALL_CACTUS_FLOWER = registerBlockItem("red_ball_cactus_flower", HabitatBlocks.RED_BALL_CACTUS_FLOWER);
    public static final RegistryObject<Item> YELLOW_BALL_CACTUS_FLOWER = registerBlockItem("yellow_ball_cactus_flower", HabitatBlocks.YELLOW_BALL_CACTUS_FLOWER);
    public static final RegistryObject<Item> ORANGE_BALL_CACTUS = registerBlockItem("orange_ball_cactus", HabitatBlocks.ORANGE_BALL_CACTUS);
    public static final RegistryObject<Item> PINK_BALL_CACTUS = registerBlockItem("pink_ball_cactus", HabitatBlocks.PINK_BALL_CACTUS);
    public static final RegistryObject<Item> RED_BALL_CACTUS = registerBlockItem("red_ball_cactus", HabitatBlocks.RED_BALL_CACTUS);
    public static final RegistryObject<Item> YELLOW_BALL_CACTUS = registerBlockItem("yellow_ball_cactus", HabitatBlocks.YELLOW_BALL_CACTUS);
    public static final RegistryObject<Item> DRIED_BALL_CACTUS = ITEMS.register("dried_ball_cactus",
            () -> new Item((new Item.Properties()).food(HabitatFoods.DRIED_BALL_CACTUS)));

    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM = ITEMS.register("fairy_ring_mushroom",
            () -> new FairyRingMushroomItem(HabitatBlocks.FAIRY_RING_MUSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BLOCK = registerBlockItem("fairy_ring_mushroom_block", HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_STEM = registerBlockItem("fairy_ring_mushroom_stem", HabitatBlocks.FAIRY_RING_MUSHROOM_STEM);
    public static final RegistryObject<Item> FAIRYLIGHT = registerBlockItem("fairylight", HabitatBlocks.FAIRYLIGHT);
    public static final RegistryObject<Item> FAIRY_SPORE_LANTERN = registerBlockItem("fairy_spore_lantern", HabitatBlocks.FAIRY_SPORE_LANTERN);

    public static final RegistryObject<Item> STRIPPED_FAIRY_RING_MUSHROOM_STEM = registerBlockItem("stripped_fairy_ring_mushroom_stem", HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_STEM);
    public static final RegistryObject<Item> ENHANCED_FAIRY_RING_MUSHROOM_STEM = registerBlockItem("enhanced_fairy_ring_mushroom_stem", HabitatBlocks.ENHANCED_FAIRY_RING_MUSHROOM_STEM);
    public static final RegistryObject<Item> STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE = registerBlockItem("stripped_fairy_ring_mushroom_hyphae", HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_HYPHAE = registerBlockItem("fairy_ring_mushroom_hyphae", HabitatBlocks.FAIRY_RING_MUSHROOM_HYPHAE);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_PLANKS = registerBlockItem("fairy_ring_mushroom_planks", HabitatBlocks.FAIRY_RING_MUSHROOM_PLANKS);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_SLAB = registerBlockItem("fairy_ring_mushroom_slab", HabitatBlocks.FAIRY_RING_MUSHROOM_SLAB);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_STAIRS = registerBlockItem("fairy_ring_mushroom_stairs", HabitatBlocks.FAIRY_RING_MUSHROOM_STAIRS);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_PRESSURE_PLATE = registerBlockItem("fairy_ring_mushroom_pressure_plate", HabitatBlocks.FAIRY_RING_MUSHROOM_PRESSURE_PLATE);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_FENCE = ITEMS.register("fairy_ring_mushroom_fence",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE.get(), 300, getProperties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_FENCE_GATE = ITEMS.register("fairy_ring_mushroom_fence_gate",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE_GATE.get(), 300, getProperties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BUTTON = registerBlockItem("fairy_ring_mushroom_button", HabitatBlocks.FAIRY_RING_MUSHROOM_BUTTON);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_TRAPDOOR = registerBlockItem("fairy_ring_mushroom_trapdoor", HabitatBlocks.FAIRY_RING_MUSHROOM_TRAPDOOR);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_DOOR = registerBlockItem("fairy_ring_mushroom_door", HabitatBlocks.FAIRY_RING_MUSHROOM_DOOR);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_SIGN = ITEMS.register("fairy_ring_mushroom_sign",
            () -> new SignItem(getProperties().stacksTo(16), HabitatBlocks.FAIRY_RING_MUSHROOM_SIGN.get(), HabitatBlocks.FAIRY_RING_MUSHROOM_WALL_SIGN.get()));

    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_VERTICAL_SLAB = ITEMS.register("fairy_ring_mushroom_vertical_slab",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_VERTICAL_SLAB.get(), 150, getProperties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BOOKSHELF = ITEMS.register("fairy_ring_mushroom_bookshelf",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_BOOKSHELF.get(), 300, getProperties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_LADDER = ITEMS.register("fairy_ring_mushroom_ladder",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_LADDER.get(), 300, getProperties()));
    public static final RegistryObject<Item> STRIPPED_FAIRY_RING_MUSHROOM_POST = ITEMS.register("stripped_fairy_ring_mushroom_post",
            () -> new FuelBlockItem(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_POST.get(), 300, getProperties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_POST = ITEMS.register("fairy_ring_mushroom_post",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_POST.get(), 300, getProperties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_CHEST = ITEMS.register("fairy_ring_mushroom_chest",
            () -> new HabitatChestItem(HabitatBlocks.FAIRY_RING_MUSHROOM_CHEST.get(), 300, getProperties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_TRAPPED_CHEST = ITEMS.register("fairy_ring_mushroom_trapped_chest",
            () -> new HabitatChestItem(HabitatBlocks.FAIRY_RING_MUSHROOM_TRAPPED_CHEST.get(), 300, getProperties()));
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BEEHIVE = registerBlockItem("fairy_ring_mushroom_beehive", HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_CABINET = ITEMS.register("fairy_ring_mushroom_cabinet",
            () -> new FuelBlockItem(HabitatBlocks.FAIRY_RING_MUSHROOM_CABINET.get(), 300, getProperties()));

    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BOAT = ITEMS.register("fairy_ring_mushroom_boat",
            () -> new HabitatBoatItem(HabitatBoat.Type.FAIRY_RING_MUSHROOM, getProperties()));

    public static final RegistryObject<Item> POOKA_SPAWN_EGG = ITEMS.register("pooka_spawn_egg",
            () -> new ForgeSpawnEggItem(HabitatEntityTypes.POOKA, 15920353, 16771962, new Item.Properties()));

    public static final RegistryObject<Item> PASSERINE_SPAWN_EGG = ITEMS.register("passerine_spawn_egg",
            () -> new ForgeSpawnEggItem(HabitatEntityTypes.PASSERINE, 1259855, 13384789, new Item.Properties()));

    public static final RegistryObject<Item> EDELWEISS_SHRUB = registerBlockItem("edelweiss_shrub", HabitatBlocks.EDELWEISS_SHRUB);
    public static final RegistryObject<Item> EDELWEISS = registerBlockItem("edelweiss", HabitatBlocks.EDELWEISS);

    public static final RegistryObject<Item> BALL_CACTUS_BLOCK = registerBlockItem("ball_cactus_block", HabitatBlocks.BALL_CACTUS_BLOCK);
    public static final RegistryObject<Item> FLOWERING_ORANGE_BALL_CACTUS_BLOCK = registerBlockItem("flowering_orange_ball_cactus_block", HabitatBlocks.FLOWERING_ORANGE_BALL_CACTUS_BLOCK);
    public static final RegistryObject<Item> FLOWERING_PINK_BALL_CACTUS_BLOCK = registerBlockItem("flowering_pink_ball_cactus_block", HabitatBlocks.FLOWERING_PINK_BALL_CACTUS_BLOCK);
    public static final RegistryObject<Item> FLOWERING_RED_BALL_CACTUS_BLOCK = registerBlockItem("flowering_red_ball_cactus_block", HabitatBlocks.FLOWERING_RED_BALL_CACTUS_BLOCK);
    public static final RegistryObject<Item> FLOWERING_YELLOW_BALL_CACTUS_BLOCK = registerBlockItem("flowering_yellow_ball_cactus_block", HabitatBlocks.FLOWERING_YELLOW_BALL_CACTUS_BLOCK);
    public static final RegistryObject<Item> DRIED_BALL_CACTUS_BLOCK = registerBlockItem("dried_ball_cactus_block", HabitatBlocks.DRIED_BALL_CACTUS_BLOCK);

    public static final RegistryObject<Item> PURPLE_ANTHURIUM = registerBlockItem("purple_anthurium", HabitatBlocks.PURPLE_ANTHURIUM);
    public static final RegistryObject<Item> RED_ANTHURIUM = registerBlockItem("red_anthurium", HabitatBlocks.RED_ANTHURIUM);
    public static final RegistryObject<Item> WHITE_ANTHURIUM = registerBlockItem("white_anthurium", HabitatBlocks.WHITE_ANTHURIUM);
    public static final RegistryObject<Item> YELLOW_ANTHURIUM = registerBlockItem("yellow_anthurium", HabitatBlocks.YELLOW_ANTHURIUM);
    public static final RegistryObject<Item> TALL_PURPLE_ANTHURIUM = registerBlockItem("tall_purple_anthurium", HabitatBlocks.TALL_PURPLE_ANTHURIUM);
    public static final RegistryObject<Item> TALL_RED_ANTHURIUM = registerBlockItem("tall_red_anthurium", HabitatBlocks.TALL_RED_ANTHURIUM);
    public static final RegistryObject<Item> TALL_WHITE_ANTHURIUM = registerBlockItem("tall_white_anthurium", HabitatBlocks.TALL_WHITE_ANTHURIUM);
    public static final RegistryObject<Item> TALL_YELLOW_ANTHURIUM = registerBlockItem("tall_yellow_anthurium", HabitatBlocks.TALL_YELLOW_ANTHURIUM);

    public static final RegistryObject<Item> DREADBUD = ITEMS.register("dreadbud",
            () -> new ItemNameBlockItem(HabitatBlocks.BLOOMING_DREADBUD.get(), new Item.Properties()));


    private static Item.Properties getProperties() {
        return new Item.Properties();
    }

    private static RegistryObject<Item> registerBlockItem(String name, Supplier<Block> block) {
        return registerBlockItem(name, block, getProperties());
    }

    private static RegistryObject<Item> registerBlockItem(String name, Supplier<Block> block, Item.Properties properties) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }
}
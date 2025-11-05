package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.item.*;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.misc.HabitatFoods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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

    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM = registerBlockItem("fairy_ring_mushroom", HabitatBlocks.FAIRY_RING_MUSHROOM);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_BLOCK = registerBlockItem("fairy_ring_mushroom_block", HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK);
    public static final RegistryObject<Item> FAIRY_RING_MUSHROOM_STEM = registerBlockItem("fairy_ring_mushroom_stem", HabitatBlocks.FAIRY_RING_MUSHROOM_STEM);
    public static final RegistryObject<Item> FAIRYLIGHT = registerBlockItem("fairylight", HabitatBlocks.FAIRYLIGHT);
    public static final RegistryObject<Item> FAIRY_SPORE_LANTERN = registerBlockItem("fairy_spore_lantern", HabitatBlocks.FAIRY_SPORE_LANTERN);

    public static final RegistryObject<Item> POOKA_SPAWN_EGG = ITEMS.register("pooka_spawn_egg",
            () -> new ForgeSpawnEggItem(HabitatEntityTypes.POOKA, 15920353, 16771962, new Item.Properties()));

    public static final RegistryObject<Item> PASSERINE_SPAWN_EGG = ITEMS.register("passerine_spawn_egg",
            () -> new ForgeSpawnEggItem(HabitatEntityTypes.PASSERINE, 1259855, 13384789, new Item.Properties()));
    public static final RegistryObject<Item> PASSERINE_IN_A_POT = ITEMS.register("passerine_in_a_pot",
            () -> new PasserinePotItem(new Item.Properties().stacksTo(1)));

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

    public static final RegistryObject<Item> BLOWBALL = ITEMS.register("blowball",
            () -> new BlowballItem(HabitatBlocks.BLOWBALL.get(), getProperties()));

    @SubscribeEvent
    public static void registerCreativeTabsItem(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(RAFFLESIA);
            event.accept(KABLOOM_PULP);
            event.accept(KABLOOM_FRUIT_PILE);
            event.accept(KABLOOM_PULP_BLOCK);
            event.accept(SLIME_FERN);
            event.accept(ORANGE_BALL_CACTUS_FLOWER);
            event.accept(PINK_BALL_CACTUS_FLOWER);
            event.accept(RED_BALL_CACTUS_FLOWER);
            event.accept(YELLOW_BALL_CACTUS_FLOWER);
            event.accept(ORANGE_BALL_CACTUS);
            event.accept(PINK_BALL_CACTUS);
            event.accept(RED_BALL_CACTUS);
            event.accept(YELLOW_BALL_CACTUS);
            event.accept(FAIRY_RING_MUSHROOM);
            event.accept(FAIRY_RING_MUSHROOM_BLOCK);
            event.accept(FAIRY_RING_MUSHROOM_STEM);
            event.accept(FAIRYLIGHT);
            event.accept(EDELWEISS_SHRUB);
            event.accept(EDELWEISS);
            event.accept(BALL_CACTUS_BLOCK);
            event.accept(FLOWERING_ORANGE_BALL_CACTUS_BLOCK);
            event.accept(FLOWERING_PINK_BALL_CACTUS_BLOCK);
            event.accept(FLOWERING_RED_BALL_CACTUS_BLOCK);
            event.accept(FLOWERING_YELLOW_BALL_CACTUS_BLOCK);
            event.accept(DRIED_BALL_CACTUS_BLOCK);
            event.accept(PURPLE_ANTHURIUM);
            event.accept(RED_ANTHURIUM);
            event.accept(WHITE_ANTHURIUM);
            event.accept(YELLOW_ANTHURIUM);
            event.accept(TALL_PURPLE_ANTHURIUM);
            event.accept(TALL_RED_ANTHURIUM);
            event.accept(TALL_WHITE_ANTHURIUM);
            event.accept(TALL_YELLOW_ANTHURIUM);
            event.accept(DREADBUD);
            event.accept(BLOWBALL);
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(FAIRY_RING_MUSHROOM);
            event.accept(FAIRYLIGHT);
            event.accept(FAIRY_SPORE_LANTERN);
        } else if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(RAFFLESIA);
            event.accept(KABLOOM_FRUIT_PILE);
            event.accept(KABLOOM_PULP_BLOCK);
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(KABLOOM_FRUIT);
        } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(KABLOOM_PULP);
            event.accept(DRIED_BALL_CACTUS);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(KABLOOM_PULP);
            event.accept(DRIED_BALL_CACTUS);
        } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(POOKA_SPAWN_EGG);
            event.accept(PASSERINE_SPAWN_EGG);
        }
    }

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
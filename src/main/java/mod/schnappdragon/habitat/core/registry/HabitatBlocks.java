package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.block.*;
import mod.schnappdragon.habitat.common.block.misc.*;
import mod.schnappdragon.habitat.common.block.wood.*;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Habitat.MOD_ID);

    public static final RegistryObject<Block> RAFFLESIA = BLOCKS.register("rafflesia", () -> new RafflesiaBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.RED).zeroHardnessAndResistance().sound(SoundType.PLANT).tickRandomly().doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> POTTED_RAFFLESIA = BLOCKS.register("potted_rafflesia", () -> new FlowerPotBlock(RAFFLESIA.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));

    public static final RegistryObject<Block> KABLOOM_BUSH = BLOCKS.register("kabloom_bush", () -> new KabloomBushBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).tickRandomly().doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> POTTED_KABLOOM_BUSH = BLOCKS.register("potted_kabloom_bush", () -> new FlowerPotBlock(KABLOOM_BUSH.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));

    public static final RegistryObject<Block> SLIME_FERN = BLOCKS.register("slime_fern", () -> new SlimeFernBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> WALL_SLIME_FERN = BLOCKS.register("wall_slime_fern", () -> new WallSlimeFernBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().lootFrom(SLIME_FERN).sound(SoundType.PLANT).doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> POTTED_SLIME_FERN = BLOCKS.register("potted_slime_fern", () -> new FlowerPotBlock(SLIME_FERN.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));

    public static final RegistryObject<Block> ORANGE_BALL_CACTUS_FLOWER = BLOCKS.register("orange_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.ORANGE, HabitatEffects.PRICKLING, 5, AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid().tickRandomly()));
    public static final RegistryObject<Block> PINK_BALL_CACTUS_FLOWER = BLOCKS.register("pink_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.PINK, HabitatEffects.PRICKLING, 5, AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid().tickRandomly()));
    public static final RegistryObject<Block> RED_BALL_CACTUS_FLOWER = BLOCKS.register("red_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.RED, HabitatEffects.PRICKLING, 5, AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid().tickRandomly()));
    public static final RegistryObject<Block> YELLOW_BALL_CACTUS_FLOWER = BLOCKS.register("yellow_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.YELLOW, HabitatEffects.PRICKLING, 5, AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid().tickRandomly()));
    public static final RegistryObject<Block> GROWING_ORANGE_BALL_CACTUS = BLOCKS.register("growing_orange_ball_cactus", () -> new GrowingBallCactusBlock(BallCactusColor.ORANGE, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.CLOTH).notSolid().tickRandomly()));
    public static final RegistryObject<Block> GROWING_PINK_BALL_CACTUS = BLOCKS.register("growing_pink_ball_cactus", () -> new GrowingBallCactusBlock(BallCactusColor.PINK, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.CLOTH).notSolid().tickRandomly()));
    public static final RegistryObject<Block> GROWING_RED_BALL_CACTUS = BLOCKS.register("growing_red_ball_cactus", () -> new GrowingBallCactusBlock(BallCactusColor.RED, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.CLOTH).notSolid().tickRandomly()));
    public static final RegistryObject<Block> GROWING_YELLOW_BALL_CACTUS = BLOCKS.register("growing_yellow_ball_cactus", () -> new GrowingBallCactusBlock(BallCactusColor.YELLOW, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.CLOTH).notSolid().tickRandomly()));
    public static final RegistryObject<Block> ORANGE_BALL_CACTUS = BLOCKS.register("orange_ball_cactus", () -> new BallCactusBlock(BallCactusColor.ORANGE, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.CLOTH).notSolid().tickRandomly()));
    public static final RegistryObject<Block> PINK_BALL_CACTUS = BLOCKS.register("pink_ball_cactus", () -> new BallCactusBlock(BallCactusColor.PINK, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.CLOTH).notSolid().tickRandomly()));
    public static final RegistryObject<Block> RED_BALL_CACTUS = BLOCKS.register("red_ball_cactus", () -> new BallCactusBlock(BallCactusColor.RED, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.CLOTH).notSolid().tickRandomly()));
    public static final RegistryObject<Block> YELLOW_BALL_CACTUS = BLOCKS.register("yellow_ball_cactus", () -> new BallCactusBlock(BallCactusColor.YELLOW, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.CLOTH).notSolid().tickRandomly()));
    public static final RegistryObject<Block> FLOWERING_ORANGE_BALL_CACTUS = BLOCKS.register("flowering_orange_ball_cactus", () -> new FloweringBallCactusBlock(BallCactusColor.ORANGE, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.CLOTH).notSolid()));
    public static final RegistryObject<Block> FLOWERING_PINK_BALL_CACTUS = BLOCKS.register("flowering_pink_ball_cactus", () -> new FloweringBallCactusBlock(BallCactusColor.PINK, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.CLOTH).notSolid()));
    public static final RegistryObject<Block> FLOWERING_RED_BALL_CACTUS = BLOCKS.register("flowering_red_ball_cactus", () -> new FloweringBallCactusBlock(BallCactusColor.RED, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.CLOTH).notSolid()));
    public static final RegistryObject<Block> FLOWERING_YELLOW_BALL_CACTUS = BLOCKS.register("flowering_yellow_ball_cactus", () -> new FloweringBallCactusBlock(BallCactusColor.YELLOW, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.CLOTH).notSolid()));
    public static final RegistryObject<Block> POTTED_ORANGE_BALL_CACTUS_FLOWER = BLOCKS.register("potted_orange_ball_cactus_flower", () -> new FlowerPotBlock(ORANGE_BALL_CACTUS_FLOWER.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final RegistryObject<Block> POTTED_PINK_BALL_CACTUS_FLOWER = BLOCKS.register("potted_pink_ball_cactus_flower", () -> new FlowerPotBlock(PINK_BALL_CACTUS_FLOWER.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final RegistryObject<Block> POTTED_RED_BALL_CACTUS_FLOWER = BLOCKS.register("potted_red_ball_cactus_flower", () -> new FlowerPotBlock(RED_BALL_CACTUS_FLOWER.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final RegistryObject<Block> POTTED_YELLOW_BALL_CACTUS_FLOWER = BLOCKS.register("potted_yellow_ball_cactus_flower", () -> new FlowerPotBlock(YELLOW_BALL_CACTUS_FLOWER.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final RegistryObject<Block> POTTED_ORANGE_BALL_CACTUS = BLOCKS.register("potted_orange_ball_cactus", () -> new FlowerPotBlock(ORANGE_BALL_CACTUS.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final RegistryObject<Block> POTTED_PINK_BALL_CACTUS = BLOCKS.register("potted_pink_ball_cactus", () -> new FlowerPotBlock(PINK_BALL_CACTUS.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final RegistryObject<Block> POTTED_RED_BALL_CACTUS = BLOCKS.register("potted_red_ball_cactus", () -> new FlowerPotBlock(RED_BALL_CACTUS.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final RegistryObject<Block> POTTED_YELLOW_BALL_CACTUS = BLOCKS.register("potted_yellow_ball_cactus", () -> new FlowerPotBlock(YELLOW_BALL_CACTUS.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));

    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM = BLOCKS.register("fairy_ring_mushroom", () -> new FairyRingMushroomBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.BROWN).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT).setNeedsPostProcessing((state, reader, pos) -> true).setLightLevel((state) -> state.get(FairyRingMushroomBlock.MUSHROOMS) * 2 + 4).notSolid().tickRandomly()));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_BLOCK = BLOCKS.register("fairy_ring_mushroom_block", () -> new HugeFairyRingMushroomBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).setLightLevel((state) -> 1).setEmmisiveRendering((state, reader, pos) -> true).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_STEM = BLOCKS.register("fairy_ring_mushroom_stem", () -> new HugeMushroomBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRYLIGHT = BLOCKS.register("fairylight", () -> new Block(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(1.0F).sound(SoundType.SHROOMLIGHT).setLightLevel((state) -> 15)));
    public static final RegistryObject<Block> POTTED_FAIRY_RING_MUSHROOM = BLOCKS.register("potted_fairy_ring_mushroom", () -> new FlowerPotBlock(FAIRY_RING_MUSHROOM.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().setLightLevel((state) -> 4).notSolid()));

    public static final RegistryObject<Block> STRIPPED_FAIRY_RING_MUSHROOM_STEM = BLOCKS.register("stripped_fairy_ring_mushroom_stem", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ENHANCED_FAIRY_RING_MUSHROOM_STEM = BLOCKS.register("enhanced_fairy_ring_mushroom_stem", () -> new LogBlock(STRIPPED_FAIRY_RING_MUSHROOM_STEM, AbstractBlock.Properties.create(Material.WOOD, (state) -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.SAND : MaterialColor.QUARTZ).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));

    /* TEMP COMMENT FROM TEAM AURORA'S ENHANCED MUSHROOMS FOR REFERENCE
    public static final RegistryObject<Block> STRIPPED_BROWN_MUSHROOM_HYPHAE = HELPER.createBlock("stripped_brown_mushroom_hyphae", ()->new StrippedWoodBlock(Block.Properties.from(Blocks.STRIPPED_OAK_WOOD)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_HYPHAE = HELPER.createBlock("brown_mushroom_hyphae", ()->new WoodBlock(STRIPPED_BROWN_MUSHROOM_HYPHAE, Block.Properties.from(Blocks.OAK_WOOD)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_PLANKS = HELPER.createBlock("brown_mushroom_planks", ()->new PlanksBlock(Block.Properties.from(Blocks.OAK_PLANKS)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_SLAB = HELPER.createBlock("brown_mushroom_slab", ()->new WoodSlabBlock(Block.Properties.from(Blocks.OAK_SLAB)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_STAIRS = HELPER.createBlock("brown_mushroom_stairs", ()->new WoodStairsBlock(BROWN_MUSHROOM_PLANKS.get().getDefaultState(), Block.Properties.from(Blocks.OAK_STAIRS)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_PRESSURE_PLATE = HELPER.createBlock("brown_mushroom_pressure_plate", ()->new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE)), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> BROWN_MUSHROOM_FENCE = HELPER.createFuelBlock("brown_mushroom_fence", ()->new WoodFenceBlock(Block.Properties.from(Blocks.OAK_FENCE)), 300, ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_FENCE_GATE = HELPER.createFuelBlock("brown_mushroom_fence_gate", ()->new WoodFenceGateBlock(Block.Properties.from(Blocks.OAK_FENCE_GATE)), 300, ItemGroup.REDSTONE);
    public static final RegistryObject<Block> BROWN_MUSHROOM_BUTTON = HELPER.createBlock("brown_mushroom_button", ()->new AbnormalsWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON)), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> BROWN_MUSHROOM_TRAPDOOR = HELPER.createBlock("brown_mushroom_trapdoor", ()->new WoodTrapDoorBlock(Block.Properties.from(Blocks.OAK_TRAPDOOR)), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> BROWN_MUSHROOM_DOOR = HELPER.createBlock("brown_mushroom_door", ()->new WoodDoorBlock(Block.Properties.from(Blocks.OAK_DOOR)), ItemGroup.REDSTONE);
    public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> BROWN_MUSHROOM_SIGNS = HELPER.createSignBlock("brown_mushroom", MaterialColor.BROWN);

    public static final RegistryObject<Block> VERTICAL_BROWN_MUSHROOM_PLANKS = HELPER.createCompatBlock("quark","vertical_brown_mushroom_planks", ()->new Block(Block.Properties.from(Blocks.OAK_PLANKS)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark","brown_mushroom_vertical_slab", ()->new VerticalSlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)), 150, ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_BOOKSHELF = HELPER.createCompatFuelBlock("quark","brown_mushroom_bookshelf", ()->new BookshelfBlock(Block.Properties.from(Blocks.BOOKSHELF)), 300, ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_LADDER = HELPER.createCompatFuelBlock("quark","brown_mushroom_ladder", ()->new AbnormalsLadderBlock(Block.Properties.from(Blocks.LADDER).harvestTool(ToolType.AXE)), 300, ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> STRIPPED_BROWN_MUSHROOM_POST = HELPER.createCompatFuelBlock("quark", "stripped_brown_mushroom_post", () -> new WoodPostBlock(AbstractBlock.Properties.from(Blocks.OAK_FENCE)), 300, ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_MUSHROOM_POST = HELPER.createCompatFuelBlock("quark", "brown_mushroom_post", () -> new WoodPostBlock(STRIPPED_BROWN_MUSHROOM_POST, AbstractBlock.Properties.from(Blocks.OAK_FENCE)), 300, ItemGroup.BUILDING_BLOCKS);
    public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> BROWN_MUSHROOM_CHESTS = HELPER.createCompatChestBlocks("brown_mushroom", MaterialColor.BROWN);

    public static final RegistryObject<Block> BROWN_MUSHROOM_BEEHIVE = HELPER.createCompatBlock("buzzier_bees","brown_mushroom_beehive", ()->new AbnormalsBeehiveBlock(Block.Properties.from(Blocks.BEEHIVE)), ItemGroup.DECORATIONS);
    */
}
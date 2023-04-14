package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.block.*;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HabitatBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Habitat.MODID);

    public static final RegistryObject<Block> RAFFLESIA = BLOCKS.register("rafflesia", () -> new RafflesiaBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion()));
    public static final RegistryObject<Block> POTTED_RAFFLESIA = BLOCKS.register("potted_rafflesia", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RAFFLESIA, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> KABLOOM_BUSH = BLOCKS.register("kabloom_bush", () -> new KabloomBushBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).randomTicks().noCollission().noOcclusion()));
    public static final RegistryObject<Block> POTTED_KABLOOM_BUSH = BLOCKS.register("potted_kabloom_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, KABLOOM_BUSH, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> KABLOOM_FRUIT_PILE = BLOCKS.register("kabloom_fruit_pile", () -> new KabloomFruitPileBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).instabreak().sound(SoundType.WOOL)));
    public static final RegistryObject<Block> KABLOOM_PULP_BLOCK = BLOCKS.register("kabloom_pulp_block", () -> new KabloomPulpBlock(BlockBehaviour.Properties.of(Material.CLAY, MaterialColor.GRASS).explosionResistance(1200.0F).noOcclusion().sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> SLIME_FERN = BLOCKS.register("slime_fern", () -> new SlimeFernBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion()));
    public static final RegistryObject<Block> POTTED_SLIME_FERN = BLOCKS.register("potted_slime_fern", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SLIME_FERN, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> ORANGE_BALL_CACTUS_FLOWER = BLOCKS.register("orange_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.ORANGE, HabitatEffects.PRICKLING, 5, BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion().randomTicks()));
    public static final RegistryObject<Block> PINK_BALL_CACTUS_FLOWER = BLOCKS.register("pink_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.PINK, HabitatEffects.PRICKLING, 5, BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion().randomTicks()));
    public static final RegistryObject<Block> RED_BALL_CACTUS_FLOWER = BLOCKS.register("red_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.RED, HabitatEffects.PRICKLING, 5, BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion().randomTicks()));
    public static final RegistryObject<Block> YELLOW_BALL_CACTUS_FLOWER = BLOCKS.register("yellow_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.YELLOW, HabitatEffects.PRICKLING, 5, BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion().randomTicks()));
    public static final RegistryObject<Block> GROWING_ORANGE_BALL_CACTUS = BLOCKS.register("growing_orange_ball_cactus", () -> new GrowingBallCactusBlock(BallCactusColor.ORANGE, BlockBehaviour.Properties.of(Material.CACTUS).instabreak().sound(SoundType.WOOL).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> GROWING_PINK_BALL_CACTUS = BLOCKS.register("growing_pink_ball_cactus", () -> new GrowingBallCactusBlock(BallCactusColor.PINK, BlockBehaviour.Properties.of(Material.CACTUS).instabreak().sound(SoundType.WOOL).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> GROWING_RED_BALL_CACTUS = BLOCKS.register("growing_red_ball_cactus", () -> new GrowingBallCactusBlock(BallCactusColor.RED, BlockBehaviour.Properties.of(Material.CACTUS).instabreak().sound(SoundType.WOOL).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> GROWING_YELLOW_BALL_CACTUS = BLOCKS.register("growing_yellow_ball_cactus", () -> new GrowingBallCactusBlock(BallCactusColor.YELLOW, BlockBehaviour.Properties.of(Material.CACTUS).instabreak().sound(SoundType.WOOL).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> ORANGE_BALL_CACTUS = BLOCKS.register("orange_ball_cactus", () -> new BallCactusBlock(BallCactusColor.ORANGE, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> PINK_BALL_CACTUS = BLOCKS.register("pink_ball_cactus", () -> new BallCactusBlock(BallCactusColor.PINK, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> RED_BALL_CACTUS = BLOCKS.register("red_ball_cactus", () -> new BallCactusBlock(BallCactusColor.RED, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> YELLOW_BALL_CACTUS = BLOCKS.register("yellow_ball_cactus", () -> new BallCactusBlock(BallCactusColor.YELLOW, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> FLOWERING_ORANGE_BALL_CACTUS = BLOCKS.register("flowering_orange_ball_cactus", () -> new FloweringBallCactusBlock(BallCactusColor.ORANGE, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> FLOWERING_PINK_BALL_CACTUS = BLOCKS.register("flowering_pink_ball_cactus", () -> new FloweringBallCactusBlock(BallCactusColor.PINK, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> FLOWERING_RED_BALL_CACTUS = BLOCKS.register("flowering_red_ball_cactus", () -> new FloweringBallCactusBlock(BallCactusColor.RED, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> FLOWERING_YELLOW_BALL_CACTUS = BLOCKS.register("flowering_yellow_ball_cactus", () -> new FloweringBallCactusBlock(BallCactusColor.YELLOW, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> POTTED_ORANGE_BALL_CACTUS_FLOWER = BLOCKS.register("potted_orange_ball_cactus_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ORANGE_BALL_CACTUS_FLOWER, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_PINK_BALL_CACTUS_FLOWER = BLOCKS.register("potted_pink_ball_cactus_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_BALL_CACTUS_FLOWER, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_RED_BALL_CACTUS_FLOWER = BLOCKS.register("potted_red_ball_cactus_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_BALL_CACTUS_FLOWER, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_YELLOW_BALL_CACTUS_FLOWER = BLOCKS.register("potted_yellow_ball_cactus_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, YELLOW_BALL_CACTUS_FLOWER, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_ORANGE_BALL_CACTUS = BLOCKS.register("potted_orange_ball_cactus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ORANGE_BALL_CACTUS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_PINK_BALL_CACTUS = BLOCKS.register("potted_pink_ball_cactus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_BALL_CACTUS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_RED_BALL_CACTUS = BLOCKS.register("potted_red_ball_cactus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_BALL_CACTUS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_YELLOW_BALL_CACTUS = BLOCKS.register("potted_yellow_ball_cactus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, YELLOW_BALL_CACTUS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM = BLOCKS.register("fairy_ring_mushroom", () -> new FairyRingMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_YELLOW).noCollission().instabreak().sound(SoundType.GRASS).hasPostProcess((state, reader, pos) -> true).lightLevel((state) -> 11 + state.getValue(FairyRingMushroomBlock.MUSHROOMS)).noOcclusion().randomTicks()));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_BLOCK = BLOCKS.register("fairy_ring_mushroom_block", () -> new HugeFairyRingMushroomBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).lightLevel((state) -> 15).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_STEM = BLOCKS.register("fairy_ring_mushroom_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.QUARTZ).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRYLIGHT = BLOCKS.register("fairylight", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.SAND).strength(1.0F).sound(SoundType.SHROOMLIGHT).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> POTTED_FAIRY_RING_MUSHROOM = BLOCKS.register("potted_fairy_ring_mushroom", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FAIRY_RING_MUSHROOM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).lightLevel((state) -> 12)));
    public static final RegistryObject<Block> FAIRY_SPORE_LANTERN = BLOCKS.register("fairy_spore_lantern", () -> new FairySporeLanternBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((state) -> 12).noOcclusion()));

    public static final RegistryObject<Block> STRIPPED_FAIRY_RING_MUSHROOM_STEM = BLOCKS.register("stripped_fairy_ring_mushroom_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F).sound(SoundType.STEM)));
    public static final RegistryObject<Block> ENHANCED_FAIRY_RING_MUSHROOM_STEM = BLOCKS.register("enhanced_fairy_ring_mushroom_stem", () -> new LogBlock(STRIPPED_FAIRY_RING_MUSHROOM_STEM, BlockBehaviour.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.COLOR_YELLOW : MaterialColor.QUARTZ).strength(2.0F).sound(SoundType.STEM)));
    public static final RegistryObject<Block> STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE = BLOCKS.register("stripped_fairy_ring_mushroom_hyphae", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F).sound(SoundType.STEM)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_HYPHAE = BLOCKS.register("fairy_ring_mushroom_hyphae", () -> new LogBlock(STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.QUARTZ).strength(2.0F).sound(SoundType.STEM)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_PLANKS = BLOCKS.register("fairy_ring_mushroom_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_SLAB = BLOCKS.register("fairy_ring_mushroom_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_STAIRS = BLOCKS.register("fairy_ring_mushroom_stairs", () -> new StairBlock(() -> FAIRY_RING_MUSHROOM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_PRESSURE_PLATE = BLOCKS.register("fairy_ring_mushroom_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_FENCE = BLOCKS.register("fairy_ring_mushroom_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_FENCE_GATE = BLOCKS.register("fairy_ring_mushroom_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_BUTTON = BLOCKS.register("fairy_ring_mushroom_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_TRAPDOOR = BLOCKS.register("fairy_ring_mushroom_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, reader, pos, entity) -> false)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_DOOR = BLOCKS.register("fairy_ring_mushroom_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_SIGN = BLOCKS.register("fairy_ring_mushroom_sign", () -> new HabitatStandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).noCollission().strength(1.0F).sound(SoundType.WOOD), HabitatWoodType.FAIRY_RING_MUSHROOM));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_WALL_SIGN = BLOCKS.register("fairy_ring_mushroom_wall_sign", () -> new HabitatWallSignBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(FAIRY_RING_MUSHROOM_SIGN), HabitatWoodType.FAIRY_RING_MUSHROOM));

    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_VERTICAL_SLAB = BLOCKS.register("fairy_ring_mushroom_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_BOOKSHELF = BLOCKS.register("fairy_ring_mushroom_bookshelf", () -> new BookshelfBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(1.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_LADDER = BLOCKS.register("fairy_ring_mushroom_ladder", () -> new LadderBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.4F).sound(SoundType.LADDER).noOcclusion()));
    public static final RegistryObject<Block> STRIPPED_FAIRY_RING_MUSHROOM_POST = BLOCKS.register("stripped_fairy_ring_mushroom_post", () -> new WoodPostBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_POST = BLOCKS.register("fairy_ring_mushroom_post", () -> new WoodPostBlock(STRIPPED_FAIRY_RING_MUSHROOM_POST, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_CHEST = BLOCKS.register("fairy_ring_mushroom_chest", () -> new HabitatChestBlock(ChestVariant.FAIY_RING_MUSHROOM_NORMAL, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_TRAPPED_CHEST = BLOCKS.register("fairy_ring_mushroom_trapped_chest", () -> new HabitatTrappedChestBlock(ChestVariant.FAIY_RING_MUSHROOM_TRAPPED, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_BEEHIVE = BLOCKS.register("fairy_ring_mushroom_beehive", () -> new HabitatBeehiveBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(0.3F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FAIRY_RING_MUSHROOM_CABINET = BLOCKS.register("fairy_ring_mushroom_cabinet", () -> new HabitatCabinetBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));

    public static final RegistryObject<Block> EDELWEISS_SHRUB = BLOCKS.register("edelweiss_shrub", () -> new EdelweissShrubBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion().randomTicks().offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> GROWN_EDELWEISS_SHRUB = BLOCKS.register("grown_edelweiss_shrub", () -> new GrownEdelweissShrubBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion().offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> EDELWEISS = BLOCKS.register("edelweiss", () -> new FlowerBlock(HabitatEffects.PROLONGATION, 8, BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().noOcclusion().offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> POTTED_EDELWEISS = BLOCKS.register("potted_edelweiss", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, EDELWEISS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> BALL_CACTUS_BLOCK = BLOCKS.register("ball_cactus_block", () -> new HugeBallCactusBlock(BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> FLOWERING_ORANGE_BALL_CACTUS_BLOCK = BLOCKS.register("flowering_orange_ball_cactus_block", () -> new HugeFloweringBallCactusBlock(BallCactusColor.ORANGE, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> FLOWERING_PINK_BALL_CACTUS_BLOCK = BLOCKS.register("flowering_pink_ball_cactus_block", () -> new HugeFloweringBallCactusBlock(BallCactusColor.PINK, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> FLOWERING_RED_BALL_CACTUS_BLOCK = BLOCKS.register("flowering_red_ball_cactus_block", () -> new HugeFloweringBallCactusBlock(BallCactusColor.RED, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> FLOWERING_YELLOW_BALL_CACTUS_BLOCK = BLOCKS.register("flowering_yellow_ball_cactus_block", () -> new HugeFloweringBallCactusBlock(BallCactusColor.YELLOW, BlockBehaviour.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> DRIED_BALL_CACTUS_BLOCK = BLOCKS.register("dried_ball_cactus_block", () -> new Block(Block.Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> TALL_PURPLE_ANTHURIUM = BLOCKS.register("tall_purple_anthurium", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> TALL_RED_ANTHURIUM = BLOCKS.register("tall_red_anthurium", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> TALL_WHITE_ANTHURIUM = BLOCKS.register("tall_white_anthurium", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> TALL_YELLOW_ANTHURIUM = BLOCKS.register("tall_yellow_anthurium", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> PURPLE_ANTHURIUM = BLOCKS.register("purple_anthurium", () -> new GrowableFlowerBlock(TALL_PURPLE_ANTHURIUM, () -> MobEffects.POISON, 12, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> RED_ANTHURIUM = BLOCKS.register("red_anthurium", () -> new GrowableFlowerBlock(TALL_RED_ANTHURIUM, () -> MobEffects.POISON, 12, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> WHITE_ANTHURIUM = BLOCKS.register("white_anthurium", () -> new GrowableFlowerBlock(TALL_WHITE_ANTHURIUM, () -> MobEffects.POISON, 12, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> YELLOW_ANTHURIUM = BLOCKS.register("yellow_anthurium", () -> new GrowableFlowerBlock(TALL_YELLOW_ANTHURIUM, () -> MobEffects.POISON, 12, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));

    public static final RegistryObject<Block> POTTED_PURPLE_ANTHURIUM = BLOCKS.register("potted_purple_anthurium", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PURPLE_ANTHURIUM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_RED_ANTHURIUM = BLOCKS.register("potted_red_anthurium", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_ANTHURIUM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_WHITE_ANTHURIUM = BLOCKS.register("potted_white_anthurium", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHITE_ANTHURIUM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_YELLOW_ANTHURIUM = BLOCKS.register("potted_yellow_anthurium", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, YELLOW_ANTHURIUM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_TALL_PURPLE_ANTHURIUM = BLOCKS.register("potted_tall_purple_anthurium", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TALL_PURPLE_ANTHURIUM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_TALL_RED_ANTHURIUM = BLOCKS.register("potted_tall_red_anthurium", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TALL_RED_ANTHURIUM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_TALL_WHITE_ANTHURIUM = BLOCKS.register("potted_tall_white_anthurium", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TALL_WHITE_ANTHURIUM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> POTTED_TALL_YELLOW_ANTHURIUM = BLOCKS.register("potted_tall_yellow_anthurium", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TALL_YELLOW_ANTHURIUM, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> HAUNTING_DREADBUD = BLOCKS.register("haunting_dreadbud", () -> new DamagingDreadbudBlock(2.0F, null, 0.0F, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> UNNERVING_DREADBUD = BLOCKS.register("unnerving_dreadbud", () -> new DamagingDreadbudBlock(1.0F, HAUNTING_DREADBUD, 4.0F, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> FADING_DREADBUD = BLOCKS.register("fading_dreadbud", () -> new DreadbudBlock(UNNERVING_DREADBUD, 3.25F, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> BLOOMING_DREADBUD = BLOCKS.register("blooming_dreadbud", () -> new BloomingDreadbudBlock(FADING_DREADBUD, 2.5F, () -> MobEffects.HARM, 7, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> POTTED_BLOOMING_DREADBUD = BLOCKS.register("potted_blooming_dreadbud", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLOOMING_DREADBUD, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
}
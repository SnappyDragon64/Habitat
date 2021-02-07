package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.block.*;
import mod.schnappdragon.bloom_and_gloom.common.misc.BallCactusColor;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.util.CompatHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<Block> RAFFLESIA = BLOCKS.register("rafflesia", () -> new RafflesiaBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.RED).zeroHardnessAndResistance().sound(SoundType.PLANT).tickRandomly().doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> POTTED_RAFFLESIA = CompatHelper.registerCompat("quark", () -> BLOCKS.register("potted_rafflesia", () -> new FlowerPotBlock(RAFFLESIA.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid())));

    public static final RegistryObject<Block> KABLOOM_BUSH = BLOCKS.register("kabloom_bush", () -> new KabloomBushBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).tickRandomly().doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> POTTED_KABLOOM_BUSH = CompatHelper.registerCompat("quark", () -> BLOCKS.register("potted_kabloom_bush", () -> new FlowerPotBlock(KABLOOM_BUSH.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid())));

    public static final RegistryObject<Block> SLIME_FERN = BLOCKS.register("slime_fern", () -> new SlimeFernBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> WALL_SLIME_FERN = BLOCKS.register("wall_slime_fern", () -> new WallSlimeFernBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> POTTED_SLIME_FERN = BLOCKS.register("potted_slime_fern", () -> new FlowerPotBlock(SLIME_FERN.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));

    public  static final  RegistryObject<Block> ORANGE_BALL_CACTUS_FLOWER = BLOCKS.register("orange_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.ORANGE, BGEffects.PRICKLING, 5, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> PINK_BALL_CACTUS_FLOWER = BLOCKS.register("pink_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.PINK, BGEffects.PRICKLING, 5, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> RED_BALL_CACTUS_FLOWER = BLOCKS.register("red_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.RED, BGEffects.PRICKLING, 5, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> YELLOW_BALL_CACTUS_FLOWER = BLOCKS.register("yellow_ball_cactus_flower", () -> new BallCactusFlowerBlock(BallCactusColor.YELLOW, BGEffects.PRICKLING, 5, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> ORANGE_BALL_CACTUS_SEEDLING = BLOCKS.register("orange_ball_cactus_seedling", () -> new BallCactusSeedlingBlock(BallCactusColor.ORANGE, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.PLANT).notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> PINK_BALL_CACTUS_SEEDLING = BLOCKS.register("pink_ball_cactus_seedling", () -> new BallCactusSeedlingBlock(BallCactusColor.PINK, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.PLANT).notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> RED_BALL_CACTUS_SEEDLING = BLOCKS.register("red_ball_cactus_seedling", () -> new BallCactusSeedlingBlock(BallCactusColor.RED, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.PLANT).notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> YELLOW_BALL_CACTUS_SEEDLING = BLOCKS.register("yellow_ball_cactus_seedling", () -> new BallCactusSeedlingBlock(BallCactusColor.YELLOW, AbstractBlock.Properties.create(Material.CACTUS).zeroHardnessAndResistance().sound(SoundType.PLANT).notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> ORANGE_BALL_CACTUS = BLOCKS.register("orange_ball_cactus", () -> new BallCactusBlock(BallCactusColor.ORANGE, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.PLANT).notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> PINK_BALL_CACTUS = BLOCKS.register("pink_ball_cactus", () -> new BallCactusBlock(BallCactusColor.PINK, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.PLANT).notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> RED_BALL_CACTUS = BLOCKS.register("red_ball_cactus", () -> new BallCactusBlock(BallCactusColor.RED, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.PLANT).notSolid().tickRandomly()));
    public  static final  RegistryObject<Block> YELLOW_BALL_CACTUS = BLOCKS.register("yellow_ball_cactus", () -> new BallCactusBlock(BallCactusColor.YELLOW, AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(1).sound(SoundType.PLANT).notSolid().tickRandomly()));
}
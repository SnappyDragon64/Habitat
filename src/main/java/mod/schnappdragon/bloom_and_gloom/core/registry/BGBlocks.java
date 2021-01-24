package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.block.KabloomBushBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.SlimeFernBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.WallSlimeFernBlock;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.common.block.RafflesiaBlock;
import mod.schnappdragon.bloom_and_gloom.core.util.CompatHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<Block> RAFFLESIA = BLOCKS.register("rafflesia", RafflesiaBlock::new);
    public static final RegistryObject<Block> KABLOOM_BUSH = BLOCKS.register("kabloom_bush", KabloomBushBlock::new);
    public static final RegistryObject<Block> SLIME_FERN = BLOCKS.register("slime_fern", SlimeFernBlock::new);
    public static final RegistryObject<Block> WALL_SLIME_FERN = BLOCKS.register("wall_slime_fern", WallSlimeFernBlock::new);

    public static final RegistryObject<Block> POTTED_RAFFLESIA = CompatHelper.registerCompat("quark", () -> BLOCKS.register("potted_rafflesia", () -> new FlowerPotBlock(RAFFLESIA.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid())));
    public static final RegistryObject<Block> POTTED_KABLOOM_BUSH = CompatHelper.registerCompat("quark", () -> BLOCKS.register("potted_kabloom_bush", () -> new FlowerPotBlock(KABLOOM_BUSH.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid())));
    public static final RegistryObject<Block> POTTED_SLIME_FERN = BLOCKS.register("potted_slime_fern", () -> new FlowerPotBlock(SLIME_FERN.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
}
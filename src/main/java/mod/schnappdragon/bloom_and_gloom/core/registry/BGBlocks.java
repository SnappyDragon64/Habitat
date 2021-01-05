package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.block.KabloomBushBlock;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.common.block.RafflesiaBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<Block> RAFFLESIA_BLOCK = BLOCKS.register("rafflesia", RafflesiaBlock::new);
    public static final RegistryObject<Block> KABLOOM_BUSH_BLOCK = BLOCKS.register("kabloom_bush", KabloomBushBlock::new);
}
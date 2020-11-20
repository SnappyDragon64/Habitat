package mod.schnappdragon.test.core.registry;

import mod.schnappdragon.test.core.Test;
import mod.schnappdragon.test.common.block.RafflesiaBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Test.MOD_ID);

    public static final RegistryObject<Block> RAFFLESIA_BLOCK = BLOCKS.register("rafflesia", RafflesiaBlock::new);
}

package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.common.tileentity.RafflesiaTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BloomAndGloom.MOD_ID);

    public static final RegistryObject<TileEntityType<RafflesiaTileEntity>> RAFFLESIA_TILE = TILE_ENTITIES.register("rafflesia",
            () -> TileEntityType.Builder.create(RafflesiaTileEntity::new, ModBlocks.RAFFLESIA_BLOCK.get()).build(null));
}

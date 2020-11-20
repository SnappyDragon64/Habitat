package mod.schnappdragon.test.core.registry;

import mod.schnappdragon.test.core.Test;
import mod.schnappdragon.test.common.tileentity.RafflesiaTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Test.MOD_ID);

    public static final RegistryObject<TileEntityType<RafflesiaTileEntity>> RAFFLESIA_TILE = TILE_ENTITIES.register("rafflesia",
            () -> TileEntityType.Builder.create(RafflesiaTileEntity::new, ModBlocks.RAFFLESIA_BLOCK.get()).build(null));
}

package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.common.tileentity.RafflesiaTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Habitat.MOD_ID);

    public static final RegistryObject<TileEntityType<RafflesiaTileEntity>> RAFFLESIA = TILE_ENTITY_TYPES.register("rafflesia",
            () -> TileEntityType.Builder.create(RafflesiaTileEntity::new, HabitatBlocks.RAFFLESIA.get()).build(null));
}
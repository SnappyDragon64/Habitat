package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.tileentity.*;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Habitat.MOD_ID);

    public static final RegistryObject<TileEntityType<RafflesiaTileEntity>> RAFFLESIA = TILE_ENTITY_TYPES.register("rafflesia",
            () -> TileEntityType.Builder.create(RafflesiaTileEntity::new, HabitatBlocks.RAFFLESIA.get()).build(null));
    public static final RegistryObject<TileEntityType<HabitatSignTileEntity>> SIGN = TILE_ENTITY_TYPES.register("sign",
            () -> TileEntityType.Builder.create(HabitatSignTileEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_SIGN.get(), HabitatBlocks.FAIRY_RING_MUSHROOM_WALL_SIGN.get()).build(null));
    public static final RegistryObject<TileEntityType<HabitatBeehiveTileEntity>> BEEHIVE = TILE_ENTITY_TYPES.register("beehive",
            () -> TileEntityType.Builder.create(HabitatBeehiveTileEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE.get()).build(null));
    public static final RegistryObject<TileEntityType<HabitatChestTileEntity>> CHEST = TILE_ENTITY_TYPES.register("chest",
            () -> TileEntityType.Builder.create(HabitatChestTileEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_CHEST.get()).build(null));
    public static final RegistryObject<TileEntityType<HabitatTrappedChestTileEntity>> TRAPPED_CHEST = TILE_ENTITY_TYPES.register("trapped_chest",
            () -> TileEntityType.Builder.create(HabitatTrappedChestTileEntity::new, HabitatBlocks.TRAPPED_FAIRY_RING_MUSHROOM_CHEST.get()).build(null));
}
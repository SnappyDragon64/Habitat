package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.tileentity.*;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatTileEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Habitat.MODID);

    public static final RegistryObject<BlockEntityType<RafflesiaTileEntity>> RAFFLESIA = TILE_ENTITY_TYPES.register("rafflesia",
            () -> BlockEntityType.Builder.of(RafflesiaTileEntity::new, HabitatBlocks.RAFFLESIA.get()).build(null));
    public static final RegistryObject<BlockEntityType<HabitatSignTileEntity>> SIGN = TILE_ENTITY_TYPES.register("sign",
            () -> BlockEntityType.Builder.of(HabitatSignTileEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_SIGN.get(), HabitatBlocks.FAIRY_RING_MUSHROOM_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<HabitatBeehiveTileEntity>> BEEHIVE = TILE_ENTITY_TYPES.register("beehive",
            () -> BlockEntityType.Builder.of(HabitatBeehiveTileEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<HabitatChestTileEntity>> CHEST = TILE_ENTITY_TYPES.register("chest",
            () -> BlockEntityType.Builder.of(HabitatChestTileEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<HabitatTrappedChestTileEntity>> TRAPPED_CHEST = TILE_ENTITY_TYPES.register("trapped_chest",
            () -> BlockEntityType.Builder.of(HabitatTrappedChestTileEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_TRAPPED_CHEST.get()).build(null));
}
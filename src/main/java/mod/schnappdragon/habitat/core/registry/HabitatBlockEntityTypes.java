package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.block.entity.*;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Habitat.MODID);

    public static final RegistryObject<BlockEntityType<RafflesiaBlockEntity>> RAFFLESIA = TILE_ENTITY_TYPES.register("rafflesia",
            () -> BlockEntityType.Builder.of(RafflesiaBlockEntity::new, HabitatBlocks.RAFFLESIA.get()).build(null));
    public static final RegistryObject<BlockEntityType<HabitatSignBlockEntity>> SIGN = TILE_ENTITY_TYPES.register("sign",
            () -> BlockEntityType.Builder.of(HabitatSignBlockEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_SIGN.get(), HabitatBlocks.FAIRY_RING_MUSHROOM_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<HabitatBeehiveBlockEntity>> BEEHIVE = TILE_ENTITY_TYPES.register("beehive",
            () -> BlockEntityType.Builder.of(HabitatBeehiveBlockEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<HabitatChestBlockEntity>> CHEST = TILE_ENTITY_TYPES.register("chest",
            () -> BlockEntityType.Builder.of(HabitatChestBlockEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<HabitatTrappedChestBlockEntity>> TRAPPED_CHEST = TILE_ENTITY_TYPES.register("trapped_chest",
            () -> BlockEntityType.Builder.of(HabitatTrappedChestBlockEntity::new, HabitatBlocks.FAIRY_RING_MUSHROOM_TRAPPED_CHEST.get()).build(null));
}
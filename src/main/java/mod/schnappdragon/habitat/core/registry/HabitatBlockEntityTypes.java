package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.block.entity.*;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HabitatBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Habitat.MODID);

    public static final RegistryObject<BlockEntityType<RafflesiaBlockEntity>> RAFFLESIA = BLOCK_ENTITY_TYPES.register("rafflesia",
            () -> BlockEntityType.Builder.of(RafflesiaBlockEntity::new, HabitatBlocks.RAFFLESIA.get()).build(null));
}
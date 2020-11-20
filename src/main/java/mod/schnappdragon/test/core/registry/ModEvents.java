package mod.schnappdragon.test.core.registry;

import mod.schnappdragon.test.core.Test;
import mod.schnappdragon.test.common.entity.ai.goal.AvoidRafflesiaGoal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static mod.schnappdragon.test.common.block.RafflesiaBlock.*;

@Mod.EventBusSubscriber(modid = Test.MOD_ID)
public class ModEvents {

    /*
     * Entity Events
     */

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entityIn = event.getEntity();

        if (entityIn instanceof CreatureEntity && !(entityIn instanceof MonsterEntity) || entityIn instanceof AbstractRaiderEntity || entityIn instanceof PiglinEntity || entityIn instanceof EndermanEntity) {
            CreatureEntity creatureIn = (CreatureEntity) event.getEntity();

            creatureIn.goalSelector.addGoal(3, new AvoidRafflesiaGoal<>(creatureIn, 4, 1.0D, 1.2D));
        }
    }

    /*
     * Villager Trades Events
     */

    @SubscribeEvent
    public static void onWandererTradesEvent(WandererTradesEvent event) {
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(ModItems.RAFFLESIA_SEED_POD.get(), 1), 12, 10, 1));
    }

    /*
     * World Events
     */

    @SubscribeEvent
    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.JUNGLE)
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> ModFeatures.RAFFLESIA_PATCH);
    }

    @SubscribeEvent
    public static void onBlockEvent(BlockEvent event) {
        if (event.getState().getBlock().matchesBlock(ModBlocks.RAFFLESIA_BLOCK.get()) && event.getState().get(AGE) < 2)  // For compatibility with mods with reverse-bonemeal & debug sticks.
            event.getWorld().setBlockState(event.getPos(), event.getState().with(COOLDOWN, false).with(STEW, false).with(POLLINATED, false), 2);
    }
}
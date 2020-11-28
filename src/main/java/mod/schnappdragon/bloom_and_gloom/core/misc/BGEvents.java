package mod.schnappdragon.bloom_and_gloom.core.misc;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.common.entity.ai.goal.AvoidRafflesiaGoal;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import mod.schnappdragon.bloom_and_gloom.common.world.gen.BGFeatures;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
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

import static mod.schnappdragon.bloom_and_gloom.common.block.RafflesiaBlock.*;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class BGEvents {

    /*
     * Entity Events
     */

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entityIn = event.getEntity();

        if (entityIn instanceof CreatureEntity && !(entityIn instanceof MonsterEntity) || entityIn instanceof AbstractRaiderEntity || entityIn instanceof PiglinEntity || entityIn instanceof EndermanEntity) {
            CreatureEntity creatureIn = (CreatureEntity) event.getEntity();
            int range = 8;
            double speed = 1;
            if (entityIn instanceof EndermanEntity)
                range = 1;
            else if (entityIn instanceof HoglinEntity || entityIn instanceof PiglinEntity || entityIn instanceof RavagerEntity)
                speed = 0.5;
            creatureIn.goalSelector.addGoal(3, new AvoidRafflesiaGoal<>(creatureIn, range, speed, 1.2D * speed));
        }
    }

    /*
     * Villager Trades Events
     */

    @SubscribeEvent
    public static void onWandererTradesEvent(WandererTradesEvent event) {
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(BGItems.RAFFLESIA_SEED.get(), 1), 12, 10, 1));
    }

    /*
     * World Events
     */

    @SubscribeEvent
    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.JUNGLE)
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> BGFeatures.RAFFLESIA_PATCH);
    }

    @SubscribeEvent
    public static void onBlockEvent(BlockEvent event) {
        if (event.getState().getBlock().matchesBlock(BGBlocks.RAFFLESIA_BLOCK.get()) && event.getState().get(AGE) < 2)
            event.getWorld().setBlockState(event.getPos(), event.getState().with(COOLDOWN, false).with(STEW, false).with(POLLINATED, false), 2);
    }
}
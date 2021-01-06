package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.common.entity.ai.goal.AvoidRafflesiaGoal;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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

        if (entityIn instanceof CreatureEntity && !(entityIn instanceof MonsterEntity) && !(entityIn instanceof WaterMobEntity) || entityIn instanceof AbstractRaiderEntity || entityIn instanceof PiglinEntity || entityIn instanceof EndermanEntity) {
            CreatureEntity creatureIn = (CreatureEntity) event.getEntity();
            int range = 8;
            double speed = 1;
            if (entityIn instanceof EndermanEntity || entityIn instanceof WanderingTraderEntity)
                range = 1;
            else if (entityIn instanceof HoglinEntity || entityIn instanceof PiglinEntity || entityIn instanceof RavagerEntity)
                speed = 0.5;
            creatureIn.goalSelector.addGoal(3, new AvoidRafflesiaGoal<>(creatureIn, range, speed, 1.2D * speed));
        }
    }

    /*
     * World Events
     */

    @SubscribeEvent
    public static void onBlockEvent(BlockEvent event) {
        if (event.getState().getBlock().matchesBlock(BGBlocks.RAFFLESIA.get()) && event.getState().get(AGE) < 2)
            event.getWorld().setBlockState(event.getPos(), event.getState().with(COOLDOWN, false).with(STEW, false).with(POLLINATED, false), 2);
    }
}
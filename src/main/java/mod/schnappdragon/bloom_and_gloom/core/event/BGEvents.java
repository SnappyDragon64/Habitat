package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.common.entity.ai.goal.BGFindPollinationTargetGoal;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class BGEvents {

    /*
     * Used to add BGFindPollinationTargetGoal to bees on spawn.
     */

    @SubscribeEvent
    public static void addPollinationGoal(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.BEE) {
            BeeEntity bee = (BeeEntity) entity;
            bee.goalSelector.addGoal(7, new BGFindPollinationTargetGoal(bee));
        }
    }

    /*
     * Used to reduce explosion damage if livingentity has blast resistance.
     */

    @SubscribeEvent
    public static void reduceExplosionDamage(LivingDamageEvent event) {
        if (event.getEntityLiving().isPotionActive(BGEffects.BLAST_RESISTANCE.get()) && event.getSource().isExplosion()) {
            float dmg = MathHelper.floor(event.getAmount() * 0.92F);
            int res = Math.round(event.getAmount() - dmg);

            if (res > 0) {
                event.setAmount(dmg);

                LivingEntity livingEntity = event.getEntityLiving();
                DamageSource source = event.getSource();

                if (livingEntity instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) livingEntity).addStat(Stats.DAMAGE_RESISTED, res * 10);
                } else if (source.getTrueSource() instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) source.getTrueSource()).addStat(Stats.DAMAGE_DEALT_RESISTED, res * 10);
                }
            }
        }
    }
}
package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.common.entity.ai.goal.HabitatFindPollinationTargetGoal;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatEffects;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID)
public class HabitatEvents {

    /*
     * Used to add HabitatFindPollinationTargetGoal to bees on spawn.
     */

    @SubscribeEvent
    public static void addPollinationGoal(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.BEE) {
            BeeEntity bee = (BeeEntity) entity;
            bee.goalSelector.addGoal(7, new HabitatFindPollinationTargetGoal(bee));
        }
    }

    /*
     * Used to reduce explosion damage if livingentity has Blast Endurance effect.
     */

    @SubscribeEvent
    public static void reduceExplosionDamage(LivingDamageEvent event) {
        if (event.getEntityLiving().isPotionActive(HabitatEffects.BLAST_ENDURANCE.get()) && event.getSource().isExplosion()) {
            LivingEntity livingEntity = event.getEntityLiving();
            DamageSource source = event.getSource();

            int lvl = Math.min(livingEntity.getActivePotionEffect(HabitatEffects.BLAST_ENDURANCE.get()).getAmplifier(), 11);
            float dmg = MathHelper.floor(event.getAmount() * (0.88F - 0.08F * lvl));
            int res = (int) (event.getAmount() - dmg);

            event.setAmount(dmg);

            if (livingEntity instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) livingEntity).addStat(Stats.DAMAGE_RESISTED, res * 10);
            } else if (source.getTrueSource() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) source.getTrueSource()).addStat(Stats.DAMAGE_DEALT_RESISTED, res * 10);
            }
        }
    }

    /*
     * Used to inflict damage if livingentity has Prickling effect.
     */

    @SubscribeEvent
    public static void causePricklingDamage(LivingHurtEvent event) {
        if (event.getEntityLiving().isPotionActive(HabitatEffects.PRICKLING.get())) {
            LivingEntity livingEntity = event.getEntityLiving();
            DamageSource source = event.getSource();
            int lvl = livingEntity.getActivePotionEffect(HabitatEffects.PRICKLING.get()).getAmplifier();

            if (livingEntity.getRNG().nextInt(4) < 2 + lvl && !source.isMagicDamage() && !source.isExplosion() && (source.getImmediateSource() instanceof LivingEntity)) {
                LivingEntity attacker = (LivingEntity) source.getImmediateSource();
                attacker.attackEntityFrom(DamageSource.causeThornsDamage(livingEntity), 1.0F + (lvl > 0 ? livingEntity.getRNG().nextInt(lvl) : 0));
            }
        }
    }
}
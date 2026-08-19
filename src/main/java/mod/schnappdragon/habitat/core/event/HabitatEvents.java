package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatEffects;
import mod.schnappdragon.habitat.core.tags.HabitatDamageTypeTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HabitatEvents {
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (livingEntity.hasEffect(HabitatEffects.PRICKLING.get())) {
            if (event.getSource().is(HabitatDamageTypeTags.PRICKLING_IMMUNE_TO)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (livingEntity.hasEffect(HabitatEffects.PRICKLING.get())) {
            DamageSource source = event.getSource();
            Entity directEntity = source.getDirectEntity();
            int lvl = livingEntity.getEffect(HabitatEffects.PRICKLING.get()).getAmplifier();

            if (directEntity != null && !source.is(HabitatDamageTypeTags.AVOIDS_PRICKLING) && livingEntity.getRandom().nextInt(4) < 1 + lvl) {
                directEntity.hurt(livingEntity.damageSources().thorns(livingEntity), 1.0F + livingEntity.getRandom().nextInt(1 + 2 * lvl));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (livingEntity.hasEffect(HabitatEffects.BLAST_ENDURANCE.get())) {
            if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
                DamageSource source = event.getSource();

                int lvl = Math.min(livingEntity.getEffect(HabitatEffects.BLAST_ENDURANCE.get()).getAmplifier(), 4);
                float dmg = Mth.floor(event.getAmount() * (0.40F - 0.20F * lvl));
                int res = (int) (event.getAmount() - dmg);

                event.setAmount(dmg);

                if (livingEntity instanceof ServerPlayer) {
                    ((ServerPlayer) livingEntity).awardStat(Stats.DAMAGE_RESISTED, res * 10);
                } else if (source.getEntity() instanceof ServerPlayer) {
                    ((ServerPlayer) source.getEntity()).awardStat(Stats.DAMAGE_DEALT_RESISTED, res * 10);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        if (event.getEntity().hasEffect(HabitatEffects.PROLONGATION.get())) {
            MobEffectInstance incoming = event.getEffectInstance();
            MobEffectInstance prolonged = new MobEffectInstance(incoming.getEffect(), Mth.ceil(incoming.getDuration() * 2.0));
            incoming.update(prolonged);
        }
    }
    
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cat cat) {
            cat.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(cat, Passerine.class, false, null));
        } else if (event.getEntity() instanceof Ocelot ocelot) {
            ocelot.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(ocelot, Passerine.class, false));
        } else if (event.getEntity() instanceof Fox fox) {
            fox.targetSelector.addGoal(fox.getVariant() == Fox.Type.RED ? 4 : 6, new NearestAttackableTargetGoal<>(fox, Passerine.class, false));
        }
    }
}

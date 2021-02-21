package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class BGEffectEvents {

    /*
     * Used to reduce explosion damage if livingentity has blast endurance.
     */

    @SubscribeEvent
    public static void reduceExplosionDamage(LivingDamageEvent event) {
        if (event.getEntityLiving().isPotionActive(BGEffects.BLAST_ENDURANCE.get()) && event.getSource().isExplosion()) {
            LivingEntity livingEntity = event.getEntityLiving();
            DamageSource source = event.getSource();

            int lvl = Math.min(livingEntity.getActivePotionEffect(BGEffects.BLAST_ENDURANCE.get()).getAmplifier(), 11);
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
     * Used to inflict damage if livingentity has prickling.
     */

    @SubscribeEvent
    public static void causePricklingDamage(LivingHurtEvent event) {
        if (event.getEntityLiving().isPotionActive(BGEffects.PRICKLING.get())) {
            LivingEntity livingEntity = event.getEntityLiving();
            DamageSource source = event.getSource();
            int lvl = livingEntity.getActivePotionEffect(BGEffects.PRICKLING.get()).getAmplifier();

            if (livingEntity.getRNG().nextInt(5) < 2 + lvl && !source.isMagicDamage() && !source.isExplosion() && (source.getImmediateSource() instanceof LivingEntity)) {
                LivingEntity attacker = (LivingEntity) source.getImmediateSource();
                attacker.attackEntityFrom(DamageSource.causeThornsDamage(livingEntity), 1.0F + (lvl > 0 ? livingEntity.getRNG().nextInt(lvl) : 0));
            }
        }
    }
}

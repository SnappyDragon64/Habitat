package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatEffects;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashSet;

@Mod.EventBusSubscriber(modid = Habitat.MODID)
public class HabitatEvents {

    /*
     * Used to registry replace Beehive PoiType
     */

    @SubscribeEvent
    public static void addToBeehivePoiType(RegisterEvent event) {
        ResourceLocation beePOIRL = new ResourceLocation("minecraft:bee_nest");
        PoiType beePOI = ForgeRegistries.POI_TYPES.getValue(beePOIRL);
        HashSet<BlockState> newSet = new HashSet<>(beePOI.matchingStates());
        newSet.addAll(HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE.get().getStateDefinition().getPossibleStates());
        event.register(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, beePOIRL, () -> new PoiType(newSet, beePOI.validRange(),  beePOI.maxTickets()));
    }

    /*
     * Used to reduce explosion damage if LivingEntity has Blast Endurance effect.
     */

    @SubscribeEvent
    public static void reduceExplosionDamage(LivingDamageEvent event) {
        if (event.getEntity().hasEffect(HabitatEffects.BLAST_ENDURANCE.get()) && event.getSource().isExplosion()) {
            LivingEntity livingEntity = event.getEntity();
            DamageSource source = event.getSource();

            int lvl = Math.min(livingEntity.getEffect(HabitatEffects.BLAST_ENDURANCE.get()).getAmplifier(), 4);
            float dmg = Mth.floor(event.getAmount() * (0.80F - 0.20F * lvl));
            int res = (int) (event.getAmount() - dmg);

            event.setAmount(dmg);

            if (livingEntity instanceof ServerPlayer) {
                ((ServerPlayer) livingEntity).awardStat(Stats.DAMAGE_RESISTED, res * 10);
            } else if (source.getEntity() instanceof ServerPlayer) {
                ((ServerPlayer) source.getEntity()).awardStat(Stats.DAMAGE_DEALT_RESISTED, res * 10);
            }
        }
    }

    /*
     * Used to inflict damage to attacker if LivingEntity has Prickling effect.
     */

    @SubscribeEvent
    public static void causePricklingDamage(LivingHurtEvent event) {
        if (event.getEntity().hasEffect(HabitatEffects.PRICKLING.get())) {
            LivingEntity livingEntity = event.getEntity();
            DamageSource source = event.getSource();
            int lvl = livingEntity.getEffect(HabitatEffects.PRICKLING.get()).getAmplifier();

            if (livingEntity.getRandom().nextInt(4) < 2 + lvl && !source.isMagic() && !source.isExplosion() && source.getDirectEntity() instanceof LivingEntity attacker) {
                attacker.hurt(DamageSource.thorns(livingEntity), 1.0F + lvl + livingEntity.getRandom().nextInt(2 + lvl));
            }
        }
    }

    /*
     * Used to increase the duration of the incoming effect if livingentity has Prolongation.
     */

    @SubscribeEvent
    public static void prolongEffectDuration(MobEffectEvent.Added event) {
        if (event.getEntity().hasEffect(HabitatEffects.PROLONGATION.get())) {
            MobEffectInstance incoming = event.getEffectInstance();
            MobEffectInstance prolonged = new MobEffectInstance(incoming.getEffect(), Mth.ceil(incoming.getDuration() * 1.2));
            incoming.update(prolonged);
        }
    }
}
package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.common.entity.ai.goal.RabbitAvoidEntityGoal;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatEffects;
import mod.schnappdragon.habitat.core.tags.HabitatEntityTypeTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashSet;

@Mod.EventBusSubscriber(modid = Habitat.MODID)
public class HabitatEvents {

    /*
     * Used to goals to vanilla entities
     */

    @SubscribeEvent
    public static void addGoals(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.RABBIT && EntityType.RABBIT.getTags().toList().contains(HabitatEntityTypeTags.POOKA_ATTACK_TARGETS)) {
            Rabbit rabbit = (Rabbit) entity;
            rabbit.goalSelector.addGoal(4, new RabbitAvoidEntityGoal<>(rabbit, Pooka.class, 2.25F, 2.2D, 2.2D));
        }
    }

    /*
     * Used to reduce explosion damage if livingentity has Blast Endurance effect.
     */

    @SubscribeEvent
    public static void reduceExplosionDamage(LivingDamageEvent event) {
        if (event.getEntity().hasEffect(HabitatEffects.BLAST_ENDURANCE.get()) && event.getSource().isExplosion()) {
            LivingEntity livingEntity = event.getEntity();
            DamageSource source = event.getSource();

            int lvl = Math.min(livingEntity.getEffect(HabitatEffects.BLAST_ENDURANCE.get()).getAmplifier(), 5);
            float dmg = Mth.floor(event.getAmount() * (0.85F - 0.17F * lvl));
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
     * Used to inflict damage if livingentity has Prickling effect.
     */

    @SubscribeEvent
    public static void causePricklingDamage(LivingHurtEvent event) {
        if (event.getEntity().hasEffect(HabitatEffects.PRICKLING.get())) {
            LivingEntity livingEntity = event.getEntity();
            DamageSource source = event.getSource();
            int lvl = livingEntity.getEffect(HabitatEffects.PRICKLING.get()).getAmplifier();

            if (livingEntity.getRandom().nextInt(5) < 3 + lvl && !source.isMagic() && !source.isExplosion() && source.getDirectEntity() instanceof LivingEntity attacker) {
                attacker.hurt(DamageSource.thorns(livingEntity), 1.0F + lvl + livingEntity.getRandom().nextInt(2 + lvl));
            }
        }
    }

    @SubscribeEvent
    public static void addToBeehivePoiType(RegisterEvent event) {
        ResourceLocation beePOIRL = new ResourceLocation("minecraft:bee_nest");
        PoiType beePOI = ForgeRegistries.POI_TYPES.getValue(beePOIRL);
        HashSet<BlockState> newSet = new HashSet<>(beePOI.matchingStates());
        newSet.addAll(HabitatBlocks.FAIRY_RING_MUSHROOM_BEEHIVE.get().getStateDefinition().getPossibleStates());
        event.register(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, beePOIRL, () -> new PoiType(newSet, beePOI.validRange(),  beePOI.maxTickets()));
    }
}
package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.common.entity.ai.goal.HabitatFindPollinationTargetGoal;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
}
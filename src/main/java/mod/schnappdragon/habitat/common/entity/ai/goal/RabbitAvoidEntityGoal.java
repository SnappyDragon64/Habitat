package mod.schnappdragon.habitat.common.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Rabbit;

public class RabbitAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final Rabbit rabbit;

    public RabbitAvoidEntityGoal(Rabbit rabbit, Class<T> aClass, float range, double v1, double v2) {
        super(rabbit, aClass, range, v1, v2);
        this.rabbit = rabbit;
    }

    public boolean canUse() {
        return this.rabbit.getRabbitType() != 99 && super.canUse();
    }
}
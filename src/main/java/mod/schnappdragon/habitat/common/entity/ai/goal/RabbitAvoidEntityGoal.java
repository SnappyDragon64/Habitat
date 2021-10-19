package mod.schnappdragon.habitat.common.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.passive.RabbitEntity;

public class RabbitAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final RabbitEntity rabbit;

    public RabbitAvoidEntityGoal(RabbitEntity rabbit, Class<T> aClass, float range, double v1, double v2) {
        super(rabbit, aClass, range, v1, v2);
        this.rabbit = rabbit;
    }

    public boolean shouldExecute() {
        return this.rabbit.getRabbitType() != 99 && super.shouldExecute();
    }
}
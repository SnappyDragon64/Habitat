package mod.schnappdragon.bloom_and_gloom.common.entity.item;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class InvulnerableToKabloomExplosionItemEntity extends ItemEntity {
    public InvulnerableToKabloomExplosionItemEntity(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.getDamageType().equals("bloom_and_gloom.kabloom") || super.isInvulnerableTo(source);
    }
}

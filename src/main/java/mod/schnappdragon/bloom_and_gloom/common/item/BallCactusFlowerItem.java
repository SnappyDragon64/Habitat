package mod.schnappdragon.bloom_and_gloom.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.DamageSource;

public class BallCactusFlowerItem extends BlockItem {
    public BallCactusFlowerItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    public boolean isDamageable(DamageSource damageSource) {
        return !(damageSource == DamageSource.CACTUS) && super.isDamageable(damageSource);
    }
}
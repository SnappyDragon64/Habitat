package mod.schnappdragon.bloom_and_gloom.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.util.DamageSource;

public class BallCactusFlowerItem extends BlockNamedItem {
    public BallCactusFlowerItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    public boolean isDamageable(DamageSource damageSource) {
        return !(damageSource == DamageSource.CACTUS) && super.isDamageable(damageSource);
    }
}

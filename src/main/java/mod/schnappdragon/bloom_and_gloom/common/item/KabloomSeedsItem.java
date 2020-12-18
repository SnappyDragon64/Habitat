package mod.schnappdragon.bloom_and_gloom.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.util.DamageSource;

import javax.annotation.ParametersAreNonnullByDefault;

public class KabloomSeedsItem extends BlockNamedItem {

    public KabloomSeedsItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isDamageable(DamageSource damageSource) {
        return (!damageSource.getDamageType().equals("bloom_and_gloom.kabloom")) && super.isDamageable(damageSource);
    }
}
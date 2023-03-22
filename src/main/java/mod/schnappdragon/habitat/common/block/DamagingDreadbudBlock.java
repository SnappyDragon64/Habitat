package mod.schnappdragon.habitat.common.block;

import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DamagingDreadbudBlock extends DreadbudBlock {
    private final int damage;

    public DamagingDreadbudBlock(int damage, @Nullable Supplier<Block> nextStage, float threshold, Properties properties) {
        super(nextStage, threshold, properties);
        this.damage = damage;
    }
}

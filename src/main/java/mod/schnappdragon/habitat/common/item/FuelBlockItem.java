package mod.schnappdragon.habitat.common.item;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class FuelBlockItem extends BlockItem {
    private int burnTime;

    public FuelBlockItem(Block block, int burnTime, Properties properties) {
        super(block, properties);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return burnTime;
    }
}
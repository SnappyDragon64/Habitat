package mod.schnappdragon.bloom_and_gloom.common.item;

import mod.schnappdragon.bloom_and_gloom.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class KabloomFruitItem extends Item {
    public KabloomFruitItem(Item.Properties builder) {
        super(builder);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.getCooldownTracker().setCooldown(this, 20);

        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        if (!worldIn.isRemote) {
            KabloomFruitEntity kabloomfruitentity = new KabloomFruitEntity(worldIn, playerIn);
            kabloomfruitentity.setItem(new ItemStack(BGItems.KABLOOM_FRUIT.get()));
            kabloomfruitentity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F, 0.9F);
            worldIn.addEntity(kabloomfruitentity);
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
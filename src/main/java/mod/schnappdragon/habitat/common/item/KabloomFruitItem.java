package mod.schnappdragon.habitat.common.item;

import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruit;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class KabloomFruitItem extends Item {
    public KabloomFruitItem(Item.Properties builder) {
        super(builder);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), HabitatSoundEvents.KABLOOM_FRUIT_THROW.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (playerIn.getRandom().nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldowns().addCooldown(this, 20);

        if (!playerIn.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        if (!worldIn.isClientSide) {
            KabloomFruit kabloomfruitentity = new KabloomFruit(worldIn, playerIn);
            kabloomfruitentity.setItem(new ItemStack(HabitatItems.KABLOOM_FRUIT.get()));
            kabloomfruitentity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 0.5F, 0.9F);
            worldIn.addFreshEntity(kabloomfruitentity);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}
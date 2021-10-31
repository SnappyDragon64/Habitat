package mod.schnappdragon.habitat.common.item;

import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class KabloomFruitItem extends Item {
    public KabloomFruitItem(Item.Properties builder) {
        super(builder);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), HabitatSoundEvents.ENTITY_KABLOOM_FRUIT_THROW.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldowns().addCooldown(this, 20);

        if (!playerIn.abilities.instabuild) {
            itemstack.shrink(1);
        }

        if (!worldIn.isClientSide) {
            KabloomFruitEntity kabloomfruitentity = new KabloomFruitEntity(worldIn, playerIn);
            kabloomfruitentity.setItem(new ItemStack(HabitatItems.KABLOOM_FRUIT.get()));
            kabloomfruitentity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 0.5F, 0.9F);
            worldIn.addFreshEntity(kabloomfruitentity);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}
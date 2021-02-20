package mod.schnappdragon.bloom_and_gloom.common.item;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGParticleTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FairyRingMushroomStewItem extends Item {
    public FairyRingMushroomStewItem(Properties properties) {
        super(properties);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);

        List<EffectInstance> list = new ArrayList<>(entityLiving.getActivePotionEffects());
        if (!list.isEmpty()) {
            EffectInstance effectIn = list.get(entityLiving.getRNG().nextInt(list.size()));
            entityLiving.getActivePotionEffect(effectIn.getPotion()).combine(new EffectInstance(effectIn.getPotion(), MathHelper.ceil(effectIn.getDuration() * 1.1F), effectIn.getAmplifier()));
        }

        for (int i = 0; i < 4; ++i)
            worldIn.addParticle(BGParticleTypes.FAIRY_RING_SPORE.get(), entityLiving.getPosX() + entityLiving.getRNG().nextDouble() / 2.0D, entityLiving.getPosYHeight(0.5D), entityLiving.getPosZ() + entityLiving.getRNG().nextDouble() / 2.0D, entityLiving.getRNG().nextGaussian() * 0.01D, 0.0D, entityLiving.getRNG().nextGaussian() * 0.01D);

        return entityLiving instanceof PlayerEntity && ((PlayerEntity) entityLiving).abilities.isCreativeMode ? itemstack : new ItemStack(Items.BOWL);
    }
}

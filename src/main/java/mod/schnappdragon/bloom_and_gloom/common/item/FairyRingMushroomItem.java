package mod.schnappdragon.bloom_and_gloom.common.item;

import mod.schnappdragon.bloom_and_gloom.core.capabilities.classes.ConsumedFairyRingMushroom;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;

public class FairyRingMushroomItem extends BlockItem {
    public FairyRingMushroomItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (!playerIn.world.isRemote && target instanceof MooshroomEntity && ((MooshroomEntity) target).getMooshroomType() == MooshroomEntity.Type.BROWN) {
            MooshroomEntity mooshroom = (MooshroomEntity) target;
            if (mooshroom.hasStewEffect != null && !mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().getConsumedFairyRingMushroom()) {
                mooshroom.effectDuration *= 2;
                if (!playerIn.abilities.isCreativeMode)
                    stack.shrink(1);
                mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().setConsumedFairyRingMushroom(true);
                //BGPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ConsumedFairyRingMushroomPacket(mooshroom.getEntityId(), true));

                for (int j = 0; j < 4; ++j)
                    Minecraft.getInstance().world.addParticle(ParticleTypes.EFFECT, mooshroom.getPosX() + mooshroom.getRNG().nextDouble() / 2.0D, mooshroom.getPosYHeight(0.5D), mooshroom.getPosZ() + mooshroom.getRNG().nextDouble() / 2.0D, 0.0D, mooshroom.getRNG().nextDouble() / 5.0D, 0.0D);

                mooshroom.world.playSound(null, mooshroom.getPosX(), mooshroom.getPosY(), mooshroom.getPosZ(), SoundEvents.ENTITY_MOOSHROOM_EAT, mooshroom.getSoundCategory(), 2.0F, 1.0F);
            }
            else {
                for (int i = 0; i < 2; ++i)
                    Minecraft.getInstance().world.addParticle(ParticleTypes.SMOKE, mooshroom.getPosX() + mooshroom.getRNG().nextDouble() / 2.0D, mooshroom.getPosYHeight(0.5D), mooshroom.getPosZ() + mooshroom.getRNG().nextDouble() / 2.0D, 0.0D, mooshroom.getRNG().nextDouble() / 5.0D, 0.0D);
            }

            return ActionResultType.SUCCESS;
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
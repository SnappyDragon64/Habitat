package mod.schnappdragon.habitat.common.item;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PasserinePotItem extends Item {
    public PasserinePotItem(Item.Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        if (level.isClientSide)
            return InteractionResult.SUCCESS;

        ItemStack stack = ctx.getItemInHand();
        Player player = ctx.getPlayer();
        CompoundTag tag = stack.getTagElement("Passerine");

        if (tag == null)
            return InteractionResult.PASS;

        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());

        Passerine passerine = HabitatEntityTypes.PASSERINE.get().create(level);

        passerine.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
        passerine.readAdditionalSaveData(tag);
        passerine.setPersistenceRequired();
        level.addFreshEntity(passerine);

        level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HabitatSoundEvents.PASSERINE_PLACE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        stack.shrink(1);
        if (player != null && !player.getAbilities().instabuild) {
            player.setItemInHand(ctx.getHand(), new ItemStack(Items.FLOWER_POT));
        }

        return InteractionResult.SUCCESS;
    }

    public static ItemStack fromPasserine(Passerine passerine) {
        ItemStack stack = new ItemStack(HabitatItems.PASSERINE_IN_A_POT.get());
        CompoundTag tag = new CompoundTag();
        passerine.saveWithoutId(tag);
        stack.getOrCreateTag().put("Passerine", tag);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag passerineTag = stack.getTagElement("Passerine");
        if (passerineTag == null) return;

        if (!passerineTag.contains("Variant", Tag.TAG_STRING))
            return;

        String id = passerineTag.getString("Variant");
        tooltip.add(Component.translatable("passerine.variant." + id.replace(':', '.'))
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

}

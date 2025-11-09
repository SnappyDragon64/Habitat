package mod.schnappdragon.habitat.common.item;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.common.entity.animal.PasserineVariant;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
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

        Player player = ctx.getPlayer();
        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
        ItemStack stack = ctx.getItemInHand();

        Passerine passerine = HabitatEntityTypes.PASSERINE.get().spawn((ServerLevel) level, stack, player, pos, MobSpawnType.BUCKET, true, false);

        if (passerine == null) return InteractionResult.FAIL;

        level.playSound(null, passerine.getX(), passerine.getY(), passerine.getZ(), HabitatSoundEvents.PASSERINE_PLACE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        stack.shrink(1);
        if (player != null && !player.getAbilities().instabuild) {
            player.setItemInHand(ctx.getHand(), new ItemStack(Items.FLOWER_POT));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag compound = stack.getTag();
        if (compound == null) return;

        if (!compound.contains("Variant", Tag.TAG_STRING))
            return;

        String id = compound.getString("Variant");
        tooltip.add(Component.translatable("passerine.variant." + id.replace(':', '.')).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}

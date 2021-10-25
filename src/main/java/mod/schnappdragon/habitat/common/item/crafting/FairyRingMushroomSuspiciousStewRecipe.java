package mod.schnappdragon.habitat.common.item.crafting;

import mod.schnappdragon.habitat.common.item.FairyRingMushroomItem;
import mod.schnappdragon.habitat.core.HabitatConfig;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatRecipeSerializers;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

public class FairyRingMushroomSuspiciousStewRecipe extends SpecialRecipe {
    public FairyRingMushroomSuspiciousStewRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean brown = false;
        boolean red = false;
        boolean fairy = false;
        boolean bowl = false;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Blocks.BROWN_MUSHROOM.asItem() && !brown)
                    brown = true;
                else if (itemstack.getItem() == Blocks.RED_MUSHROOM.asItem() && !red)
                    red = true;
                else if (itemstack.getItem() == HabitatItems.FAIRY_RING_MUSHROOM.get() && !fairy)
                    fairy = true;
                else if (itemstack.getItem() == Items.BOWL && !bowl)
                    bowl = true;
                else
                    return false;
            }
        }

        return brown && red && fairy && bowl;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        Pair<Effect, Integer> effect = FairyRingMushroomItem.getStewEffect();

        ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW, 1);
        SuspiciousStewItem.addEffect(stew, effect.getLeft(), effect.getRight());
        return stew;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return HabitatRecipeSerializers.CRAFTING_SPECIAL_FAIRYRINGMUSHROOMSUSPICIOUSSTEW.get();
    }
}

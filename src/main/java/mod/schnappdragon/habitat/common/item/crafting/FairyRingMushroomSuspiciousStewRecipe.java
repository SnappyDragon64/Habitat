package mod.schnappdragon.habitat.common.item.crafting;

import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatRecipeSerializers;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FairyRingMushroomSuspiciousStewRecipe extends SpecialRecipe {
    public FairyRingMushroomSuspiciousStewRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean brown = false;
        boolean red = false;
        boolean fairy = false;
        boolean flower = false;
        boolean bowl = false;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Blocks.BROWN_MUSHROOM.asItem() && !brown)
                    brown = true;
                else if (itemstack.getItem() == Blocks.RED_MUSHROOM.asItem() && !red)
                    red = true;
                else if (itemstack.getItem() == HabitatItems.FAIRY_RING_MUSHROOM.get() && !fairy)
                    fairy = true;
                else if (itemstack.getItem().isIn(ItemTags.SMALL_FLOWERS) && !flower)
                    flower = true;
                else if (itemstack.getItem() == Items.BOWL && !bowl)
                    bowl = true;
                else
                    return false;
            }
        }

        return brown && red && fairy && flower & bowl;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack flower = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack inSlot = inv.getStackInSlot(i);
            if (!inSlot.isEmpty() && inSlot.getItem().isIn(ItemTags.SMALL_FLOWERS)) {
                flower = inSlot;
                break;
            }
        }

        ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW, 1);
        if (flower.getItem() instanceof BlockItem && ((BlockItem) flower.getItem()).getBlock() instanceof FlowerBlock) {
            FlowerBlock flowerblock = (FlowerBlock) ((BlockItem) flower.getItem()).getBlock();
            SuspiciousStewItem.addEffect(stew, flowerblock.getStewEffect(), flowerblock.getStewEffectDuration() * 2);
        }

        return stew;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 3 || width >= 3 && height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return HabitatRecipeSerializers.CRAFTING_SPECIAL_FAIRYRINGMUSHROOMSUSPICIOUSSTEW.get();
    }
}

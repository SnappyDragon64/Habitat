package mod.schnappdragon.habitat.common.item.crafting;

import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatRecipeSerializers;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<Item> flowers = ItemTags.SMALL_FLOWERS.getAllElements().stream().filter((item) -> item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof FlowerBlock).collect(Collectors.toList());
        Optional<Item> flower = flowers.stream().skip((int) (flowers.size() * Math.random())).findFirst();

        if (flower.isPresent()) {
            ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW, 1);
            FlowerBlock flowerBlock = (FlowerBlock) ((BlockItem) flower.get()).getBlock();
            SuspiciousStewItem.addEffect(stew, flowerBlock.getStewEffect(), flowerBlock.getStewEffectDuration() * 2);
            return stew;
        }
        return ItemStack.EMPTY; // Reached when tag is empty or does not contain BlockItem for FlowerBlock at all
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

package mod.schnappdragon.bloom_and_gloom.core.dispenser;

import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExistingDispenseItemBehaviour {

    public static IDispenseItemBehavior getShearsItemBehaviour() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getDispenserBehaviour = DispenserBlock.class.getDeclaredMethod("getBehavior", ItemStack.class);
        getDispenserBehaviour.setAccessible(true);
        return (IDispenseItemBehavior) getDispenserBehaviour.invoke(Blocks.DISPENSER, new ItemStack(Items.SHEARS, 1));
    }
}
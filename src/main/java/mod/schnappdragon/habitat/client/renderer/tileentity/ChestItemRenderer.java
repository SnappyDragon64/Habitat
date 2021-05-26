package mod.schnappdragon.habitat.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Supplier;

public class ChestItemRenderer<T extends TileEntity> extends ItemStackTileEntityRenderer {
    private final Supplier<T> tileEntity;

    public ChestItemRenderer(Supplier<T> tileEntityIn) {
        this.tileEntity = tileEntityIn;
    }

    @Override
    public void func_239207_a_(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        HabitatChestTileEntityRenderer.itemBlock = ((BlockItem) itemStackIn.getItem()).getBlock();
        TileEntityRendererDispatcher.instance.renderItem(this.tileEntity.get(), matrixStack, buffer, combinedLight, combinedOverlay);
        HabitatChestTileEntityRenderer.itemBlock = null;
    }
}
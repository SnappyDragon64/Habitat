package mod.schnappdragon.habitat.client.renderer.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.schnappdragon.habitat.common.tileentity.HabitatChestTileEntity;
import mod.schnappdragon.habitat.common.tileentity.HabitatTrappedChestTileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

public class ChestItemRenderer<T extends BlockEntity> extends BlockEntityWithoutLevelRenderer {
    private final Supplier<T> tileEntity;

    private ChestItemRenderer(Supplier<T> tileEntityIn) {
        this.tileEntity = tileEntityIn;
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        HabitatChestTileEntityRenderer.itemBlock = ((BlockItem) itemStackIn.getItem()).getBlock();
        BlockEntityRenderDispatcher.instance.renderItem(this.tileEntity.get(), matrixStack, buffer, combinedLight, combinedOverlay);
        HabitatChestTileEntityRenderer.itemBlock = null;
    }

    public static BlockEntityWithoutLevelRenderer getChestISTER() {
        return new ChestItemRenderer<>(HabitatChestTileEntity::new);
    }

    public static BlockEntityWithoutLevelRenderer getTrappedChestISTER() {
        return new ChestItemRenderer<>(HabitatTrappedChestTileEntity::new);
    }
}
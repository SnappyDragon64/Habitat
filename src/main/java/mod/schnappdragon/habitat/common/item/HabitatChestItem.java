package mod.schnappdragon.habitat.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.schnappdragon.habitat.client.renderer.block.HabitatChestBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.function.Consumer;

public class HabitatChestItem extends FuelBlockItem {
    public HabitatChestItem(Block block, int burnTime, Properties properties) {
        super(block, burnTime, properties);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> new BlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()) {
                @Override
                public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
                    HabitatChestBlockEntityRenderer.block = ((BlockItem) itemStackIn.getItem()).getBlock();
                    super.renderByItem(itemStackIn, transformType, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
                    HabitatChestBlockEntityRenderer.block = null;
                }
            });

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer.get();
            }
        });
    }
}

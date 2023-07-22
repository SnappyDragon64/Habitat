package mod.schnappdragon.habitat.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.schnappdragon.habitat.client.renderer.block.HabitatChestRenderer;
import mod.schnappdragon.habitat.common.block.entity.HabitatChestBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.function.Consumer;

public class HabitatChestItem extends FuelBlockItem {
    public HabitatChestItem(Block block, int burnTime, Properties properties) {
        super(block, burnTime, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> new BlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()) {
                @Override
                public void renderByItem(ItemStack itemStackIn, ItemDisplayContext transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
                    HabitatChestRenderer.block = ((BlockItem) itemStackIn.getItem()).getBlock();
                    HabitatChestRenderer.INSTANCE.render((HabitatChestBlockEntity) ((EntityBlock) HabitatChestRenderer.block).newBlockEntity(BlockPos.ZERO, HabitatChestRenderer.block.defaultBlockState()), 0, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
                    HabitatChestRenderer.block = null;
                }
            });

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer.get();
            }
        });
    }
}

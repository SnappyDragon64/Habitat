package mod.schnappdragon.habitat.client.renderer.block;

import mod.schnappdragon.habitat.common.block.VariantChest;
import mod.schnappdragon.habitat.common.block.ChestVariant;
import mod.schnappdragon.habitat.common.block.entity.HabitatChestBlockEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.ChestType;

public class HabitatChestRenderer extends ChestRenderer<HabitatChestBlockEntity> {
    public static HabitatChestRenderer INSTANCE;
    public static Block block = null;
    public boolean xmasTextures;

    public HabitatChestRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        INSTANCE = this;
    }

    @Override
    public Material getMaterial(HabitatChestBlockEntity blockEntity, ChestType type) {
        if (this.xmasTextures) {
            return switch (type) {
                case RIGHT -> Sheets.CHEST_XMAS_LOCATION_RIGHT;
                case LEFT -> Sheets.CHEST_XMAS_LOCATION_LEFT;
                default -> Sheets.CHEST_XMAS_LOCATION;
            };
        }
        else {
            Block chestBlock = block;
            if (chestBlock == null)
                chestBlock = blockEntity.getBlockState().getBlock();
            ChestVariant chestVariant = ((VariantChest) chestBlock).getVariant();

            if (chestVariant == null)
                return null;

            return switch (type) {
                case RIGHT -> getRenderMaterial(chestVariant.getRight());
                case LEFT -> getRenderMaterial(chestVariant.getLeft());
                default -> getRenderMaterial(chestVariant.getSingle());
            };
        }
    }

    private Material getRenderMaterial(ResourceLocation resource) {
        return new Material(Sheets.CHEST_SHEET, resource);
    }
}
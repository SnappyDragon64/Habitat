package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.misc.ChestVariants;
import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class HabitatTrappedChestBlock extends ChestBlock implements IChestVariant {
    private final ChestVariants.ChestVariant variant;

    public HabitatTrappedChestBlock(ChestVariants.ChestVariant variant, Properties properties) {
        super(properties, HabitatTileEntityTypes.TRAPPED_CHEST::get);
        this.variant = variant;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return HabitatTileEntityTypes.TRAPPED_CHEST.get().create();
    }

    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    public boolean canProvidePower(BlockState state) {
        return true;
    }

    public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return MathHelper.clamp(ChestTileEntity.getPlayersUsing(world, pos), 0, 15);
    }

    public int getStrongPower(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return direction == Direction.UP ? state.getWeakPower(world, pos, direction) : 0;
    }

    @Override
    public ChestVariants.ChestVariant getVariant() {
        return variant;
    }
}
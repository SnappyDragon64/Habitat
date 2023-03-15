package mod.schnappdragon.habitat.common.block.entity;

import mod.schnappdragon.habitat.common.block.RafflesiaBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RafflesiaBlockEntity extends BlockEntity {
    public ListTag Effects;

    public RafflesiaBlockEntity(BlockPos pos, BlockState state) {
        super(HabitatBlockEntityTypes.RAFFLESIA.get(), pos, state);
        this.Effects = RafflesiaBlock.getDefault();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Effects", this.Effects);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.Effects = compound.getList("Effects", 10);
    }

    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = new CompoundTag();
        this.saveAdditional(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(CompoundTag compound) {
        this.load(compound);
    }

    public void onChange(Level worldIn, BlockState newState) {
        this.setChanged();
        worldIn.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), newState, -1);
    }
}

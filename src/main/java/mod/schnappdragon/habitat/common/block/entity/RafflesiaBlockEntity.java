package mod.schnappdragon.habitat.common.block.entity;

import mod.schnappdragon.habitat.common.block.RafflesiaBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
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

    @Nonnull
    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("Effects", this.Effects);

        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        this.Effects = compound.getList("Effects", 10);

        super.load(compound);
    }

    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, -1, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbtTagCompound = new CompoundTag();
        save(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(CompoundTag compound) {
        this.load(compound);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    public void onChange(Level worldIn, BlockState newState) {
        this.setChanged();
        worldIn.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), newState, -1);
    }
}

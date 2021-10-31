package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RafflesiaTileEntity extends BlockEntity {
    public ListTag Effects = new ListTag();

    public RafflesiaTileEntity() {
        super(HabitatTileEntityTypes.RAFFLESIA.get());
        CompoundTag tag = new CompoundTag();
        tag.putByte("EffectId", (byte) 19);
        tag.putInt("EffectDuration", 240);
        this.Effects.add(tag);
    }

    @Nonnull
    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("Effects", this.Effects);

        return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
        this.Effects = compound.getList("Effects", 10);

        super.load(state, compound);
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
    public void handleUpdateTag(BlockState state, CompoundTag compound) {
        this.load(state, compound);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(this.getBlockState(), pkt.getTag());
    }

    public void onChange(Level worldIn, BlockState newState) {
        this.setChanged();
        worldIn.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), newState, -1);
    }
}

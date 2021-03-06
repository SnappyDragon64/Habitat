package mod.schnappdragon.habitat.common.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RafflesiaTileEntity extends TileEntity {
    public ListNBT Effects = new ListNBT();

    public RafflesiaTileEntity() {
        super(HabitatTileEntityTypes.RAFFLESIA.get());
        CompoundNBT tag = new CompoundNBT();
        tag.putByte("EffectId", (byte) 19);
        tag.putInt("EffectDuration", 240);
        this.Effects.add(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("Effects", this.Effects);

        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        this.Effects = compound.getList("Effects", 10);

        super.read(state, compound);
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, -1, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT compound) {
        this.read(state, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.getBlockState(), pkt.getNbtCompound());
    }

    public void onChange(World worldIn, BlockState newState) {
        this.markDirty();
        worldIn.notifyBlockUpdate(this.getPos(), this.getBlockState(), newState, -1);
    }
}

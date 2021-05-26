package mod.schnappdragon.habitat.common.entity.item;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatBoatEntity extends BoatEntity {
    private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(HabitatBoatEntity.class, DataSerializers.VARINT);

    public HabitatBoatEntity(EntityType<? extends BoatEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
        this.preventEntitySpawning = true;
    }

    public HabitatBoatEntity(World worldIn, double x, double y, double z) {
        this(HabitatEntityTypes.BOAT.get(), worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public HabitatBoatEntity.Type getHabitatBoatType() {
        return HabitatBoatEntity.Type.byId(this.dataManager.get(BOAT_TYPE));
    }

    @Override
    public Item getItemBoat() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(Habitat.MOD_ID, this.getHabitatBoatType().getName() + "_boat"));
    }

    public void setBoatType(HabitatBoatEntity.Type boatType) {
        this.dataManager.set(BOAT_TYPE, boatType.ordinal());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BOAT_TYPE, Type.FAIRY_RING_MUSHROOM.ordinal());
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putString("Type", this.getHabitatBoatType().getName());
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        if (compound.contains("Type", 8))
            this.setBoatType(Type.getTypeFromString(compound.getString("Type")));
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.lastYd = this.getMotion().y;
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != BoatEntity.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.onLivingFall(this.fallDistance, 1.0F);
                    if (!this.world.isRemote && !this.removed) {
                        this.remove();
                        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            Item planks = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Habitat.MOD_ID, this.getHabitatBoatType().getName() + "_planks"));

                            for (int i = 0; i < 3; ++i)
                                this.entityDropItem(planks);

                            for (int j = 0; j < 2; ++j)
                                this.entityDropItem(Items.STICK);
                        }
                    }
                }

                this.fallDistance = 0.0F;
            }
            else if (!this.world.getFluidState(this.getPosition().down()).isTagged(FluidTags.WATER) && y < 0.0D)
                this.fallDistance = (float) ((double) this.fallDistance - y);
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public enum Type {
        FAIRY_RING_MUSHROOM("fairy_ring_mushroom");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static HabitatBoatEntity.Type byId(int id) {
            HabitatBoatEntity.Type[] types = values();
            if (id < 0 || id >= types.length)
                id = 0;

            return types[id];
        }

        public static HabitatBoatEntity.Type getTypeFromString(String nameIn) {
            HabitatBoatEntity.Type[] types = values();

            for (Type type : types) {
                if (type.getName().equals(nameIn))
                    return type;
            }

            return types[0];
        }
    }
}
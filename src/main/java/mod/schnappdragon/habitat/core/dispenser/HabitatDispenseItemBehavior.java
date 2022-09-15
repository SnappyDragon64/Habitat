package mod.schnappdragon.habitat.core.dispenser;

import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.common.block.FloweringBallCactusBlock;
import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.common.block.RafflesiaBlock;
import mod.schnappdragon.habitat.common.block.entity.RafflesiaBlockEntity;
import mod.schnappdragon.habitat.common.entity.IHabitatShearable;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.common.entity.projectile.ThrownKabloomFruit;
import mod.schnappdragon.habitat.common.entity.vehicle.HabitatBoat;
import mod.schnappdragon.habitat.core.registry.*;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class HabitatDispenseItemBehavior {
    private static DispenseItemBehavior SuspiciousStewBehavior;
    private static DispenseItemBehavior BowlItemBehavior;
    private static DispenseItemBehavior ShearsBehavior;
    private static DispenseItemBehavior RedstoneBehavior;

    public static void registerDispenserBehaviors() {
        SuspiciousStewBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.SUSPICIOUS_STEW);
        BowlItemBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.BOWL);
        ShearsBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.SHEARS);
        RedstoneBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.REDSTONE);

        DispenserBlock.registerBehavior(HabitatItems.FAIRY_RING_MUSHROOM_BOAT.get(), new HabitatDispenseBoatBehavior(HabitatBoat.Type.FAIRY_RING_MUSHROOM));

        DispenserBlock.registerBehavior(Items.SUSPICIOUS_STEW, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                ServerLevel worldIn = source.getLevel();
                BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (state.is(HabitatBlocks.RAFFLESIA.get()) && worldIn.getBlockEntity(pos) instanceof RafflesiaBlockEntity rafflesia && !state.getValue(RafflesiaBlock.HAS_STEW)) {
                    CompoundTag tag = stack.getTag();
                    if (tag != null && tag.contains("Effects", 9)) {
                        rafflesia.Effects = tag.getList("Effects", 10);
                    }
                    worldIn.setBlockAndUpdate(pos, state.setValue(RafflesiaBlock.HAS_STEW, true));
                    rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                    worldIn.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_SLURP.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                    worldIn.sendParticles(RafflesiaBlock.getParticle(rafflesia.Effects), pos.getX() + 0.5D + (2 * worldIn.random.nextDouble() - 1.0F) / 3.0D, pos.getY() + 0.25F + worldIn.random.nextDouble() / 2, pos.getZ() + 0.5D + (2 * worldIn.random.nextDouble() - 1.0F) / 3.0D, 0, 0.0D, 0.1D, 0.0D, 1.0D);
                    worldIn.gameEvent(GameEvent.FLUID_PLACE, pos);
                    stack = new ItemStack(Items.BOWL, 1);
                    this.setSuccess(true);
                    return stack;
                } else if (SuspiciousStewBehavior != null)
                    SuspiciousStewBehavior.dispense(source, stack);
                this.setSuccess(false);
                return stack;
            }
        });

        DispenserBlock.registerBehavior(Items.BOWL, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                ServerLevel worldIn = source.getLevel();
                BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (state.is(HabitatBlocks.RAFFLESIA.get()) && worldIn.getBlockEntity(pos) instanceof RafflesiaBlockEntity rafflesia && state.getValue(RafflesiaBlock.HAS_STEW)) {
                    ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
                    rafflesia.Effects.forEach(tag -> {
                        CompoundTag compound = (CompoundTag) tag;
                        SuspiciousStewItem.saveMobEffect(stew, MobEffect.byId(compound.getByte("EffectId")), compound.getInt("EffectDuration"));
                    });
                    worldIn.setBlockAndUpdate(pos, state.setValue(RafflesiaBlock.HAS_STEW, false));
                    rafflesia.Effects = RafflesiaBlock.getDefault();
                    rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                    worldIn.playSound(null, pos, HabitatSoundEvents.RAFFLESIA_FILL_BOWL.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                    worldIn.gameEvent(GameEvent.FLUID_PICKUP, pos);
                    this.setSuccess(true);
                    stack.shrink(1);

                    if (stack.isEmpty())
                        return stew;
                    else {
                        if (source.<DispenserBlockEntity>getEntity().addItem(stew) < 0)
                            new DefaultDispenseItemBehavior().dispense(source, stew);

                        return stack;
                    }
                } else if (BowlItemBehavior != null)
                    BowlItemBehavior.dispense(source, stack);
                this.setSuccess(false);
                return stack;
            }
        });

        DispenserBlock.registerBehavior(Items.SHEARS, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                ServerLevel worldIn = source.getLevel();
                BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));

                if (shearEntity(Pooka.class, worldIn, stack, pos)) {
                    this.setSuccess(true);
                    return stack;
                }

                BlockState state = worldIn.getBlockState(pos);

                if (state.is(HabitatBlocks.KABLOOM_BUSH.get()) && state.getValue(KabloomBushBlock.AGE) == 7) {
                    Block.popResource(worldIn, pos, new ItemStack(HabitatItems.KABLOOM_FRUIT.get()));
                    worldIn.setBlockAndUpdate(pos, state.setValue(KabloomBushBlock.AGE, 3));
                    worldIn.playSound(null, pos, HabitatSoundEvents.KABLOOM_BUSH_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                    if (stack.hurt(1, worldIn.getRandom(), null))
                        stack.setCount(0);
                    worldIn.gameEvent(GameEvent.SHEAR, pos);
                    this.setSuccess(true);
                } else if (state.getBlock() instanceof FloweringBallCactusBlock cactus) {
                    Block.popResource(worldIn, pos, new ItemStack(cactus.getColor().getFlower()));
                    worldIn.setBlockAndUpdate(pos, cactus.getColor().getBallCactus().defaultBlockState());
                    worldIn.playSound(null, pos, HabitatSoundEvents.FLOWERING_BALL_CACTUS_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                    if (stack.hurt(1, worldIn.getRandom(), null))
                        stack.setCount(0);
                    worldIn.gameEvent(GameEvent.SHEAR, pos);
                    this.setSuccess(true);
                } else if (state.is(HabitatBlocks.FAIRY_RING_MUSHROOM.get()) && state.getValue(FairyRingMushroomBlock.MUSHROOMS) > 1) {
                    Block.popResource(worldIn, pos, new ItemStack(state.getBlock()));
                    worldIn.setBlockAndUpdate(pos, state.setValue(FairyRingMushroomBlock.MUSHROOMS, state.getValue(FairyRingMushroomBlock.MUSHROOMS) - 1));
                    worldIn.playSound(null, pos, HabitatSoundEvents.FAIRY_RING_MUSHROOM_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                    if (stack.hurt(1, worldIn.getRandom(), null))
                        stack.setCount(0);
                    worldIn.gameEvent(GameEvent.SHEAR, pos);
                    this.setSuccess(true);
                } else
                    return ShearsBehavior.dispense(source, stack);
                return stack;
            }
        });

        DispenserBlock.registerBehavior(HabitatItems.KABLOOM_FRUIT.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
                return Util.make(new ThrownKabloomFruit(worldIn, position.x(), position.y(), position.z()), (kabloomfruit) -> {
                    kabloomfruit.setItem(stackIn);
                });
            }

            protected float getUncertainty() {
                return super.getUncertainty() * 0.9F;
            }

            protected float getPower() {
                return super.getPower() * 0.5F;
            }
        });

        DispenserBlock.registerBehavior(Items.REDSTONE, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                ServerLevel worldIn = source.getLevel();
                BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (state.is(HabitatBlocks.FAIRY_RING_MUSHROOM.get()) && !state.getValue(FairyRingMushroomBlock.DUSTED)) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(FairyRingMushroomBlock.DUSTED, true));
                    worldIn.addParticle(DustParticleOptions.REDSTONE, pos.getX() + 0.5D, pos.getY() + 0.125D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    worldIn.playSound(null, pos, HabitatSoundEvents.FAIRY_RING_MUSHROOM_DUST.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                    worldIn.gameEvent(GameEvent.BLOCK_CHANGE, pos);
                    stack.shrink(1);
                    this.setSuccess(true);
                    return stack;
                } else if (RedstoneBehavior != null)
                    RedstoneBehavior.dispense(source, stack);
                this.setSuccess(false);
                return stack;
            }
        });

        DispenserBlock.registerBehavior(HabitatItems.FAIRY_RING_MUSHROOM.get(), new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                ServerLevel worldIn = source.getLevel();
                BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                for (LivingEntity livingentity : worldIn.getEntitiesOfClass(LivingEntity.class, new AABB(pos), EntitySelector.NO_SPECTATORS)) {
                    if (livingentity.getType() == EntityType.RABBIT) {
                        Rabbit rabbit = (Rabbit) livingentity;
                        worldIn.gameEvent(GameEvent.MOB_INTERACT, rabbit.eyeBlockPosition());
                        rabbit.playSound(HabitatSoundEvents.RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isBaby() ? (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.5F : (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.0F);
                        rabbit.discard();
                        worldIn.addFreshEntity(Pooka.convertRabbitToPooka(rabbit));
                        stack.shrink(1);
                        for (int j = 0; j < 8; ++j)
                            worldIn.sendParticles(HabitatParticleTypes.FAIRY_RING_SPORE.get(), rabbit.getRandomX(0.5D), rabbit.getY(0.5D), rabbit.getRandomZ(0.5D), 0, rabbit.getRandom().nextGaussian(), 0.0D, rabbit.getRandom().nextGaussian(), 0.01D);
                        this.setSuccess(true);
                        return stack;
                    }
                }
                return stack;
            }
        });
    }

    private static <T extends Entity & IHabitatShearable> boolean shearEntity(Class<T> entityClass, ServerLevel worldIn, ItemStack stack, BlockPos pos) {
        boolean flag = false;

        for (T entity : worldIn.getEntitiesOfClass(entityClass, new AABB(pos), EntitySelector.NO_SPECTATORS)) {
            if (entity.isShearable(ItemStack.EMPTY, worldIn, pos)) {
                entity.onSheared(null, stack, worldIn, pos, 0, SoundSource.BLOCKS).forEach(drop -> worldIn.addFreshEntity(new ItemEntity(worldIn, entity.getX(), entity.getY(1.0D), entity.getZ(), drop)));

                if (stack.hurt(1, worldIn.getRandom(), null))
                    stack.setCount(0);
                flag = true;
            }
        }

        return flag;
    }
}
package mod.schnappdragon.habitat.core.dispenser;

import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.common.block.FloweringBallCactusBlock;
import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.common.block.RafflesiaBlock;
import mod.schnappdragon.habitat.common.block.entity.RafflesiaBlockEntity;
import mod.schnappdragon.habitat.common.entity.vehicle.HabitatBoat;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruit;
import mod.schnappdragon.habitat.common.item.HabitatSpawnEggItem;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class HabitatDispenserBehaviours {
    private static DispenseItemBehavior SuspiciousStewBehavior;
    private static DispenseItemBehavior ShearsBehavior;
    private static DispenseItemBehavior RedstoneBehavior;

    public static void registerDispenserBehaviour() {
        SuspiciousStewBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.SUSPICIOUS_STEW);
        ShearsBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.SHEARS);
        RedstoneBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.REDSTONE);

        DispenserBlock.registerBehavior(HabitatItems.FAIRY_RING_MUSHROOM_BOAT.get(), new HabitatDispenseBoatBehavior(HabitatBoat.Type.FAIRY_RING_MUSHROOM));

        DispenserBlock.registerBehavior(Items.SUSPICIOUS_STEW, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level worldIn = source.getLevel();
                BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (!worldIn.isClientSide && state.is(HabitatBlocks.RAFFLESIA.get())) {
                    BlockEntity tile = worldIn.getBlockEntity(pos);
                    if (tile instanceof RafflesiaBlockEntity && !state.getValue(RafflesiaBlock.HAS_STEW)) {
                        RafflesiaBlockEntity rafflesia = (RafflesiaBlockEntity) tile;
                        CompoundTag tag = stack.getTag();
                        if (tag != null && tag.contains("Effects", 9)) {
                            rafflesia.Effects = tag.getList("Effects", 10);
                        }
                        worldIn.setBlockAndUpdate(pos, state.setValue(RafflesiaBlock.HAS_STEW, true));
                        rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_RAFFLESIA_SLURP.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                        stack = new ItemStack(Items.BOWL, 1);
                        this.setSuccess(true);
                        return stack;
                    }
                }
                else if (SuspiciousStewBehavior != null)
                    SuspiciousStewBehavior.dispense(source, stack);
                this.setSuccess(false);
                return stack;
            }
        });

        DispenserBlock.registerBehavior(Items.SHEARS, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level worldIn = source.getLevel();
                BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (!worldIn.isClientSide) {
                    for (Pooka pooka : worldIn.getEntitiesOfClass(Pooka.class, new AABB(pos), EntitySelector.NO_SPECTATORS)) {
                        if (pooka.isPacified()) {
                            worldIn.playSound(null, pooka, HabitatSoundEvents.ENTITY_POOKA_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                            ((ServerLevel) worldIn).sendParticles(ParticleTypes.EXPLOSION, pooka.getX(), pooka.getY(0.5D), pooka.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                            pooka.discard();
                            worldIn.addFreshEntity(Pooka.convertPooka(pooka));
                            worldIn.addFreshEntity(new ItemEntity(worldIn, pooka.getX(), pooka.getY(1.0D), pooka.getZ(), new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get())));

                            if (stack.hurt(1, worldIn.getRandom(), null))
                                stack.setCount(0);
                            this.setSuccess(true);
                            return stack;
                        }
                    }

                    if (state.is(HabitatBlocks.KABLOOM_BUSH.get()) && state.getValue(KabloomBushBlock.AGE) == 7) {
                        Block.popResource(worldIn, pos, new ItemStack(HabitatItems.KABLOOM_FRUIT.get()));
                        worldIn.setBlockAndUpdate(pos, state.setValue(KabloomBushBlock.AGE, 3));
                        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_KABLOOM_BUSH_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                        if (stack.hurt(1, worldIn.getRandom(), null))
                            stack.setCount(0);
                        this.setSuccess(true);
                    }
                    else if (state.getBlock() instanceof FloweringBallCactusBlock) {
                        FloweringBallCactusBlock cactus = (FloweringBallCactusBlock) state.getBlock();
                        Block.popResource(worldIn, pos, new ItemStack(cactus.getColor().getFlower()));
                        worldIn.setBlockAndUpdate(pos, cactus.getColor().getBallCactus().defaultBlockState());
                        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_FLOWERING_BALL_CACTUS_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                        if (stack.hurt(1, worldIn.getRandom(), null))
                            stack.setCount(0);
                        this.setSuccess(true);
                    }
                    else if (state.is(HabitatBlocks.FAIRY_RING_MUSHROOM.get()) && state.getValue(FairyRingMushroomBlock.MUSHROOMS) > 1) {
                        Block.popResource(worldIn, pos, new ItemStack(state.getBlock()));
                        worldIn.setBlockAndUpdate(pos, state.setValue(FairyRingMushroomBlock.MUSHROOMS, state.getValue(FairyRingMushroomBlock.MUSHROOMS) - 1));
                        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_FAIRY_RING_MUSHROOM_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                        if (stack.hurt(1, worldIn.getRandom(), null))
                            stack.setCount(0);
                        this.setSuccess(true);
                    }
                    else
                        return ShearsBehavior.dispense(source, stack);
                }
                else
                    return ShearsBehavior.dispense(source, stack);
                return stack;
            }
        });

        DispenserBlock.registerBehavior(HabitatItems.KABLOOM_FRUIT.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
                return Util.make(new KabloomFruit(worldIn, position.x(), position.y(), position.z()), (kabloomfruit) -> {
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
                Level worldIn = source.getLevel();
                BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (!worldIn.isClientSide && state.is(HabitatBlocks.FAIRY_RING_MUSHROOM.get()) && !state.getValue(FairyRingMushroomBlock.DUSTED)) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(FairyRingMushroomBlock.DUSTED, true));
                    worldIn.addParticle(DustParticleOptions.REDSTONE, pos.getX() + 0.5D, pos.getY() + 0.125D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_FAIRY_RING_MUSHROOM_DUST.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
                    stack.shrink(1);
                    this.setSuccess(true);
                    return stack;
                }
                else if (RedstoneBehavior != null)
                    RedstoneBehavior.dispense(source, stack);
                this.setSuccess(false);
                return stack;
            }
        });

        for (SpawnEggItem egg : HabitatSpawnEggItem.HABITAT_EGGS) {
            DispenserBlock.registerBehavior(egg, new DefaultDispenseItemBehavior() {
                protected ItemStack execute(BlockSource source, ItemStack stack) {
                    Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                    EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                    entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                    stack.shrink(1);
                    return stack;
                }
            });
        }

        DispenserBlock.registerBehavior(HabitatItems.FAIRY_RING_MUSHROOM.get(), new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level worldIn = source.getLevel();
                if (!worldIn.isClientSide) {
                    BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                    for (LivingEntity livingentity : worldIn.getEntitiesOfClass(LivingEntity.class, new AABB(pos), EntitySelector.NO_SPECTATORS)) {
                        if (livingentity.getType() == EntityType.RABBIT) {
                            Rabbit rabbit = (Rabbit) livingentity;
                            rabbit.playSound(HabitatSoundEvents.ENTITY_RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isBaby() ? (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.5F : (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.0F);
                            rabbit.discard();
                            worldIn.addFreshEntity(Pooka.convertRabbit(rabbit));
                            stack.shrink(1);
                            for (int j = 0; j < 8; ++j)
                                ((ServerLevel) worldIn).sendParticles(HabitatParticleTypes.FAIRY_RING_SPORE.get(), rabbit.getRandomX(0.5D), rabbit.getY(0.5D), rabbit.getRandomZ(0.5D), 0, rabbit.getRandom().nextGaussian(), 0.0D, rabbit.getRandom().nextGaussian(), 0.01D);
                            this.setSuccess(true);
                            return stack;
                        }
                    }
                }
                return stack;
            }
        });
    }
}
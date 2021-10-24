package mod.schnappdragon.habitat.core.dispenser;

import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.common.block.FloweringBallCactusBlock;
import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.common.block.RafflesiaBlock;
import mod.schnappdragon.habitat.common.entity.item.HabitatBoatEntity;
import mod.schnappdragon.habitat.common.entity.monster.PookaEntity;
import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.habitat.common.item.HabitatSpawnEggItem;
import mod.schnappdragon.habitat.common.tileentity.RafflesiaTileEntity;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class HabitatDispenserBehaviours {
    private static IDispenseItemBehavior SuspiciousStewBehavior;
    private static IDispenseItemBehavior ShearsBehavior;
    private static IDispenseItemBehavior RedstoneBehavior;

    public static void registerDispenserBehaviour() {
        SuspiciousStewBehavior = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(Items.SUSPICIOUS_STEW);
        ShearsBehavior = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(Items.SHEARS);
        RedstoneBehavior = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(Items.REDSTONE);

        DispenserBlock.registerDispenseBehavior(HabitatItems.FAIRY_RING_MUSHROOM_BOAT.get(), new HabitatDispenseBoatBehavior(HabitatBoatEntity.Type.FAIRY_RING_MUSHROOM));

        DispenserBlock.registerDispenseBehavior(Items.SUSPICIOUS_STEW, new OptionalDispenseBehavior() {
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World worldIn = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (!worldIn.isRemote && state.isIn(HabitatBlocks.RAFFLESIA.get())) {
                    TileEntity tile = worldIn.getTileEntity(pos);
                    if (tile instanceof RafflesiaTileEntity && !state.get(RafflesiaBlock.HAS_STEW)) {
                        RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
                        CompoundNBT tag = stack.getTag();
                        if (tag != null && tag.contains("Effects", 9)) {
                            rafflesia.Effects = tag.getList("Effects", 10);
                        }
                        worldIn.setBlockState(pos, state.with(RafflesiaBlock.HAS_STEW, true));
                        rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_RAFFLESIA_SLURP.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                        stack = new ItemStack(Items.BOWL, 1);
                        this.setSuccessful(true);
                        return stack;
                    }
                }
                else if (SuspiciousStewBehavior != null)
                    SuspiciousStewBehavior.dispense(source, stack);
                this.setSuccessful(false);
                return stack;
            }
        });

        DispenserBlock.registerDispenseBehavior(Items.SHEARS, new OptionalDispenseBehavior() {
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World worldIn = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (!worldIn.isRemote) {
                    for (PookaEntity pooka : worldIn.getEntitiesWithinAABB(PookaEntity.class, new AxisAlignedBB(pos), EntityPredicates.NOT_SPECTATING)) {
                        if (pooka.isPacified()) {
                            worldIn.playMovingSound(null, pooka, HabitatSoundEvents.ENTITY_POOKA_SHEAR.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                            ((ServerWorld) worldIn).spawnParticle(ParticleTypes.EXPLOSION, pooka.getPosX(), pooka.getPosYHeight(0.5D), pooka.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                            pooka.remove();
                            worldIn.addEntity(PookaEntity.convertPooka(pooka));
                            worldIn.addEntity(new ItemEntity(worldIn, pooka.getPosX(), pooka.getPosYHeight(1.0D), pooka.getPosZ(), new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get())));

                            if (stack.attemptDamageItem(1, worldIn.getRandom(), null))
                                stack.setCount(0);
                            this.setSuccessful(true);
                            return stack;
                        }
                    }

                    if (state.isIn(HabitatBlocks.KABLOOM_BUSH.get()) && state.get(KabloomBushBlock.AGE) == 7) {
                        Block.spawnAsEntity(worldIn, pos, new ItemStack(HabitatItems.KABLOOM_FRUIT.get()));
                        worldIn.setBlockState(pos, state.with(KabloomBushBlock.AGE, 3));
                        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_KABLOOM_BUSH_SHEAR.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                        if (stack.attemptDamageItem(1, worldIn.getRandom(), null))
                            stack.setCount(0);
                        this.setSuccessful(true);
                    }
                    else if (state.getBlock() instanceof FloweringBallCactusBlock) {
                        FloweringBallCactusBlock cactus = (FloweringBallCactusBlock) state.getBlock();
                        Block.spawnAsEntity(worldIn, pos, new ItemStack(cactus.getColor().getFlower()));
                        worldIn.setBlockState(pos, cactus.getColor().getBallCactus().getDefaultState());
                        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_FLOWERING_BALL_CACTUS_SHEAR.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                        if (stack.attemptDamageItem(1, worldIn.getRandom(), null))
                            stack.setCount(0);
                        this.setSuccessful(true);
                    }
                    else if (state.isIn(HabitatBlocks.FAIRY_RING_MUSHROOM.get()) && state.get(FairyRingMushroomBlock.MUSHROOMS) > 1) {
                        Block.spawnAsEntity(worldIn, pos, new ItemStack(state.getBlock()));
                        worldIn.setBlockState(pos, state.with(FairyRingMushroomBlock.MUSHROOMS, state.get(FairyRingMushroomBlock.MUSHROOMS) - 1));
                        worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_FAIRY_RING_MUSHROOM_SHEAR.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                        if (stack.attemptDamageItem(1, worldIn.getRandom(), null))
                            stack.setCount(0);
                        this.setSuccessful(true);
                    }
                    else
                        return ShearsBehavior.dispense(source, stack);
                }
                else
                    return ShearsBehavior.dispense(source, stack);
                return stack;
            }
        });

        DispenserBlock.registerDispenseBehavior(HabitatItems.KABLOOM_FRUIT.get(), new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return Util.make(new KabloomFruitEntity(worldIn, position.getX(), position.getY(), position.getZ()), (kabloomfruit) -> {
                    kabloomfruit.setItem(stackIn);
                });
            }

            protected float getProjectileInaccuracy() {
                return super.getProjectileInaccuracy() * 0.9F;
            }

            protected float getProjectileVelocity() {
                return super.getProjectileVelocity() * 0.5F;
            }
        });

        DispenserBlock.registerDispenseBehavior(Items.REDSTONE, new OptionalDispenseBehavior() {
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World worldIn = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (!worldIn.isRemote && state.isIn(HabitatBlocks.FAIRY_RING_MUSHROOM.get()) && !state.get(FairyRingMushroomBlock.DUSTED)) {
                    worldIn.setBlockState(pos, state.with(FairyRingMushroomBlock.DUSTED, true));
                    worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 0.125D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    worldIn.playSound(null, pos, HabitatSoundEvents.BLOCK_FAIRY_RING_MUSHROOM_DUST.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                    stack.shrink(1);
                    this.setSuccessful(true);
                    return stack;
                }
                else if (RedstoneBehavior != null)
                    RedstoneBehavior.dispense(source, stack);
                this.setSuccessful(false);
                return stack;
            }
        });

        for (SpawnEggItem egg : HabitatSpawnEggItem.HABITAT_EGGS) {
            DispenserBlock.registerDispenseBehavior(egg, new DefaultDispenseItemBehavior() {
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    Direction direction = source.getBlockState().get(DispenserBlock.FACING);
                    EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                    entitytype.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
                    stack.shrink(1);
                    return stack;
                }
            });
        }

        DispenserBlock.registerDispenseBehavior(HabitatItems.FAIRY_RING_MUSHROOM.get(), new OptionalDispenseBehavior() {
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World worldIn = source.getWorld();
                if (!worldIn.isRemote) {
                    BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                    for (LivingEntity livingentity : worldIn.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos), EntityPredicates.NOT_SPECTATING)) {
                        if (livingentity.getType() == EntityType.RABBIT) {
                            RabbitEntity rabbit = (RabbitEntity) livingentity;
                            rabbit.playSound(HabitatSoundEvents.ENTITY_RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isChild() ? (rabbit.getRNG().nextFloat() - rabbit.getRNG().nextFloat()) * 0.2F + 1.5F : (rabbit.getRNG().nextFloat() - rabbit.getRNG().nextFloat()) * 0.2F + 1.0F);
                            rabbit.remove();
                            worldIn.addEntity(PookaEntity.convertRabbit(rabbit));
                            stack.shrink(1);
                            for (int j = 0; j < 8; ++j)
                                ((ServerWorld) worldIn).spawnParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), rabbit.getPosXRandom(0.5D), rabbit.getPosYHeight(0.5D), rabbit.getPosZRandom(0.5D), 0, rabbit.getRNG().nextGaussian(), 0.0D, rabbit.getRNG().nextGaussian(), 0.01D);
                            this.setSuccessful(true);
                            return stack;
                        }
                    }
                }
                return stack;
            }
        });
    }
}
package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.common.block.FloweringBallCactusBlock;
import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.common.block.RafflesiaBlock;
import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.habitat.common.tileentity.RafflesiaTileEntity;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HabitatDispenserBehaviours {
    public static IDispenseItemBehavior ShearsBehaviour;

    public static void registerDispenserBehaviour() {
        ShearsBehaviour = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(Items.SHEARS);

        DispenserBlock.registerDispenseBehavior(Items.SUSPICIOUS_STEW, new OptionalDispenseBehavior() {
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World worldIn = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (!worldIn.isRemote && state.isIn(HabitatBlocks.RAFFLESIA.get()) && state.get(RafflesiaBlock.AGE) == 2) {
                    TileEntity tile = worldIn.getTileEntity(pos);
                    if (tile instanceof RafflesiaTileEntity && !state.get(RafflesiaBlock.HAS_STEW) && !state.get(RafflesiaBlock.POLLINATED)) {
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
                        return ShearsBehaviour.dispense(source, stack);
                }
                else
                    return ShearsBehaviour.dispense(source, stack);
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
    }
}
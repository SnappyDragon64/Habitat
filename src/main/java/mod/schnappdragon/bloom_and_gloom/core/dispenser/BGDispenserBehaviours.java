package mod.schnappdragon.bloom_and_gloom.core.dispenser;

import mod.schnappdragon.bloom_and_gloom.common.block.KabloomBushBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.RafflesiaBlock;
import mod.schnappdragon.bloom_and_gloom.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.bloom_and_gloom.common.tileentity.RafflesiaTileEntity;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BGDispenserBehaviours {
    public static IDispenseItemBehavior ShearsBehaviour;

    public static void registerDispenserBehaviour() {
        ShearsBehaviour = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(Items.SHEARS);

        DispenserBlock.registerDispenseBehavior(Items.SUSPICIOUS_STEW, new OptionalDispenseBehavior() {
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World worldIn = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                BlockState state = worldIn.getBlockState(pos);
                if (!worldIn.isRemote && state.isIn(BGBlocks.RAFFLESIA.get()) && state.get(RafflesiaBlock.AGE) == 2) {
                    TileEntity tile = worldIn.getTileEntity(pos);
                    if (tile instanceof RafflesiaTileEntity && !state.get(RafflesiaBlock.HAS_STEW) && !state.get(RafflesiaBlock.POLLINATED)) {
                        RafflesiaTileEntity rafflesia = (RafflesiaTileEntity) tile;
                        CompoundNBT tag = stack.getTag();
                        if (tag != null && tag.contains("Effects", 9)) {
                            rafflesia.Effects = tag.getList("Effects", 10);
                        }
                        worldIn.setBlockState(pos, state.with(RafflesiaBlock.HAS_STEW, true));
                        rafflesia.onChange(worldIn, worldIn.getBlockState(pos));
                        worldIn.playSound(null, pos, BGSoundEvents.BLOCK_RAFFLESIA_SLURP.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
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
                if (!worldIn.isRemote && state.isIn(BGBlocks.KABLOOM_BUSH.get()) && state.get(KabloomBushBlock.AGE) == 7) {
                    ItemEntity item = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BGItems.KABLOOM_FRUIT.get(), 1));
                    item.setDefaultPickupDelay();
                    worldIn.addEntity(item);
                    worldIn.setBlockState(pos, state.with(KabloomBushBlock.AGE, 3));
                    worldIn.playSound(null, pos, BGSoundEvents.BLOCK_KABLOOM_BUSH_SHEAR.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                    if (stack.attemptDamageItem(1, worldIn.getRandom(), null))
                        stack.setCount(0);
                    this.setSuccessful(true);
                }
                else
                    return ShearsBehaviour.dispense(source, stack);
                return stack;
            }
        });

        DispenserBlock.registerDispenseBehavior(BGItems.KABLOOM_FRUIT.get(), new ProjectileDispenseBehavior() {
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
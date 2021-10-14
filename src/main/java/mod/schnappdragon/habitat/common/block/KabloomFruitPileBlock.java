package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class KabloomFruitPileBlock extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public KabloomFruitPileBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.fullCube();
    }

    /*
     * Kabloom Bush Function Methods
     */

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        explode(worldIn, pos, true, false);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        explode(worldIn, pos, true, false);
        return ActionResultType.func_233537_a_(worldIn.isRemote);
    }

    @Override
    public void catchFire(BlockState state, World worldIn, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        explode(worldIn, pos, true, true);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        explode(worldIn, pos, false, false);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        ItemStack held = player.getHeldItemMainhand();

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, held) == 0 || !(held.getItem() instanceof ToolItem))
            explode(worldIn, pos, false, false);

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    private void explode(World worldIn, BlockPos pos, boolean destroyBlock, boolean setFire) {
        if (destroyBlock && !worldIn.isRemote)
            worldIn.destroyBlock(pos, false);

        worldIn.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, HabitatSoundEvents.ENTITY_KABLOOM_FRUIT_EXPLODE.get(), SoundCategory.NEUTRAL, 1.0F, (1.0F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F), true);
        worldIn.addParticle(ParticleTypes.EXPLOSION, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 1.0D, 0.0D, 0.0D);

        for (int i = 1; i <= 8; i++) {
            KabloomFruitEntity kabloom = new KabloomFruitEntity(worldIn, pos.getX() + worldIn.rand.nextFloat(), pos.getY() + worldIn.rand.nextFloat(), pos.getZ() + worldIn.rand.nextFloat());
            if (setFire)
                kabloom.setFire(8);
            worldIn.addEntity(kabloom);
        }
    }
}
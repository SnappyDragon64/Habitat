package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.entity.projectile.KabloomFruit;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class KabloomFruitPileBlock extends Block implements IHasPistonDestroyEffect {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D);
    public KabloomFruitPileBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /*
     * Kabloom Bush Function Methods
     */

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        explode(worldIn, pos, entityIn instanceof Projectile ? ((Projectile) entityIn).getOwner() : entityIn, true, false);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        explode(worldIn, pos, player, true, false);
        return InteractionResult.sidedSuccess(worldIn.isClientSide);
    }

    @Override
    public void catchFire(BlockState state, Level worldIn, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        explode(worldIn, pos, igniter, true, true);
    }

    @Override
    public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
        explode(worldIn, pos, null, false, false);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        ItemStack held = player.getMainHandItem();

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, held) == 0 || !(held.getItem() instanceof DiggerItem))
            explode(worldIn, pos, player, false, false);

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public void onPistonDestroy(Level worldIn, BlockPos pos, BlockState state) {
        explode(worldIn, pos, null, false, false);
    }

    private void explode(Level worldIn, BlockPos pos, @Nullable Entity activator, boolean destroyBlock, boolean setFire) {
        if (!worldIn.isClientSide) {
            if (destroyBlock)
                worldIn.destroyBlock(pos, false);

            worldIn.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, HabitatSoundEvents.ENTITY_KABLOOM_FRUIT_EXPLODE.get(), SoundSource.NEUTRAL, 1.0F, (1.0F + (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.2F), true);
            worldIn.addParticle(ParticleTypes.EXPLOSION, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 1.0D, 0.0D, 0.0D);

            for (int i = 1; i <= 9; i++) {
                KabloomFruit kabloom = new KabloomFruit(worldIn, pos.getX() + worldIn.random.nextFloat(), pos.getY() + worldIn.random.nextFloat(), pos.getZ() + worldIn.random.nextFloat());
                kabloom.setOwner(activator);
                if (setFire)
                    kabloom.setSecondsOnFire(8);
                worldIn.addFreshEntity(kabloom);
            }
        }
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DANGER_OTHER;
    }
}
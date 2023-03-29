package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.entity.projectile.ThrownKabloomFruit;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
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

public class KabloomFruitPileBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public KabloomFruitPileBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /*
     * Kabloom Fruit Pile Function Methods
     */

    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState state1, boolean isMoving) {
        if (!state1.is(state.getBlock())) {
            if (world.hasNeighborSignal(pos)) {
                explode(world, pos, true, false);
            }
        }
    }

    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (world.hasNeighborSignal(pos))
            explode(world, pos, true, false);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        explode(worldIn, pos, true, false);
    }

    @Override
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hitResult, Projectile projectile) {
        explode(worldIn, hitResult.getBlockPos(), true, false);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        explode(worldIn, pos, true, false);
        return InteractionResult.sidedSuccess(worldIn.isClientSide);
    }

    @Override
    public void onCaughtFire(BlockState state, Level worldIn, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        explode(worldIn, pos, true, true);
    }

    @Override
    public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
        explode(worldIn, pos, false, false);
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel worldIn, BlockPos pos, ItemStack stack, boolean flag) {
        super.spawnAfterBreak(state, worldIn, pos, stack, flag);

        if (worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && stack.getEnchantmentLevel(Enchantments.SILK_TOUCH) == 0)
            explode(worldIn, pos, false, false);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    private void explode(Level worldIn, BlockPos pos, boolean destroyBlock, boolean setFire) {
        if (!worldIn.isClientSide) {
            if (destroyBlock)
                worldIn.destroyBlock(pos, false);

            worldIn.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, HabitatSoundEvents.KABLOOM_FRUIT_EXPLODE.get(), SoundSource.NEUTRAL, 1.0F, (1.0F + (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.2F), true);
            worldIn.addParticle(ParticleTypes.EXPLOSION, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 1.0D, 0.0D, 0.0D);

            for (int i = 1; i <= 9; i++) {
                ThrownKabloomFruit kabloom = new ThrownKabloomFruit(worldIn, pos.getX() + worldIn.random.nextFloat(), pos.getY() + worldIn.random.nextFloat(), pos.getZ() + worldIn.random.nextFloat());
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
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DANGER_OTHER;
    }
}
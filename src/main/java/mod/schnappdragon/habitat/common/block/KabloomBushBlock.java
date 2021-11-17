package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.entity.projectile.ThrownKabloomFruit;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class KabloomBushBlock extends BushBlock implements BonemealableBlock, HasPistonDestroyEffect {
    protected static final VoxelShape[] SHAPES = {Block.box(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D)};
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public KabloomBushBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(AGE)];
    }

    /*
     * Kabloom Bush Function Methods
     */

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (state.getValue(AGE) == 7 && entityIn.getType() != EntityType.BEE)
            dropFruit(state, worldIn, pos, entityIn instanceof Projectile ? ((Projectile) entityIn).getOwner() : entityIn, true, false);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(AGE) == 7) {
            if (player.getItemInHand(handIn).getItem() instanceof ShearsItem) {
                popResource(worldIn, pos, new ItemStack(HabitatItems.KABLOOM_FRUIT.get(), 1));
                player.getItemInHand(handIn).hurtAndBreak(1, player, (playerIn) -> {
                    playerIn.broadcastBreakEvent(handIn);
                });
                worldIn.setBlock(pos, state.setValue(AGE, 3), 2);
                worldIn.playSound(null, pos, HabitatSoundEvents.KABLOOM_BUSH_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            } else
                dropFruit(state, worldIn, pos, player, true, false);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void catchFire(BlockState state, Level worldIn, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if (state.getValue(AGE) == 7)
            dropFruit(state, worldIn, pos, igniter, true, true);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        ItemStack held = player.getMainHandItem();

        if (state.getValue(AGE) == 7 && !player.getAbilities().instabuild && !(held.getItem() instanceof ShearsItem) && (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, held) == 0 || !(held.getItem() instanceof DiggerItem)))
            dropFruit(state, worldIn, pos, player, false, false);

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public void onPistonDestroy(Level worldIn, BlockPos pos, BlockState state) {
        if (state.getValue(AGE) == 7)
            dropFruit(state, worldIn, pos, null, false, false);
    }

    private void dropFruit(BlockState state, Level worldIn, BlockPos pos, @Nullable Entity activator, boolean replaceBush, boolean setFire) {
        if (!worldIn.isClientSide) {
            if (replaceBush) {
                worldIn.setBlock(pos, state.setValue(AGE, 3), 2);
                worldIn.playSound(null, pos, HabitatSoundEvents.KABLOOM_BUSH_RUSTLE.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            }

            ThrownKabloomFruit kabloom = new ThrownKabloomFruit(worldIn, pos.getX() + 0.5F, pos.getY() + 0.6F, pos.getZ() + 0.5F);
            kabloom.setOwner(activator);
            if (setFire)
                kabloom.setSecondsOnFire(8);
            worldIn.addFreshEntity(kabloom);
        }
    }

    /*
     * Growth Methods
     */

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 7;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (state.getValue(AGE) < 7 && ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(5) == 0)) {
            worldIn.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 7;
    }

    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlock(pos, state.setValue(AGE, Math.min(7, state.getValue(AGE) + Mth.nextInt(rand, 2, 4))), 2);
    }

    /*
     * Plant Method
     */

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return state.getValue(AGE) > 1 ? BlockPathTypes.DANGER_OTHER : null;
    }
}
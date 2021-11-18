package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.state.properties.HabitatBlockStateProperties;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class FairyRingMushroomBlock extends BushBlock implements BonemealableBlock {
    protected static final VoxelShape[] SHAPE = {Block.box(6.0D, 0.0D, 6.0D, 10.0D, 13.0D, 10.0D), Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D), Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)};

    public static final IntegerProperty MUSHROOMS = HabitatBlockStateProperties.MUSHROOMS_1_4;
    public static final BooleanProperty DUSTED = HabitatBlockStateProperties.DUSTED;

    public FairyRingMushroomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MUSHROOMS, 1).setValue(DUSTED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MUSHROOMS, DUSTED);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE[state.getValue(MUSHROOMS) - 1];
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).isSolidRender(worldIn, pos.below());
    }

    /*
     * Conversion method
     */

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.getType() == EntityType.RABBIT && entityIn.isAlive()) {
            Rabbit rabbit = (Rabbit) entityIn;
            rabbit.playSound(HabitatSoundEvents.RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isBaby() ? (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.5F : (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.0F);
            rabbit.discard();
            worldIn.addFreshEntity(Pooka.convertRabbit(rabbit));

            for (int i = 0; i < 8; i++)
                ((ServerLevel) worldIn).sendParticles(HabitatParticleTypes.FAIRY_RING_SPORE.get(), rabbit.getRandomX(0.5D), rabbit.getY(0.5D), rabbit.getRandomZ(0.5D), 0, rabbit.getRandom().nextGaussian(), 0.0D, rabbit.getRandom().nextGaussian(), 0.01D);
        }
    }

    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, Level worldIn, BlockPos pos, Random rand) {
        if (state.getValue(DUSTED) && rand.nextInt(18 - 2 * state.getValue(MUSHROOMS)) == 0)
            worldIn.addParticle(DustParticleOptions.REDSTONE, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0.0D, 0.0D, 0.0D);

        if (rand.nextInt(9 - state.getValue(MUSHROOMS)) == 0)
            worldIn.addParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), rand.nextGaussian() * 0.01D, 0.0D, rand.nextGaussian() * 0.01D);
    }

    /*
     * Right Click Method
     */

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() instanceof ShearsItem && state.getValue(MUSHROOMS) > 1) {
            popResource(worldIn, pos, new ItemStack(defaultBlockState().getBlock()));
            player.getItemInHand(handIn).hurtAndBreak(1, player, (playerIn) -> {
                playerIn.broadcastBreakEvent(handIn);
            });
            worldIn.gameEvent(player, GameEvent.SHEAR, pos);
            worldIn.setBlock(pos, state.setValue(MUSHROOMS, state.getValue(MUSHROOMS) - 1), 2);
            worldIn.playSound(null, pos, HabitatSoundEvents.FAIRY_RING_MUSHROOM_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        if (player.getItemInHand(handIn).getItem() == HabitatItems.FAIRY_RING_MUSHROOM.get() && state.getValue(MUSHROOMS) < 4) {
            if (!player.getAbilities().instabuild)
                player.getItemInHand(handIn).shrink(1);
            worldIn.setBlock(pos, state.setValue(MUSHROOMS, state.getValue(MUSHROOMS) + 1), 2);
            worldIn.playSound(null, pos, SoundType.GRASS.getPlaceSound(), SoundSource.BLOCKS, SoundType.GRASS.getVolume() + 1.0F / 2.0F, SoundType.GRASS.getPitch() * 0.8F);
            worldIn.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        if (player.getItemInHand(handIn).getItem() == Items.REDSTONE && !state.getValue(DUSTED)) {
            if (!player.getAbilities().instabuild)
                player.getItemInHand(handIn).shrink(1);
            worldIn.setBlock(pos, state.setValue(DUSTED, true), 2);
            worldIn.addParticle(DustParticleOptions.REDSTONE, pos.getX() + 0.5D, pos.getY() + 0.125D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            worldIn.playSound(null, pos, HabitatSoundEvents.FAIRY_RING_MUSHROOM_DUST.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            worldIn.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    /*
     * Redstone Power Method
     */

    @Override
    public int getSignal(BlockState state, BlockGetter worldIn, BlockPos pos, Direction side) {
        return state.getValue(DUSTED) ? state.getValue(MUSHROOMS) : 0;
    }

    /*
     * Growth Methods
     */

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(MUSHROOMS) < 4 && !state.getValue(DUSTED);
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (state.getValue(MUSHROOMS) < 4 && ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(25) == 0)) {
            worldIn.setBlock(pos, state.setValue(MUSHROOMS, state.getValue(MUSHROOMS) + 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !state.getValue(DUSTED);
    }

    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return !state.getValue(DUSTED) && (state.getValue(MUSHROOMS) != 4 || rand.nextFloat() < (worldIn.getBlockState(pos.below()).is(BlockTags.MUSHROOM_GROW_BLOCK) ? 0.8F : 0.4F));
    }

    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.getValue(MUSHROOMS) < 4)
            worldIn.setBlock(pos, state.setValue(MUSHROOMS, Math.min(4, state.getValue(MUSHROOMS) + Mth.nextInt(rand, 1, 2))), 2);
        else
            growHugeMushroom(worldIn, rand, pos, state);
    }

    private void growHugeMushroom(ServerLevel world, Random rand, BlockPos pos, BlockState state) {
        world.removeBlock(pos, false);
        ConfiguredFeature<?, ?> configuredfeature = HabitatConfiguredFeatures.HUGE_FAIRY_RING_MUSHROOM;

        if (!configuredfeature.place(world, world.getChunkSource().getGenerator(), rand, pos))
            world.setBlock(pos, state, 3);
    }
}
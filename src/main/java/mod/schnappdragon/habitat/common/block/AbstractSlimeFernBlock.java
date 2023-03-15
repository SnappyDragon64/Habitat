package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public abstract class AbstractSlimeFernBlock extends Block implements BonemealableBlock {
    public static final BooleanProperty SLIMY = BooleanProperty.create("slimy");

    public AbstractSlimeFernBlock(Properties properties) {
        super(properties);
    }

    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, Level worldIn, BlockPos pos, RandomSource rand) {
        if (state.getValue(SLIMY) && rand.nextInt(10) == 0) {
            VoxelShape voxelshape = this.getShape(state, worldIn, pos, CollisionContext.empty());
            Vec3 vector3d = voxelshape.bounds().getCenter();
            double X = (double) pos.getX() + vector3d.x;
            double Y = (double) pos.getY() + vector3d.y;
            double Z = (double) pos.getZ() + vector3d.z;
            worldIn.addParticle(HabitatParticleTypes.FALLING_SLIME.get(), X + (2 * rand.nextDouble() - 1.0F) / 2.5D, Y - rand.nextDouble() / 5, Z + (2 * rand.nextDouble() - 1.0F) / 2.5D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_51240_) {
        Level worldIn = p_51240_.getLevel();
        boolean flag = false;

        if (!worldIn.isClientSide()) {
            BlockPos pos = p_51240_.getClickedPos();
            ChunkPos chunkPos = new ChunkPos(pos);
            flag = WorldgenRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, ((WorldGenLevel) worldIn).getSeed(), 987234911L).nextInt(10) == 0;
        }

        return this.defaultBlockState().setValue(SLIMY, flag);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() == Items.SLIME_BALL && !state.getValue(SLIMY)) {
            if (!player.getAbilities().instabuild)
                player.getItemInHand(handIn).shrink(1);
            worldIn.setBlock(pos, state.setValue(SLIMY, true), 2);
            worldIn.addParticle(ParticleTypes.ITEM_SLIME, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            worldIn.playSound(null, pos, HabitatSoundEvents.SLIME_FERN_COAT.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            worldIn.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    /*
     * Entity Walk Method
     */

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.getType() != EntityType.SLIME) {
            double motionY = Math.abs(entityIn.getDeltaMovement().y);
            if (motionY < 0.1D && !entityIn.isSteppingCarefully()) {
                double slowedMotion = 0.4D + motionY * 0.2D;
                entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(slowedMotion, 1.0D, slowedMotion));
            }
        }

        super.entityInside(state, worldIn, pos, entityIn);
    }

    /*
     * IGrowable Methods
     */

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for (int j = 0; j < 3; ++j) {
            blockpos$mutable.setWithOffset(pos, Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2));

            if (worldIn.isEmptyBlock(blockpos$mutable) || worldIn.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) {
                for (Direction dir : Direction.values()) {
                    if (worldIn.getBlockState(blockpos$mutable.relative(dir)).isFaceSturdy(worldIn, blockpos$mutable, dir.getOpposite())) {
                        BlockState state1 = HabitatBlocks.SLIME_FERN.get().defaultBlockState();

                        if (dir == Direction.UP)
                            state1 = state1.setValue(SlimeFernBlock.ON_CEILING, true);
                        else if (dir != Direction.DOWN)
                            state1 = HabitatBlocks.WALL_SLIME_FERN.get().defaultBlockState().setValue(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite());

                        worldIn.setBlock(blockpos$mutable, state1, 3);
                        break;
                    }
                }
            }
        }
    }

    /*
     * Piston Push Reaction Method
     */

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}

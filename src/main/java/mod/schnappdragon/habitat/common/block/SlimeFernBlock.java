package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.state.properties.HabitatBlockStateProperties;
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
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SlimeFernBlock extends DirectionalBlock implements BonemealableBlock {
    private static final VoxelShape UP_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    private static final VoxelShape DOWN_SHAPE = Block.box(0.0D, 1.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 2.0D, 1.0D, 16.0D, 14.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE =  Block.box(0.0D, 2.0D, 0.0D, 16.0D, 14.0D, 15.0D);
    private static final VoxelShape WEST_SHAPE = Block.box(1.0D, 2.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    private static final VoxelShape EAST_SHAPE = Block.box(0.0D, 2.0D, 0.0D, 15.0D, 14.0D, 16.0D);

    public static final BooleanProperty SLIMY = HabitatBlockStateProperties.SLIMY;

    public SlimeFernBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(SLIMY, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SLIMY);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case DOWN -> {
                return DOWN_SHAPE;
            }
            case NORTH -> {
                return NORTH_SHAPE;
            }
            case SOUTH -> {
                return SOUTH_SHAPE;
            }
            case WEST -> {
                return WEST_SHAPE;
            }
            case EAST -> {
                return EAST_SHAPE;
            }
            default -> {
                return UP_SHAPE;
            }
        }
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        Level worldIn = context.getLevel();
        boolean flag = false;

        if (!worldIn.isClientSide()) {
            BlockPos pos = context.getClickedPos();
            ChunkPos chunkPos = new ChunkPos(pos);
            flag = WorldgenRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, ((WorldGenLevel) worldIn).getSeed(), 987234911L).nextInt(10) == 0;
        }

        return this.defaultBlockState().setValue(FACING, direction).setValue(SLIMY, flag);
    }

    public BlockState updateShape(BlockState stateIn, Direction direction, BlockState directionState, LevelAccessor worldIn, BlockPos currentPos, BlockPos directionPos) {
        return getBlockConnected(stateIn) == direction && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, direction, directionState, worldIn, currentPos, directionPos);
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Direction dir = getBlockConnected(state);
        BlockState stateConnected = worldIn.getBlockState(pos.relative(dir));
        return dir == Direction.DOWN && stateConnected.is(Blocks.FARMLAND) || stateConnected.isFaceSturdy(worldIn, pos.relative(dir), dir.getOpposite());
    }

    protected static Direction getBlockConnected(BlockState state) {
        return state.getValue(FACING).getOpposite();
    }

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

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos blockpos$mutable1 = new BlockPos.MutableBlockPos();

        for (int j = 0; j < 3; ++j) {
            blockpos$mutable.setWithOffset(pos, Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2), Mth.nextInt(rand, 1, 2) - Mth.nextInt(rand, 1, 2));

            if (worldIn.isEmptyBlock(blockpos$mutable)) {
                for (Direction dir : Direction.values()) {
                    blockpos$mutable1.setWithOffset(blockpos$mutable, dir);

                    if (worldIn.getBlockState(blockpos$mutable1).isFaceSturdy(worldIn, blockpos$mutable1, dir.getOpposite())) {
                        BlockState state1 = this.defaultBlockState().setValue(DirectionalBlock.FACING, dir.getOpposite());
                        worldIn.setBlock(blockpos$mutable, state1, 3);
                    }
                }
            }
        }
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}

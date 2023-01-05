package mod.schnappdragon.habitat.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class GrowingBallCactusBlock extends AbstractBallCactusBlock implements BonemealableBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D);
    protected static final AABB TOUCH_AABB = new AABB(0.25D, 0, 0.25D, 0.75D, 0.25D, 0.75D);

    public GrowingBallCactusBlock(BallCactusColor colorIn, Properties properties) {
        super(colorIn, properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return new ItemStack(getColor().getFlower());
    }

    /*
     * Entity Collision Method
     */

    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE && worldIn.getEntities(null, TOUCH_AABB.move(pos)).contains(entityIn)) {
            entityIn.hurt(DamageSource.CACTUS, 1.0F);
        }
    }

    /*
     * Growth Methods
     */

    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(10) == 0)) {
            worldIn.setBlockAndUpdate(pos, getColor().getBallCactus().defaultBlockState());
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        worldIn.setBlockAndUpdate(pos, (rand.nextBoolean() ? getColor().getBallCactus() : getColor().getFloweringBallCactus()).defaultBlockState());
    }
}
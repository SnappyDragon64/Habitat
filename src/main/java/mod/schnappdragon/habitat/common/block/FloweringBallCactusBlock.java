package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ToolActions;

public class FloweringBallCactusBlock extends AbstractBallCactusBlock implements BonemealableBlock {
    public FloweringBallCactusBlock(BallCactusColor colorIn, Properties properties) {
        super(colorIn, properties);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return new ItemStack(getColor().getBallCactus());
    }

    /*
     * Shearing Method
     */

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).canPerformAction(ToolActions.SHEARS_HARVEST)) {
            popResource(worldIn, pos, new ItemStack(getColor().getFlower()));
            player.getItemInHand(handIn).hurtAndBreak(1, player, (playerIn) -> {
                playerIn.broadcastBreakEvent(handIn);
            });
            worldIn.gameEvent(player, GameEvent.SHEAR, pos);
            worldIn.setBlock(pos, getColor().getBallCactus().defaultBlockState(), 2);
            worldIn.playSound(null, pos, HabitatSoundEvents.FLOWERING_BALL_CACTUS_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean flag) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
        return rand.nextInt(5) < 2;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
        level.removeBlock(pos, false);
        ResourceLocation hugeBallCactusFeature = this.getColor().getHugeBallCactus();
        ConfiguredFeature<?, ?> configuredfeature = level.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY).get(hugeBallCactusFeature);

        if (!configuredfeature.place(level, level.getChunkSource().getGenerator(), rand, pos))
            level.setBlock(pos, state, 3);
    }
}
package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatEffects;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KabloomPulpBlock extends HalfTransparentBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public KabloomPulpBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isSlimeBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    public boolean canStickTo(BlockState state, BlockState other) {
        return !other.is(HabitatBlockTags.KABLOOM_PULP_BLOCK_DOES_NOT_STICK_TO);
    }

    @Override
    public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
        entityIn.makeStuckInBlock(worldIn.getBlockState(pos), new Vec3(0.95F, 0.9F, 0.95F));

        if (entityIn instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(HabitatEffects.BLAST_ENDURANCE.get(), 100));
        }
    }

    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float f) {
        if (!world.isClientSide) {
            showParticles(entity, 10);
        }

        if (entity.causeFallDamage(f, 0.5F, DamageSource.FALL)) {
            entity.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
        }
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && (entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ()) && world.random.nextInt(10) == 0) {
            showParticles(entity, 5);
        }

        super.entityInside(state, world, pos, entity);
    }

    private static void showParticles(Entity entity, int n) {
        BlockState blockstate = HabitatBlocks.KABLOOM_PULP_BLOCK.get().defaultBlockState();
        ((ServerLevel) entity.level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), entity.getX(), entity.getY(), entity.getZ(), n, 0.0D, 0.0D, 0.0D, 0.0D);
    }
}
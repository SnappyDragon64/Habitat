package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class KabloomPulpBlock extends HalfTransparentBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public KabloomPulpBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void stepOn(Level worldIn, BlockPos pos, Entity entityIn) {
        entityIn.makeStuckInBlock(worldIn.getBlockState(pos), new Vec3(0.95F, 0.9F, 0.95F));

        if (entityIn instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entityIn;
            living.addEffect(new MobEffectInstance(HabitatEffects.BLAST_ENDURANCE.get(), 100));
        }
    }
}
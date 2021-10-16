package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.registry.HabitatEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class KabloomPulpBlock extends BreakableBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 15.0D, 15.0D, 15.0D);

    public KabloomPulpBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotionMultiplier(worldIn.getBlockState(pos), new Vector3d(0.95F, 0.9F, 0.95F));

        if (entityIn instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entityIn;
            living.addPotionEffect(new EffectInstance(HabitatEffects.BLAST_ENDURANCE.get(), 100));
        }
    }
}
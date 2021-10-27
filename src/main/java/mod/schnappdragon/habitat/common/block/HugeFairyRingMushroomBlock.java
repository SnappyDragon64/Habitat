package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.entity.monster.PookaEntity;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class HugeFairyRingMushroomBlock extends HugeMushroomBlock {
    public HugeFairyRingMushroomBlock(Properties properties) {
        super(properties);
    }

    /*
     * Conversion Method
     */

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.getType() == EntityType.RABBIT) {
            RabbitEntity rabbit = (RabbitEntity) entityIn;
            rabbit.playSound(HabitatSoundEvents.ENTITY_RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isChild() ? (rabbit.getRNG().nextFloat() - rabbit.getRNG().nextFloat()) * 0.2F + 1.5F : (rabbit.getRNG().nextFloat() - rabbit.getRNG().nextFloat()) * 0.2F + 1.0F);
            rabbit.remove();
            worldIn.addEntity(PookaEntity.convertRabbit(rabbit));

            for (int i = 0; i < 8; i++)
                ((ServerWorld) worldIn).spawnParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), rabbit.getPosXRandom(0.5D), rabbit.getPosYHeight(0.5D), rabbit.getPosZRandom(0.5D), 0, rabbit.getRNG().nextGaussian(), 0.0D, rabbit.getRNG().nextGaussian(), 0.01D);
        }
    }

    /*
     * Particle Animation Method
     */

    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(8) == 0)
            worldIn.addParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), rand.nextGaussian() * 0.01D, 0.0D, rand.nextGaussian() * 0.01D);
    }
}
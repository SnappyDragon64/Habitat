package mod.schnappdragon.bloom_and_gloom.common.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeature;

import java.util.Random;

public class SlimeChunkRandomPatchFeature extends RandomPatchFeature {
    public SlimeChunkRandomPatchFeature(Codec<BlockClusterFeatureConfig> p_i231979_1_) {
        super(p_i231979_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        ChunkPos chunkPos = new ChunkPos(pos);
        return (SharedSeedRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, reader.getSeed(), 987234911L).nextInt(10) == 0) && super.generate(reader, generator, rand, pos, config);
    }
}

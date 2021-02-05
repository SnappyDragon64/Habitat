package mod.schnappdragon.bloom_and_gloom.common.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;

public class FairyRingFeature extends Feature<NoFeatureConfig> {
    public FairyRingFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (reader.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK)) {
            int spread = 2 + (rand.nextInt(10) == 0 ? 1 : (rand.nextInt(6) == 0) ? -1 : 0);

            ArrayList<BlockPos.Mutable> list = new ArrayList<>();

            for (int i = -spread; i <= spread + 1; i++) {
                for (int j = -spread; j <= spread + 1; j++) {
                    BlockPos.Mutable blockpos$mutable = pos.add(i, 0, j).toMutable();
                    double distance = MathHelper.sqrt(pos.distanceSq(blockpos$mutable));

                    if (distance >= spread & distance < spread + 1) {
                        if ((reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable() || reader.isAirBlock(blockpos$mutable)) && reader.getBlockState(blockpos$mutable.down()).isIn(Blocks.GRASS_BLOCK))
                            list.add(blockpos$mutable);
                        else
                            return false;
                    }

                    if (distance < spread && (!reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable() && !reader.isAirBlock(blockpos$mutable) || !reader.getBlockState(blockpos$mutable.down()).isIn(Blocks.GRASS_BLOCK)))
                        return false;
                }
            }

            for (BlockPos.Mutable blockpos$mutable : list) {
                reader.setBlockState(blockpos$mutable, Blocks.SHROOMLIGHT.getDefaultState(), 2);
            }
            return true;
        }
        return false;
    }
}

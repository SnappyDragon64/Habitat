package mod.schnappdragon.habitat.common.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

public class FairyRingStructure extends Structure<NoFeatureConfig> {
    public FairyRingStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public  IStartFactory<NoFeatureConfig> getStartFactory() {
        return FairyRingStructure.Start::new;
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    /*
    private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
    );

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
        return STRUCTURE_MONSTERS;
    }

    private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of(
            new MobSpawnInfo.Spawners(EntityType.RABBIT, 100, 1, 2)
    );

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
        return STRUCTURE_CREATURES;
    }
    */

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos center = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
        int landHeight = chunkGenerator.getNoiseHeight(center.getX(), center.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
        IBlockReader columnOfBlocks = chunkGenerator.func_230348_a_(center.getX(), center.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(center.up(landHeight));

        return topBlock.getFluidState().isEmpty();
    }

    public static class Start extends StructureStart<NoFeatureConfig>  {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
            int x = chunkX << 4;
            int z = chunkZ << 4;

            BlockPos blockpos = new BlockPos(x, 0, z);

            JigsawManager.func_242837_a(
                    dynamicRegistryManager,
                    new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY)
                            .getOrDefault(new ResourceLocation(Habitat.MOD_ID, "fairy_ring/start_pool")),
                            10),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    blockpos,
                    this.components,
                    this.rand,
                    false,
                    true);

            this.recalculateStructureSize();

            Habitat.getLOGGER().debug("Fairy Ring at " +
                    this.components.get(0).getBoundingBox().maxX + " " +
                    this.components.get(0).getBoundingBox().maxY + " " +
                    this.components.get(0).getBoundingBox().maxZ);
        }

    }
}
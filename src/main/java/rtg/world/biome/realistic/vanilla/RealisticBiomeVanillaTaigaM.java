package rtg.world.biome.realistic.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import rtg.api.biome.BiomeConfig;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaTaigaM;
import rtg.world.biome.deco.collection.DecoCollectionTaiga;
import rtg.world.gen.surface.vanilla.SurfaceVanillaTaigaM;
import rtg.world.gen.terrain.vanilla.TerrainVanillaTaigaM;

public class RealisticBiomeVanillaTaigaM extends RealisticBiomeVanillaBase
{
    public static BiomeGenBase standardBiome = Biomes.taiga;
    public static BiomeGenBase mutationBiome = Biomes.getBiome(standardBiome.biomeID + MUTATION_ADDEND);
    
    public static Block topBlock = mutationBiome.topBlock;
    public static Block fillerBlock = mutationBiome.fillerBlock;
    
    public RealisticBiomeVanillaTaigaM(BiomeConfig config)
    {
    
        super(config, 
            mutationBiome,
            BiomeGenBase.river,
            new TerrainVanillaTaigaM(70f, 180f, 7f, 100f, 38f, 160f, 68f),
            new SurfaceVanillaTaigaM(config, topBlock, fillerBlock)
        );
        this.noLakes=true;
        
        this.addDecoCollection(new DecoCollectionTaiga(this.config._boolean(BiomeConfigVanillaTaigaM.decorationLogsId), 10f));
    }
}

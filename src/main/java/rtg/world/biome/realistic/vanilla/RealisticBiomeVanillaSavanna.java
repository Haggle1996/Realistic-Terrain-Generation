package rtg.world.biome.realistic.vanilla;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

import rtg.api.biome.BiomeConfig;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaSavanna;
import rtg.world.biome.deco.collection.DecoCollectionDesertRiver;
import rtg.world.biome.deco.collection.DecoCollectionSavanna;
import rtg.world.gen.surface.vanilla.SurfaceVanillaSavanna;
import rtg.world.gen.terrain.vanilla.TerrainVanillaSavanna;

public class RealisticBiomeVanillaSavanna extends RealisticBiomeVanillaBase
{
    public static Biome biome = Biomes.SAVANNA;
    public static Biome river = Biomes.RIVER;
    
    public RealisticBiomeVanillaSavanna(BiomeConfig config)
    {
        super(config, biome, river,
            new TerrainVanillaSavanna(),
            new SurfaceVanillaSavanna(config, biome.topBlock, biome.fillerBlock, biome.topBlock, 13f, 0.27f)
        );

        this.addDecoCollection(new DecoCollectionDesertRiver());
        this.addDecoCollection(new DecoCollectionSavanna(this.config._boolean(BiomeConfigVanillaSavanna.decorationLogsId)));
    }
}

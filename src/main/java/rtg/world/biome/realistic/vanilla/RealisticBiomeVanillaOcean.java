package rtg.world.biome.realistic.vanilla;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

import rtg.api.biome.BiomeConfig;
import rtg.world.biome.deco.DecoBaseBiomeDecorations;
import rtg.world.gen.surface.vanilla.SurfaceVanillaOcean;
import rtg.world.gen.terrain.vanilla.TerrainVanillaOcean;

public class RealisticBiomeVanillaOcean extends RealisticBiomeVanillaBase
{
    public static Biome biome = Biomes.OCEAN;
    public static Biome river = Biomes.RIVER;
    
    public RealisticBiomeVanillaOcean(BiomeConfig config)
    {
        super(config, biome, river,
            new TerrainVanillaOcean(),
            new SurfaceVanillaOcean(config, Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState(), 20f, 0.2f));
        
        this.waterSurfaceLakeChance = 0;
        this.lavaSurfaceLakeChance = 0;
        this.noLakes=true;
        this.noWaterFeatures = true;

		DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
		this.addDeco(decoBaseBiomeDecorations);
    }
}

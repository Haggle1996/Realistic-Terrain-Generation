package rtg.world.gen.surface;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import rtg.api.biome.BiomeConfig;
import rtg.util.CellNoise;
import rtg.util.OpenSimplexNoise;

public class SurfaceMountainPolar extends SurfaceBase
{
	private boolean beach;
	private IBlockState beachBlock;
	private float min;
	
	public SurfaceMountainPolar(BiomeConfig config, IBlockState top, IBlockState fill, boolean genBeach, IBlockState genBeachBlock, float minCliff) 
	{
	    super(config, top, fill);
		beach = genBeach;
		beachBlock = genBeachBlock;
		min = minCliff;
	}

    @Override
	public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int y, int depth, World world, Random rand, OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, Biome[] base)
	{

	}
}
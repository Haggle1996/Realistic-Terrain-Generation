package rtg.world.gen;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.MINESHAFT;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.OCEAN_MONUMENT;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.SCATTERED_FEATURE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.STRONGHOLD;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.VILLAGE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import rtg.api.biome.BiomeConfig;
import rtg.config.rtg.ConfigRTG;
import rtg.util.Acceptor;
import rtg.util.Accessor;
import rtg.util.BiomeUtils;
import rtg.util.CanyonColour;
import rtg.util.CellNoise;
import rtg.util.Compass;
import rtg.util.Converter;
import rtg.util.Direction;
import rtg.util.LimitedMap;
import rtg.util.LimitedSet;
import rtg.util.OpenSimplexNoise;
import rtg.util.PlaneLocation;
import rtg.util.SimplexCellularNoise;
import rtg.util.TimeTracker;
import rtg.util.TimedHashSet;
import rtg.world.WorldTypeRTG;
import rtg.world.biome.BiomeAnalyzer;
import rtg.world.biome.RTGBiomeProvider;
import rtg.world.biome.WorldChunkManagerRTG;
import rtg.world.biome.realistic.RealisticBiomeBase;
import rtg.world.biome.realistic.RealisticBiomePatcher;


public class ChunkProviderRTG implements IChunkGenerator
{
    /**
     * Declare variables.
     */

    private final MapGenBase caveGenerator;
    private final MapGenBase ravineGenerator;
    private final MapGenStronghold strongholdGenerator;
    private final MapGenMineshaft mineshaftGenerator;
    private final MapGenVillage villageGenerator;
    private final MapGenScatteredFeature scatteredFeatureGenerator;
    private final StructureOceanMonument oceanMonumentGenerator;
    private boolean mapFeaturesEnabled;
    private final int worldHeight;
    private final boolean isRTGWorld;
    private final int sampleSize = 8;
    private final int sampleArraySize;
    private BiomeAnalyzer analyzer = new BiomeAnalyzer();
    private int [] xyinverted = analyzer.xyinverted();
    
    private IBlockState bedrockBlock = Block.getBlockFromName(ConfigRTG.bedrockBlockId).getStateFromMeta(ConfigRTG.bedrockBlockByte);

    private Random rand;
    private Random mapRand;
    private World worldObj;
    protected RTGBiomeProvider cmr;
    private final LandscapeGenerator landscapeGenerator;
    private OpenSimplexNoise simplex;
    private CellNoise cell;
	//private RealisticBiomeBase[] biomesForGeneration;
	private Biome[] baseBiomesList;
    private int[] biomeData;
    private float[] testHeight;
    private boolean[] biomesGeneratedInChunk;
    private float[] borderNoise;
    private float[] [] weightings;
    private long worldSeed;
    private RealisticBiomePatcher biomePatcher;
    private HashMap<PlaneLocation,Chunk> inGeneration = new HashMap<PlaneLocation,Chunk>();
    private HashSet<PlaneLocation> toCheck = new HashSet<PlaneLocation>();
    private static String rtgTerrain = "RTG Terrain";
    private static String rtgNoise = "RTG Noise";
    
    private Set<Long> serverLoadingChunks;
    // we have to store this callback because it's a WeakReference in the event manager
    public final Acceptor<ChunkEvent.Load> delayedDecorator = new Acceptor<ChunkEvent.Load>() {

        @Override
        public void accept(ChunkEvent.Load accepted) {
            if (accepted.isCanceled()) return;
            int cx = accepted.getChunk().xPosition;
            int cy = accepted.getChunk().zPosition;
            PlaneLocation location = new PlaneLocation.Invariant(cx, cy);
            if (!toCheck.contains(location)) return;
            toCheck.remove(location);
            for (Direction forPopulation: directions) {
                decorateIfOtherwiseSurrounded(worldObj.getChunkProvider(), location, forPopulation);
            }
            //clearDecorations(0);
        }

    };

    Accessor<ChunkProviderServer,Set<Long>> forServerLoadingChunks =
            new Accessor<ChunkProviderServer,Set<Long>>("loadingChunks");

    private Compass compass = new Compass();
    ArrayList<Direction> directions = compass.directions();

    public ChunkProviderRTG(World world, long l)
    {
        worldObj = world;
        cmr = (WorldChunkManagerRTG) worldObj.getBiomeProvider();
        worldHeight = worldObj.provider.getActualHeight();

        rand = new Random(l);
        simplex = new OpenSimplexNoise(l);
        cell = new SimplexCellularNoise(l);

        landscapeGenerator = new LandscapeGenerator(simplex,cell);

    	mapRand = new Random(l);
    	worldSeed = l;

        Map m = new HashMap();
        m.put("size", "0");
        m.put("distance", "24");

        mapFeaturesEnabled = world.getWorldInfo().isMapFeaturesEnabled();
        isRTGWorld = world.getWorldInfo().getTerrainType() instanceof WorldTypeRTG;

        if (isRTGWorld && ConfigRTG.enableCaveModifications) {
            caveGenerator = TerrainGen.getModdedMapGen(new MapGenCavesRTG(), CAVE);
        }
        else {
            caveGenerator = TerrainGen.getModdedMapGen(new MapGenCaves(), CAVE);
        }
        
        if (isRTGWorld && ConfigRTG.enableRavineModifications) {
            ravineGenerator = TerrainGen.getModdedMapGen(new MapGenRavineRTG(), RAVINE);
        }
        else {
            ravineGenerator = TerrainGen.getModdedMapGen(new MapGenRavine(), RAVINE);
        }

        villageGenerator = (MapGenVillage) TerrainGen.getModdedMapGen(new MapGenVillage(m), VILLAGE);
		strongholdGenerator = (MapGenStronghold) TerrainGen.getModdedMapGen(new MapGenStronghold(), STRONGHOLD);
		mineshaftGenerator = (MapGenMineshaft) TerrainGen.getModdedMapGen(new MapGenMineshaft(), MINESHAFT);
		scatteredFeatureGenerator = (MapGenScatteredFeature) TerrainGen.getModdedMapGen(new MapGenScatteredFeature(), SCATTERED_FEATURE);
		oceanMonumentGenerator = (StructureOceanMonument) TerrainGen.getModdedMapGen(new StructureOceanMonument(), OCEAN_MONUMENT);

		CanyonColour.init(l);

        sampleArraySize = sampleSize * 2 + 5;

        baseBiomesList = new Biome[256];
        biomeData = new int[sampleArraySize * sampleArraySize];
    	testHeight = new float[256];
    	biomesGeneratedInChunk = new boolean[256];
    	borderNoise = new float[256];
    	biomePatcher = new RealisticBiomePatcher();

        // set up the cache of available chunks
        availableChunks = new LimitedMap<PlaneLocation,Chunk>(1000);
        setWeightings();

        // check for bogus world
        if (worldObj == null) throw new RuntimeException("Attempt to create chunk provider without a world");
    }

    private void setWeightings() {
        weightings = new float [sampleArraySize * sampleArraySize][256];
        int adjustment = 4;// this should actually vary with sampleSize
        for (int i = 0; i < 16; i++) {
            for (int j=0; j<16; j++) {
    			int locationIndex = ((int)(i + adjustment) * 25 + (j + adjustment));
                TimeTracker.manager.start("Weighting");
                float totalWeight = 0;
                float limit = (float)Math.pow((56f*56f),.7);
                // float limit = 56f;

                for (int mapX = 0 ; mapX < sampleArraySize; mapX ++) {
                    for (int mapZ = 0 ; mapZ < sampleArraySize; mapZ ++) {
                        float xDist = (i - chunkCoordinate(mapX));
                        float yDist = (j - chunkCoordinate(mapZ));
                        float distanceSquared = xDist*xDist + yDist*yDist;
                        //float distance = (float)Math.sqrt(distanceSquared);
                        float distance = (float)Math.pow(distanceSquared,.7);
                        float weight = 1f - distance/limit;
                        if (weight <0) weight = 0;
                        weightings[mapX*sampleArraySize + mapZ][i * 16 + j] = weight;
                    }
                }
            }
        }
    }
    public void isFakeGenerator() {
        this.mapFeaturesEnabled = false;
    }

    /**
     * @see IChunkProvider
     *
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    //private HashSet<PlaneLocation> everGenerated = new HashSet<PlaneLocation>();
    private TimedHashSet<PlaneLocation> chunkMade  = new TimedHashSet<PlaneLocation>(5*1000);
    private final LimitedMap<PlaneLocation,Chunk> availableChunks;
    public Chunk provideChunk(final int cx, final int cy)
    {
        ChunkPrimer primer = new ChunkPrimer();
        final PlaneLocation chunkLocation = new PlaneLocation.Invariant(cx,cy);
        if (inGeneration.containsKey(chunkLocation)) {
            return inGeneration.get(chunkLocation);
        }
        //if (availableChunks.size() > 1000) throw new RuntimeException();
        if (chunkMade.contains(chunkLocation)) {
            Chunk available;
            available = availableChunks.get(chunkLocation);
            // this should never be happening but it came up when Forge/MC re-requested an already
            // made chunk for a lighting check (???)

            // we are having a problem with Forge complaining about double entity registration
            // so we'll unload any loaded entities
            if (available != null) {
                List [] entityLists = (List[]) available.getEntityLists();
                for (int i = 0; i< entityLists.length; i++) {
                    Iterator iterator = entityLists[i].iterator();
                    while (iterator.hasNext()) {

                        iterator.next();
                        iterator.remove();
                    }
                    worldObj.unloadEntities(entityLists[i]);
                }
                toCheck.add(chunkLocation);
                return available;
            }
        }

        //if (everGenerated.contains(chunkLocation)) throw new RuntimeException();
        
        TimeTracker.manager.start(rtgTerrain);
    	rand.setSeed((long)cx * 0x4f9939f508L + (long)cy * 0x1ef1565bd5L);
        IBlockState[] blocks = new IBlockState[65536];
        int k;

        ChunkLandscape landscape = landscapeGenerator.landscape(cmr, cx *16, cy*16);

        generateTerrain(cmr, cx, cy, blocks, landscape.biome, landscape.noise);
        // that routine can change the blocks.
        //get standard biome Data

        for (int ci = 0; ci < 256; ci++) {
            biomesGeneratedInChunk[BiomeUtils.getId(landscape.biome[ci].baseBiome)] = true;
        }

        for(k = 0; k < 256; k++)
        {
            if(biomesGeneratedInChunk[k] )
            {
                RealisticBiomeBase.getBiome(k).generateMapGen(blocks, worldSeed, worldObj, cmr, mapRand, cx, cy, simplex, cell, landscape.noise);
                biomesGeneratedInChunk[k] = false;
            }
            try {
                baseBiomesList[k] = landscape.biome[k].baseBiome;
            } catch (Exception e) {
                baseBiomesList[k] = biomePatcher.getPatchedBaseBiome(""+BiomeUtils.getId(landscape.biome[k].baseBiome));
            }
        }

        replaceBlocksForBiome(cx, cy, primer, landscape.biome, baseBiomesList, landscape.noise);

        caveGenerator.generate(this.worldObj, cx, cy, primer);
        ravineGenerator.generate(this.worldObj, cx, cy, primer);

        if (mapFeaturesEnabled) {

            if (ConfigRTG.generateMineshafts) {
                mineshaftGenerator.generate(this.worldObj, cx, cy, primer);
            }
            
            if (ConfigRTG.generateStrongholds) {
                strongholdGenerator.generate(this.worldObj, cx, cy, primer);
            }
            
            if (ConfigRTG.generateVillages) {
                
                if (ConfigRTG.villageCrashFix) {
                    
                    try {
                        villageGenerator.generate(this.worldObj, cx, cy, primer);
                    }
                    catch (Exception e) {
                        // Do nothing.
                    }
                }
                else {
                    villageGenerator.generate(this.worldObj, cx, cy, primer);
                }
            }
            
            if (ConfigRTG.generateScatteredFeatures) {
                scatteredFeatureGenerator.generate(this.worldObj, cx, cy, primer);
            }
            
            if (ConfigRTG.generateOceanMonuments) {
                oceanMonumentGenerator.generate(this.worldObj, cx, cy, primer);
            }
        }
        
        // store in the in process pile
        Chunk chunk = new Chunk(this.worldObj, primer, cx, cy);
        inGeneration.put(chunkLocation, chunk);

    	// doJitter no longer needed as the biome array gets fixed
    	byte[] abyte1 = chunk.getBiomeArray();
    	for (k = 0; k < abyte1.length; ++k)
    	{
    		byte b = (byte)BiomeUtils.getId(this.baseBiomesList[this.xyinverted[k]]);
    		abyte1[k] = b;
    	}
    	chunk.setBiomeArray(abyte1);

        chunk.generateSkylightMap();
        toCheck.add(chunkLocation);

        // remove from in process pile
        inGeneration.remove(chunkLocation);
        this.chunkMade.add(chunkLocation);
        //this.everGenerated.add(chunkLocation);
        /*if (!chunkMade.contains(chunkLocation)||!everGenerated.contains(chunkLocation)) {
            throw new RuntimeException(chunkLocation.toString() +  chunkMade.size());
        }*/
        availableChunks.put(chunkLocation,chunk);
        TimeTracker.manager.stop(rtgTerrain);
        return chunk;
    }

    public void decorateIfOtherwiseSurrounded(IChunkProvider world, PlaneLocation source, Direction fromNewChunk) {

        // check if this is the master provider
        if (WorldTypeRTG.chunkProvider != this) return;

        // see if otherwise surrounded besides the new chunk
        int cx = source.x() + fromNewChunk.xOffset;
        int cy = source.z() + fromNewChunk.zOffset;
        
        // check to see if already decorated; shouldn't be but just in case
        probe.setX(cx);
        probe.setZ(cy);
        if (this.alreadyDecorated.contains(probe)) return;
        // if an in-process chunk; we'll get a populate call later;
        // if (this.inGeneration.containsKey(probe)) return;

        for (Direction checked: directions) {
            if (checked == compass.opposite(fromNewChunk)) continue; // that's the new chunk
            if (!chunkExists(world,cx+checked.xOffset, cy+checked.zOffset)) return;// that one's missing
        }
        // passed all checks
        addToDecorationList(new PlaneLocation.Invariant(cx,cy));
        //this.doPopulate(world, cx, cy);
    }

    public void generateTerrain(RTGBiomeProvider cmr, int cx, int cy, IBlockState[] blocks, RealisticBiomeBase biomes[], float[] noise)
    {
    	int p, h;
    	for(int i = 0; i < 16; i++)
    	{
    		for(int j = 0; j < 16; j++)
    		{
    			h = (int)noise[j * 16 + i];

    			for(int k = 0; k < 256; k++)
    			{
    				p = (j * 16 + i) * 256 + k;
    				if(k > h)
    				{
    					if(k < 63)
        				{
        					blocks[p] = Blocks.WATER.getDefaultState();
        				}
        				else
        				{
        					blocks[p] = Blocks.AIR.getDefaultState();
        				}
    				}
    				else
    				{
    					blocks[p] = Blocks.STONE.getDefaultState();
    				}
    			}
    		}
    	}
    }

    private static final int centerLocationIndex = 312;// this is x=8, y=8 with the calcs below

    private boolean totalNotOne(float [] tested) {
        float total = 0;
        for (int i = 0; i < tested.length; i++) {
            total += tested[i];
        }
        if (total<.999||total>1.001f) return true;
        return false;
    }

    private int chunkCoordinate(int biomeMapCoordinate) {
        return (biomeMapCoordinate - sampleSize)*8;
    }

    public String description(float [] biomeArray) {
        String result = "";
        for (int i = 0 ; i < BiomeUtils.getRegisteredBiomes().length; i ++) {
            if (biomeArray[i]>0) {
                result += " " + i + " " + biomeArray[i];
            }
        }
        return result;
    }

    public static String firstBlock;
    public static String biomeLayoutActivity = "Biome Layout";

    public void replaceBlocksForBiome(int cx, int cy, ChunkPrimer primer, RealisticBiomeBase[] biomes, Biome[] base, float[] n)
    {
        ChunkGeneratorEvent.ReplaceBiomeBlocks event = new ChunkGeneratorEvent.ReplaceBiomeBlocks(this, cx, cy, primer, this.worldObj);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return;

    	int i, j, depth;
    	float river;
    	for(i = 0; i < 16; i++)
    	{
    		for(j = 0; j < 16; j++)
    		{
    			RealisticBiomeBase biome = biomes[j * 16 + i];

    			river = -cmr.getRiverStrength(cx * 16 + j, cy * 16 + i);
    			depth = -1;

    			biome.rReplace(primer, cx * 16 + j, cy * 16 + i, i, j, depth, worldObj, rand, simplex, cell, n, river, base);

                int rough;
                int flatBedrockLayers = ConfigRTG.flatBedrockLayers;
                flatBedrockLayers = flatBedrockLayers < 0 ? 0 : (flatBedrockLayers > 5 ? 5 : flatBedrockLayers);

                if (flatBedrockLayers > 0) {
                    for (int bl = 0; bl < flatBedrockLayers; bl++) {
                        primer.setBlockState(i, bl, j, bedrockBlock);
                    }
                } else {

                    primer.setBlockState(i, 0, j, bedrockBlock);

                    rough = rand.nextInt(2);
                    primer.setBlockState(i, rough, j, bedrockBlock);

                    rough = rand.nextInt(3);
                    primer.setBlockState(i, rough, j, bedrockBlock);

                    rough = rand.nextInt(4);
                    primer.setBlockState(i, rough, j, bedrockBlock);

                    rough = rand.nextInt(5);
                    primer.setBlockState(i, rough, j, bedrockBlock);
                }
    		}
    	}
    }

    /**
     * @see IChunkProvider
     *
     * Loads or generates the chunk at the chunk location specified.
     */
    @SuppressWarnings("unused")
	public Chunk loadChunk(int par1, int par2)
    {
        if (1>0) throw new RuntimeException();
        return provideChunk(par1, par2);
    }

    private double[] func_4061_a(double ad[], int i, int j, int k, int l, int i1, int j1)
    {
    	return null;
    }

    private boolean chunkExists(IChunkProvider world, int par1, int par2) {
        //if (chunkExists(par1,par2)) return true;
        PlaneLocation location = new PlaneLocation.Invariant(par1,par2);
        if (inGeneration.containsKey(location)) return true;
        if (toCheck.contains(location)) return true;
        if (this.chunkMade.contains(location)) return true;
        //if  (world.chunkExists(par1, par2)) return true;
        if (chunkLoader().chunkExists(worldObj, par1, par2)) return true;
        //if (this.everGenerated.contains(location)) throw new RuntimeException("somehow lost "+location.toString());
        return false;
    }

    private PlaneLocation.Probe probe = new PlaneLocation.Probe(0, 0);
    /**
     * @see IChunkProvider
     *
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        probe.setX(par1);
        probe.setZ(par2);
        /**
         * TODO: Write custom logic to determine whether chunk exists, instead of assuming it does.
         */
        return this.inGeneration.containsKey(probe);
        //return true;
    }
    /**
     * @see IChunkProvider
     *
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider ichunkprovider, int chunkX, int chunkZ){
        // check if this is the master provider
        if (WorldTypeRTG.chunkProvider != this) return;
        //if (this.alreadyDecorated.contains(new PlaneLocation.Invariant(chunkX, chunkZ))) return;
        if (this.neighborsDone(ichunkprovider, chunkX, chunkZ)) {
            this.doPopulate(ichunkprovider, chunkX, chunkZ);
        }
        clearDecorations(0);
    }
    
    public Runnable clearOnServerClose() {
        return new Runnable () {
            public void run() {
                clearToDecorateList();
            }
        };
    }

    private void clearToDecorateList() {
        if (WorldTypeRTG.chunkProvider != this) return;
        if (populating) return;// in process, do later;
        // we have to make a copy of the set to work on or we'll get errors
        IChunkProvider ichunkprovider = worldObj.getChunkProvider();
        Set<PlaneLocation> toProcess = doableLocations(0);
        while (toProcess.size() >0) {
            for (PlaneLocation location: toProcess) {
                removeFromDecorationList(location);
            }
            for (PlaneLocation location: toProcess) {
                doPopulate(ichunkprovider,location.x(),location.z());
            }
            // and loop because the decorating might have created other chunks to decorate;
            toProcess = doableLocations(0);
        }
    }

    private void clearDecorations(int limit) {
        if (WorldTypeRTG.chunkProvider != this) return;
        IChunkProvider ichunkprovider = worldObj.getChunkProvider();
        Set<PlaneLocation> toProcess = doableLocations(limit);
        for (PlaneLocation location: toProcess) {
                removeFromDecorationList(location);
            }
        for (PlaneLocation location: toProcess) {
            doPopulate(ichunkprovider,location.x(),location.z());
        }
    }

    private Set<PlaneLocation> doableLocations(int limit) {
        HashSet<PlaneLocation> toProcess = new HashSet<PlaneLocation>();
        int found = 0;
        synchronized(toDecorate) {
            for (PlaneLocation location: toDecorate) {
                Chunk existing;
                existing = availableChunks.get(location);
                if (existing != null) {
                    if (!existing.isTerrainPopulated()) {
                        //continue; // not populated so let more "normal" systems handle it
                    }
                }
                if (inGeneration.containsKey(location)) continue;
                toProcess.add(location);
                if (++found == limit) return toProcess;
            }
        }
        return toProcess;
    }

    private Converter<Chunk,PlaneLocation> keyer() {
        return new Converter<Chunk,PlaneLocation>() {

            @Override
            public final PlaneLocation result(Chunk original) {
                return new PlaneLocation.Invariant(original.xPosition, original.zPosition);
            }

        };
    }
    private boolean populating = false;
    private static ChunkProviderRTG populatingProvider;

    private final HashSet<PlaneLocation> toDecorate = new HashSet<PlaneLocation>();
    private LimitedSet<PlaneLocation> alreadyDecorated  = new LimitedSet<PlaneLocation>(1000);
    //private HashSet<PlaneLocation> everDecorated  = new HashSet<PlaneLocation>();

    private final void addToDecorationList(PlaneLocation toAdd) {
        synchronized(toDecorate) {
            toDecorate.add(toAdd);
        }
    }

    private final void removeFromDecorationList(PlaneLocation toAdd) {
        synchronized(toDecorate) {
            toDecorate.remove(toAdd);
        }
    }

    private void doPopulate(IChunkGenerator ichunkprovider, int chunkX, int chunkZ)
    {
        // don't populate if already done

        PlaneLocation location = new PlaneLocation.Invariant(chunkX, chunkZ);
        //Logger.warn("trying to decorate "+location.toString());
        if (alreadyDecorated.contains(location)) return;

        if (populating) {
            // this has been created by another decoration; put in todo pile
            addToDecorationList(location);
            return;
        }

        if (populatingProvider != null) {
            throw new RuntimeException(toString() + " " + populatingProvider.toString());
        }
        if (inGeneration.containsKey(location)) {
            addToDecorationList(location);
            return;
        }
        //Logger.warn("decorating");
        alreadyDecorated.add(location);
        populating = true;
        populatingProvider = this;

        TimeTracker.manager.start("RTG populate");
        TimeTracker.manager.start("Features");
        BlockFalling.fallInstantly = true;

		int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;
        TimeTracker.manager.start(biomeLayoutActivity);
        RealisticBiomeBase biome = cmr.getBiomeDataAt(worldX + 16, worldZ + 16);
        TimeTracker.manager.stop(biomeLayoutActivity);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)chunkX * i1 + (long)chunkZ * j1 ^ this.worldObj.getSeed());
        boolean hasPlacedVillageBlocks = false;
        ChunkPos chunkCoords = new ChunkPos(chunkX, chunkZ);
        BlockPos worldCoords = new BlockPos(worldX, 0, worldZ);
        boolean gen = false;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(ichunkprovider, worldObj, rand, chunkX, chunkZ, hasPlacedVillageBlocks));

        if (mapFeaturesEnabled) {
        
            TimeTracker.manager.start("Mineshafts");
            if (ConfigRTG.generateMineshafts) {
                mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkCoords);
            }
            TimeTracker.manager.stop("Mineshafts");
            
            TimeTracker.manager.start("Strongholds");
            if (ConfigRTG.generateStrongholds) {
                strongholdGenerator.generateStructure(this.worldObj, this.rand, chunkCoords);
            }
            TimeTracker.manager.stop("Strongholds");
            
            TimeTracker.manager.start("Villages");
            if (ConfigRTG.generateVillages) {
                if (ConfigRTG.villageCrashFix) {
                    try {
                        hasPlacedVillageBlocks = villageGenerator.generateStructure(this.worldObj, this.rand, chunkCoords);
                    }
                    catch (Exception e) {
                        hasPlacedVillageBlocks = false;
                    }
                }
                else {
                    hasPlacedVillageBlocks = villageGenerator.generateStructure(this.worldObj, this.rand, chunkCoords);
                }
            }
            TimeTracker.manager.stop("Villages");
            
            TimeTracker.manager.start("Scattered");
            if (ConfigRTG.generateScatteredFeatures) {
                scatteredFeatureGenerator.generateStructure(this.worldObj, this.rand, chunkCoords);
            }
            TimeTracker.manager.stop("Scattered");
            
            TimeTracker.manager.start("OceanMonuments");
            if (ConfigRTG.generateOceanMonuments) {
                oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, chunkCoords);
            }
            TimeTracker.manager.stop("OceanMonuments");
        }

        TimeTracker.manager.start("Pools");
        biome.rPopulatePreDecorate(ichunkprovider, worldObj, rand, chunkX, chunkZ, hasPlacedVillageBlocks);
        TimeTracker.manager.stop("Pools");

        /**
         * What is this doing? And why does it need to be done here? - Pink
         * Answer: building a frequency table of nearby biomes - Zeno. 
         */

        final int adjust = 24;// seems off? but decorations aren't matching their chunks.

        TimeTracker.manager.start(biomeLayoutActivity);
        for (int bx = -4; bx <= 4; bx++) {
            
        	for(int by = -4; by <= 4; by++)
        	{
        		borderNoise[landscapeGenerator.getBiomeDataAt(cmr,worldX + adjust + bx * 4, worldZ + adjust  + by * 4)] += 0.01234569f;
        	}
        }
        TimeTracker.manager.stop(biomeLayoutActivity);
        TimeTracker.manager.stop("Features");
        /**
         * ########################################################################
         * # START DECORATE BIOME
         * ########################################################################
         */

        TimeTracker.manager.start("Decorations");
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, rand, new BlockPos(worldX, 0, worldZ)));

        //Initialise variables.
        float river = -cmr.getRiverStrength(worldX + 16, worldZ + 16);
        
        //Clay.
        biome.rDecorateClay(worldObj, rand, chunkX, chunkZ, river, worldX, worldZ);
        
        //Border noise. (Does this have to be done here? - Pink)
        RealisticBiomeBase realisticBiome;
        float snow = 0f;
        
        for(int bn = 0; bn < 256; bn++)
        {
        	if(borderNoise[bn] > 0f)
        	{
        		if(borderNoise[bn] >= 1f)
        		{
        			borderNoise[bn] = 1f;
        		}

                realisticBiome = RealisticBiomeBase.getBiome(bn);
                
                // Do we need to patch the biome?
                if (realisticBiome == null)
                {
                    realisticBiome = biomePatcher.getPatchedRealisticBiome("NULL biome (" + bn + ") found when generating border noise.");
                }
                
                /**
                 * When decorating the biome, we need to look at the biome configs to see if RTG is allowed to decorate it.
                 * If the biome configs don't allow it, then we try to let the base biome decorate itself.
                 * However, there are some mod biomes that crash when they try to decorate themselves,
                 * so that's what the try/catch is for. If it fails, then it falls back to RTG decoration.
                 */
                if (ConfigRTG.enableRTGBiomeDecorations && realisticBiome.config._boolean(BiomeConfig.useRTGDecorationsId)) {

                	realisticBiome.decorateInAnOrderlyFashion(this.worldObj, this.rand, worldX, worldZ, simplex, cell, borderNoise[bn], river, hasPlacedVillageBlocks);
                }
                else {
                    
                    try {
                        
                        realisticBiome.baseBiome.decorate(this.worldObj, rand, new BlockPos(worldX, 0, worldZ));
                    }
                    catch (Exception e) {

                    	realisticBiome.decorateInAnOrderlyFashion(this.worldObj, this.rand, worldX, worldZ, simplex, cell, borderNoise[bn], river, hasPlacedVillageBlocks);
                    }
                }

                if(realisticBiome.baseBiome.getTemperature() < 0.15f)
                {
                	snow -= 0.6f * borderNoise[bn];
                }
                else
                {
                	snow += 0.6f * borderNoise[bn];
                }
                borderNoise[bn] = 0f;
        	}
        }
        
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, rand, new BlockPos(worldX, 0, worldZ)));

        TimeTracker.manager.stop("Decorations");
        /**
         * ########################################################################
         * # END DECORATE BIOME
         * ########################################################################
         */
        TimeTracker.manager.start("Post-decorations");
        biome.rPopulatePostDecorate(this, worldObj, rand, chunkX, chunkZ, hasPlacedVillageBlocks);
        
        //Flowing water.
        if (ConfigRTG.flowingWaterChance > 0) {
	        if (rand.nextInt(ConfigRTG.flowingWaterChance) == 0) {
	    		for(int l18 = 0; l18 < 50; l18++)
	    		{
	    			int l21 = worldX + rand.nextInt(16);// + 8;
	    			int k23 = rand.nextInt(rand.nextInt(worldHeight - 16) + 10);
	    			int l24 = worldZ + rand.nextInt(16);// + 8;
	    			(new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(worldObj, rand, new BlockPos(l21, k23, l24));
	    		}
	        }
        }

        //Flowing lava.
        if (ConfigRTG.flowingLavaChance > 0) {
	        if (rand.nextInt(ConfigRTG.flowingLavaChance) == 0) {
	    		for(int i19 = 0; i19 < 20; i19++)
	    		{
	    			int i22 = worldX + rand.nextInt(16);// + 8;
	    			int l23 = rand.nextInt(worldHeight / 2);
	    			int i25 = worldZ + rand.nextInt(16);// + 8;
	    			(new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(worldObj, rand, new BlockPos(i22, l23, i25));
	    		}
	        }
        }

        TimeTracker.manager.stop("Post-decorations");
        TimeTracker.manager.start("Entities");
        probe.setX(chunkX);
        probe.setZ(chunkZ);
        //if (everDecorated.contains(probe)) throw new RuntimeException();
        if (TerrainGen.populate(this, this.worldObj, rand, chunkX, chunkZ, hasPlacedVillageBlocks, PopulateChunkEvent.Populate.EventType.ANIMALS)) {
            WorldEntitySpawner.performWorldGenSpawning(this.worldObj, this.worldObj.getBiome(new BlockPos(worldX + 16, 0, worldZ + 16)), worldX + 8, worldZ + 8, 16, 16, this.rand);
        }

        TimeTracker.manager.stop("Entities");
        TimeTracker.manager.start("Ice");
        //everDecorated.add(location);
        probe.setX(chunkX);
        probe.setZ(chunkZ);
        //if (!everDecorated.contains(probe)) throw new RuntimeException();
        if (TerrainGen.populate(this, worldObj, rand, chunkX, chunkZ, hasPlacedVillageBlocks, PopulateChunkEvent.Populate.EventType.ICE)) {

            int k1, l1, i2;
            
            for(k1 = 0; k1 < 16; ++k1) {
                
                for(l1 = 0; l1 < 16; ++l1) {
                    
                    i2 = this.worldObj.getPrecipitationHeight(new BlockPos(worldX + k1, 0, worldZ + l1)).getY();

                    if(this.worldObj.canBlockFreezeWater(new BlockPos(k1 + worldX, i2 - 1, l1 + worldZ))) {
                        this.worldObj.setBlockState(new BlockPos(k1 + worldX, i2 - 1, l1 + worldZ), Blocks.ICE.getDefaultState(), 2);
                    }

                    if (ConfigRTG.enableSnowLayers && this.worldObj.canSnowAtBody(new BlockPos(k1 + worldX, i2, l1 + worldZ), true)) {
                        this.worldObj.setBlockState(new BlockPos(k1 + worldX, i2, l1 + worldZ), Blocks.SNOW_LAYER.getDefaultState(), 2);
                    }
                }
            }
        } else {
            throw new RuntimeException();
        }
        TimeTracker.manager.stop("Ice");

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(ichunkprovider, worldObj, rand, chunkX, chunkZ, hasPlacedVillageBlocks));

        BlockFalling.fallInstantly = false;
        TimeTracker.manager.stop("RTG populate");
        populating = false;
        populatingProvider = null;
    }



    public boolean neighborsDone(IChunkProvider world, int cx, int cz) {
        if (!chunkExists(world,cx - 1, cz - 1)) return false;
        if (!chunkExists(world,cx - 1, cz)) return false;
        if (!chunkExists(world,cx - 1, cz + 1)) return false;
        if (!chunkExists(world,cx, cz - 1)) return false;
        if (!chunkExists(world,cx, cz + 1)) return false;
        if (!chunkExists(world,cx + 1, cz - 1)) return false;
        if (!chunkExists(world,cx + 1, cz)) return false;
        if (!chunkExists(world,cx + 1, cz + 1)) return false;
        return true;
    }
    /**
     * @see IChunkProvider
     *
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * @see IChunkProvider
     *
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    public boolean unload100OldestChunks()
    {
        return false;
    }

    /**
     * @see IChunkProvider
     *
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * IChunkProvider
     *
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "ChunkProviderRTG";
    }

    /**
     * @see IChunkProvider
     */
    public int getLoadedChunkCount()
    {
        return 0;
    }

    /**
     * @see IChunkProvider
     *
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.
     * Currently unimplemented.
     */
    public void saveExtraData() {}


    private AnvilChunkLoader chunkLoader;
    private AnvilChunkLoader chunkLoader() {
        if (chunkLoader == null) {
            ChunkProviderServer server = (ChunkProviderServer)(worldObj.getChunkProvider());
            chunkLoader = (AnvilChunkLoader)(server.chunkLoader);
        }
        return chunkLoader;
    }

    public Set<Long> serverLoadingChunks() {
        if (this.serverLoadingChunks == null) {
            ChunkProviderServer server = (ChunkProviderServer)(worldObj.getChunkProvider());
            chunkLoader = (AnvilChunkLoader)(server.chunkLoader);
            serverLoadingChunks = forServerLoadingChunks.get(server);
        }
        return serverLoadingChunks;
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    /**
     * @see IChunkProvider
     * <p/>
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @Override
    public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome var5 = this.worldObj.getBiome(pos);
        if (this.mapFeaturesEnabled) {
            if (creatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.isSwampHut(pos)) {
                return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
            }

            if (creatureType == EnumCreatureType.MONSTER && ConfigRTG.generateOceanMonuments && this.oceanMonumentGenerator.isPositionInStructure(this.worldObj, pos)) {
                return this.oceanMonumentGenerator.getScatteredFeatureSpawnList();
            }
        }
        return var5 == null ? null : var5.getSpawnableList(creatureType);
    }

    /**
     * @see IChunkProvider
     */
    @Override
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
    {
        if (!ConfigRTG.generateStrongholds) {
            return null;
        }

        return "Stronghold".equals(structureName) && this.strongholdGenerator != null ? this.strongholdGenerator.getClosestStrongholdPos(worldIn, position) : null;
    }

    /**
     * @see IChunkProvider
     */
    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {

        if (mapFeaturesEnabled) {

            if (ConfigRTG.generateMineshafts) {
                mineshaftGenerator.generate(this.worldObj, x, z, null);
            }

            if (ConfigRTG.generateStrongholds) {
                strongholdGenerator.generate(this.worldObj, x, z, null);
            }

            if (ConfigRTG.generateVillages) {

                if (ConfigRTG.villageCrashFix) {

                    try {
                        villageGenerator.generate(this.worldObj, x, z, null);
                    } catch (Exception e) {
                        // Do nothing.
                    }

                } else {
                    villageGenerator.generate(this.worldObj, x, z, null);
                }
            }

            if (ConfigRTG.generateScatteredFeatures) {
                scatteredFeatureGenerator.generate(this.worldObj, x, z, null);
            }

            if (ConfigRTG.generateOceanMonuments) {
                oceanMonumentGenerator.generate(this.worldObj, x, z, null);
            }
        }
    }
}
package rtg.world.gen.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import net.minecraftforge.common.BiomeDictionary;

import rtg.config.rtg.ConfigRTG;
import rtg.util.Logger;
import rtg.world.WorldTypeRTG;

/**
 * Author: Choonster (https://github.com/Choonster)
 * Source: https://github.com/Choonster/TestMod2/blob/1575b85ad8949381215f3aeb6ca76ea2368074de/src/main/java/com/choonster/testmod2/world/gen/structure/MapGenScatteredFeatureModBiomes.java
 * Modified by: WhichOnesPink (https://github.com/whichonespink44)
 * <p/>
 * Allows scattered features (jungle/desert temples, witch huts) to spawn in modded biomes, equivalent to the vanilla biomes,
 * i.e. any config registered as JUNGLE, SANDY or SWAMP
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2471489-jungle-and-desert-temple-spawn-biome
 * <p/>
 * This class was modified by WhichOnesPink on 2015-11-05 to allow the spawning of scattered features ONLY
 * in biomes that have been registered with multiple BiomeDictionary types that are shared by their vanilla counterparts.
 * For example, desert temples don't generate in SANDY biomes - they are only allowed to generate in biomes that
 * have been registered as HOT + DRY + SANDY.
 * <p/>
 * This class has also been modified to resolve a very specific use case involving Thaumcraft world gen:
 * https://github.com/Team-RTG/Realistic-Terrain-Generation/issues/249
 */
public class MapGenScatteredFeatureRTG extends MapGenScatteredFeature {

    private static List biomelist = Arrays.asList(Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.SWAMPLAND, Biomes.COLD_TAIGA, Biomes.ICE_PLAINS);

    /**
     * contains possible spawns for scattered features
     */
    private List scatteredFeatureSpawnList;

    /**
     * the maximum distance between scattered features
     */
    private int maxDistanceBetweenScatteredFeatures;

    /**
     * the minimum distance between scattered features
     */
    private int minDistanceBetweenScatteredFeatures;

    public MapGenScatteredFeatureRTG(Map p_i2061_1_) {
        this();

        for (Object o : p_i2061_1_.entrySet()) {
            Entry entry = (Entry) o;

            if (entry.getKey().equals("distance")) {
                this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String) entry.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
            }
        }
    }

    public MapGenScatteredFeatureRTG() {
        int minDistance = ConfigRTG.minDistanceScatteredFeatures;
        int maxDistance = ConfigRTG.maxDistanceScatteredFeatures;

        if (minDistance > maxDistance) {
            minDistance = 8;
            maxDistance = 32;
        }

        this.scatteredFeatureSpawnList = new ArrayList();
        this.maxDistanceBetweenScatteredFeatures = maxDistance;
        this.minDistanceBetweenScatteredFeatures = minDistance;
        this.scatteredFeatureSpawnList.add(new Biome.SpawnListEntry(EntityWitch.class, 1, 1, 1));
    }

    @Override
    public String getStructureName() {
        return "Temple";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        if (!(this.worldObj.getWorldType() instanceof WorldTypeRTG)) return false;
        int k = chunkX;
        int l = chunkZ;

        if (chunkX < 0) {
            chunkX -= this.maxDistanceBetweenScatteredFeatures - 1;
        }

        if (chunkZ < 0) {
            chunkZ -= this.maxDistanceBetweenScatteredFeatures - 1;
        }

        int i1 = chunkX / this.maxDistanceBetweenScatteredFeatures;
        int j1 = chunkZ / this.maxDistanceBetweenScatteredFeatures;
        Random random = this.worldObj.setRandomSeed(i1, j1, 14357617);
        i1 *= this.maxDistanceBetweenScatteredFeatures;
        j1 *= this.maxDistanceBetweenScatteredFeatures;
        i1 += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        j1 += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);

        if (k == i1 && l == j1) {
            Biome biomegenbase = this.worldObj.getBiome(new BlockPos(k * 16 + 8, 0, l * 16 + 8));

            if (biomegenbase != null) {
                
                //Desert temple.
                if (canSpawnDesertTemple(biomegenbase)) {
                    return true;
                }
                
                //Jungle temple.
                if (canSpawnJungleTemple(biomegenbase)) {
                    return true;
                }
                
                //Witch hut.
                if (canSpawnWitchHut(biomegenbase)) {
                    return true;
                }
                
                //Igloo.
                if (canSpawnIgloo(biomegenbase)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new MapGenScatteredFeatureRTG.Start(this.worldObj, this.rand, chunkX, chunkZ);
    }

    /**
     * returns possible spawns for scattered features
     */
    @Override
    public List getScatteredFeatureSpawnList() {
        return Collections.unmodifiableList(this.scatteredFeatureSpawnList);
    }

    @Override
    public boolean isInsideStructure(BlockPos pos) {
        StructureStart structurestart = this.getStructureAt(pos);
        if (structurestart != null && structurestart instanceof MapGenScatteredFeatureRTG.Start && !structurestart.getComponents().isEmpty()) {
            StructureComponent structurecomponent = structurestart.getComponents().get(0);
            return structurecomponent instanceof ComponentScatteredFeaturePieces.SwampHut;
        } else {
            return false;
        }
    }

    public static class Start extends MapGenScatteredFeature.Start {
        public Start() {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ) {

            super(worldIn, random, chunkX, chunkZ);

            LinkedList desertTempleComponents = new LinkedList();
            LinkedList jungleTempleComponents = new LinkedList();
            LinkedList witchHutComponents = new LinkedList();
            LinkedList iglooComponents = new LinkedList();

            Biome biomegenbase = worldIn.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8));
            
            this.components.clear();
            
            if (canSpawnDesertTemple(biomegenbase)) {
                
                ComponentScatteredFeaturePieces.DesertPyramid desertpyramid = new ComponentScatteredFeaturePieces.DesertPyramid(random, chunkX * 16, chunkZ * 16);
                desertTempleComponents.add(desertpyramid);
                this.components.addAll(desertTempleComponents);
            }
            else if (canSpawnJungleTemple(biomegenbase)) {
                
                ComponentScatteredFeaturePieces.JunglePyramid junglepyramid = new ComponentScatteredFeaturePieces.JunglePyramid(random, chunkX * 16, chunkZ * 16);
                jungleTempleComponents.add(junglepyramid);
                this.components.addAll(jungleTempleComponents);
            }
            else if (canSpawnWitchHut(biomegenbase)) {
                
                ComponentScatteredFeaturePieces.SwampHut swamphut = new ComponentScatteredFeaturePieces.SwampHut(random, chunkX * 16, chunkZ * 16);
                witchHutComponents.add(swamphut);
                this.components.addAll(witchHutComponents);
            }
            
            this.updateBoundingBox();
            
            Logger.debug("Scattered feature candidate at %d, %d", chunkX * 16, chunkZ * 16);
        }
    }
    
    private static boolean canSpawnDesertTemple(Biome b)
    {
        boolean canSpawn = false;
        
        if (BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.HOT) && BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.DRY) && BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.SANDY)) {
            canSpawn = true;
        }
        
        return canSpawn;
    }
    
    private static boolean canSpawnJungleTemple(Biome b)
    {
        boolean canSpawn = false;
        
        if (BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.HOT) && BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.WET) && BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.JUNGLE)) {
            canSpawn = true;
        }
        
        return canSpawn;
    }
    
    private static boolean canSpawnWitchHut(Biome b)
    {
        boolean canSpawn = false;
        
        if (BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.WET) && BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.SWAMP)) {
            canSpawn = true;
        }
        
        return canSpawn;
    }
    
    private static boolean canSpawnIgloo(Biome b)
    {
        boolean canSpawn = false;
        
        if (BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.COLD) && BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.SNOWY)) {
            canSpawn = true;
        }
        
        return canSpawn;
    }
}
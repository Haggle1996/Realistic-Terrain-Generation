package rtg.config;

import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;
import rtg.api.biome.BiomeConfig;
import rtg.api.biome.BiomeConfigProperty;
import rtg.api.biome.vanilla.config.BiomeConfigVanilla;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaBeach;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaBirchForest;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaBirchForestHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaBirchForestHillsM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaBirchForestM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaColdBeach;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaColdTaiga;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaColdTaigaHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaColdTaigaM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaDeepOcean;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaDesert;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaDesertHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaDesertM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaExtremeHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaExtremeHillsEdge;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaExtremeHillsM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaExtremeHillsPlus;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaExtremeHillsPlusM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaFlowerForest;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaForest;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaForestHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaFrozenOcean;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaFrozenRiver;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaIceMountains;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaIcePlains;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaIcePlainsSpikes;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaJungle;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaJungleEdge;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaJungleEdgeM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaJungleHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaJungleM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMegaSpruceTaiga;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMegaTaiga;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMegaTaigaHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMesa;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMesaBryce;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMesaPlateau;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMesaPlateauF;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMesaPlateauFM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMesaPlateauM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMushroomIsland;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaMushroomIslandShore;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaOcean;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaPlains;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaRedwoodTaigaHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaRiver;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaRoofedForest;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaRoofedForestM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaSavanna;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaSavannaM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaSavannaPlateau;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaSavannaPlateauM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaStoneBeach;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaSunflowerPlains;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaSwampland;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaSwamplandM;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaTaiga;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaTaigaHills;
import rtg.api.biome.vanilla.config.BiomeConfigVanillaTaigaM;

public class BiomeConfigManager
{
    
    public static void initBiomeConfigs()
    {
        initBiomeConfigsAC();
        initBiomeConfigsAM();
        initBiomeConfigsATG();
        initBiomeConfigsBOP();
        initBiomeConfigsBC();
        initBiomeConfigsCC();
        initBiomeConfigsEB();
        initBiomeConfigsEBXL();
        initBiomeConfigsGC();
        initBiomeConfigsHL();
        initBiomeConfigsIC();
        initBiomeConfigsIDT();
        initBiomeConfigsRW();
        initBiomeConfigsTC();
        initBiomeConfigsVAMP();
        initBiomeConfigsLOM();
        initBiomeConfigsTOFU();
        initBiomeConfigsFN();
        initBiomeConfigsVanilla();
    }
    
    public static void initBiomeConfigsAC()
    {
        BiomeConfigAC.biomeConfigACCoraliumInfestedSwamp = new BiomeConfigACCoraliumInfestedSwamp();
        BiomeConfigAC.biomeConfigACDarklands = new BiomeConfigACDarklands();
        BiomeConfigAC.biomeConfigACDarklandsForest = new BiomeConfigACDarklandsForest();
        BiomeConfigAC.biomeConfigACDarklandsHighland = new BiomeConfigACDarklandsHighland();
        BiomeConfigAC.biomeConfigACDarklandsMountains = new BiomeConfigACDarklandsMountains();
        BiomeConfigAC.biomeConfigACDarklandsPlains = new BiomeConfigACDarklandsPlains();
    }
    
    public static void initBiomeConfigsAM()
    {
        BiomeConfigAM.biomeConfigAMWitchwoodForest = new BiomeConfigAMWitchwoodForest();
    }
    
    public static void initBiomeConfigsATG()
    {
        BiomeConfigATG.biomeConfigATGGravelBeach = new BiomeConfigATGGravelBeach();
        BiomeConfigATG.biomeConfigATGRockySteppe = new BiomeConfigATGRockySteppe();
        BiomeConfigATG.biomeConfigATGShrubland = new BiomeConfigATGShrubland();
        BiomeConfigATG.biomeConfigATGSnowyGravelBeach = new BiomeConfigATGSnowyGravelBeach();
        BiomeConfigATG.biomeConfigATGTropicalShrubland = new BiomeConfigATGTropicalShrubland();
        BiomeConfigATG.biomeConfigATGTundra = new BiomeConfigATGTundra();
        BiomeConfigATG.biomeConfigATGVolcano = new BiomeConfigATGVolcano();
        BiomeConfigATG.biomeConfigATGWoodland = new BiomeConfigATGWoodland();
    }
    
    public static void initBiomeConfigsBOP()
    {
        BiomeConfigBOP.biomeConfigBOPAlps = new BiomeConfigBOPAlps();
        BiomeConfigBOP.biomeConfigBOPArctic = new BiomeConfigBOPArctic();
        BiomeConfigBOP.biomeConfigBOPBambooForest = new BiomeConfigBOPBambooForest();
        BiomeConfigBOP.biomeConfigBOPBayou = new BiomeConfigBOPBayou();
        BiomeConfigBOP.biomeConfigBOPBog = new BiomeConfigBOPBog();
        BiomeConfigBOP.biomeConfigBOPBorealForest = new BiomeConfigBOPBorealForest();
        BiomeConfigBOP.biomeConfigBOPBrushland = new BiomeConfigBOPBrushland();
        BiomeConfigBOP.biomeConfigBOPCanyon = new BiomeConfigBOPCanyon();
        BiomeConfigBOP.biomeConfigBOPChaparral = new BiomeConfigBOPChaparral();
        BiomeConfigBOP.biomeConfigBOPCherryBlossomGrove = new BiomeConfigBOPCherryBlossomGrove();
        BiomeConfigBOP.biomeConfigBOPConiferousForest = new BiomeConfigBOPConiferousForest();
        BiomeConfigBOP.biomeConfigBOPCrag = new BiomeConfigBOPCrag();
        BiomeConfigBOP.biomeConfigBOPDeadForest = new BiomeConfigBOPDeadForest();
        BiomeConfigBOP.biomeConfigBOPDeadSwamp = new BiomeConfigBOPDeadSwamp();
        BiomeConfigBOP.biomeConfigBOPDeciduousForest = new BiomeConfigBOPDeciduousForest();
        BiomeConfigBOP.biomeConfigBOPDenseForest = new BiomeConfigBOPDenseForest();
        BiomeConfigBOP.biomeConfigBOPDryRiver = new BiomeConfigBOPDryRiver();
        BiomeConfigBOP.biomeConfigBOPEucalyptusForest = new BiomeConfigBOPEucalyptusForest();
        BiomeConfigBOP.biomeConfigBOPFen = new BiomeConfigBOPFen();
        BiomeConfigBOP.biomeConfigBOPFlowerField = new BiomeConfigBOPFlowerField();
        BiomeConfigBOP.biomeConfigBOPFrostForest = new BiomeConfigBOPFrostForest();
        BiomeConfigBOP.biomeConfigBOPFungiForest = new BiomeConfigBOPFungiForest();
        BiomeConfigBOP.biomeConfigBOPGarden = new BiomeConfigBOPGarden();
        BiomeConfigBOP.biomeConfigBOPGrassland = new BiomeConfigBOPGrassland();
        BiomeConfigBOP.biomeConfigBOPGrove = new BiomeConfigBOPGrove();
        BiomeConfigBOP.biomeConfigBOPHeathland = new BiomeConfigBOPHeathland();
        BiomeConfigBOP.biomeConfigBOPHighland = new BiomeConfigBOPHighland();
        BiomeConfigBOP.biomeConfigBOPJadeCliffs = new BiomeConfigBOPJadeCliffs();
        BiomeConfigBOP.biomeConfigBOPLandOfLakes = new BiomeConfigBOPLandOfLakes();
        BiomeConfigBOP.biomeConfigBOPLavenderFields = new BiomeConfigBOPLavenderFields();
        BiomeConfigBOP.biomeConfigBOPLushDesert = new BiomeConfigBOPLushDesert();
        BiomeConfigBOP.biomeConfigBOPLushRiver = new BiomeConfigBOPLushRiver();
        BiomeConfigBOP.biomeConfigBOPLushSwamp = new BiomeConfigBOPLushSwamp();
        BiomeConfigBOP.biomeConfigBOPMapleWoods = new BiomeConfigBOPMapleWoods();
        BiomeConfigBOP.biomeConfigBOPMarsh = new BiomeConfigBOPMarsh();
        BiomeConfigBOP.biomeConfigBOPMeadow = new BiomeConfigBOPMeadow();
        BiomeConfigBOP.biomeConfigBOPMoor = new BiomeConfigBOPMoor();
        BiomeConfigBOP.biomeConfigBOPMountain = new BiomeConfigBOPMountain();
        BiomeConfigBOP.biomeConfigBOPMysticGrove = new BiomeConfigBOPMysticGrove();
        BiomeConfigBOP.biomeConfigBOPOminousWoods = new BiomeConfigBOPOminousWoods();
        BiomeConfigBOP.biomeConfigBOPOriginValley = new BiomeConfigBOPOriginValley();
        BiomeConfigBOP.biomeConfigBOPOutback = new BiomeConfigBOPOutback();
        BiomeConfigBOP.biomeConfigBOPPrairie = new BiomeConfigBOPPrairie();
        BiomeConfigBOP.biomeConfigBOPRainforest = new BiomeConfigBOPRainforest();
        BiomeConfigBOP.biomeConfigBOPRedwoodForest = new BiomeConfigBOPRedwoodForest();
        BiomeConfigBOP.biomeConfigBOPSacredSprings = new BiomeConfigBOPSacredSprings();
        BiomeConfigBOP.biomeConfigBOPSeasonalForest = new BiomeConfigBOPSeasonalForest();
        BiomeConfigBOP.biomeConfigBOPShield = new BiomeConfigBOPShield();
        BiomeConfigBOP.biomeConfigBOPShrubland = new BiomeConfigBOPShrubland();
        BiomeConfigBOP.biomeConfigBOPSludgepit = new BiomeConfigBOPSludgepit();
        BiomeConfigBOP.biomeConfigBOPSnowyConiferousForest = new BiomeConfigBOPSnowyConiferousForest();
        BiomeConfigBOP.biomeConfigBOPSteppe = new BiomeConfigBOPSteppe();
        BiomeConfigBOP.biomeConfigBOPTemperateRainforest = new BiomeConfigBOPTemperateRainforest();
        BiomeConfigBOP.biomeConfigBOPThicket = new BiomeConfigBOPThicket();
        BiomeConfigBOP.biomeConfigBOPTropicalRainforest = new BiomeConfigBOPTropicalRainforest();
        BiomeConfigBOP.biomeConfigBOPTundra = new BiomeConfigBOPTundra();
        BiomeConfigBOP.biomeConfigBOPWasteland = new BiomeConfigBOPWasteland();
        BiomeConfigBOP.biomeConfigBOPWetland = new BiomeConfigBOPWetland();
        BiomeConfigBOP.biomeConfigBOPWoodland = new BiomeConfigBOPWoodland();
        BiomeConfigBOP.biomeConfigBOPXericShrubland = new BiomeConfigBOPXericShrubland();
        BiomeConfigBOP.biomeConfigBOPAlpsForest = new BiomeConfigBOPAlpsForest();
        BiomeConfigBOP.biomeConfigBOPCanyonRavine = new BiomeConfigBOPCanyonRavine();
        BiomeConfigBOP.biomeConfigBOPGlacier = new BiomeConfigBOPGlacier();
        BiomeConfigBOP.biomeConfigBOPLandOfLakesMarsh = new BiomeConfigBOPLandOfLakesMarsh();
        BiomeConfigBOP.biomeConfigBOPMangrove = new BiomeConfigBOPMangrove();
        BiomeConfigBOP.biomeConfigBOPMeadowForest = new BiomeConfigBOPMeadowForest();
        BiomeConfigBOP.biomeConfigBOPOasis = new BiomeConfigBOPOasis();
        BiomeConfigBOP.biomeConfigBOPOrchard = new BiomeConfigBOPOrchard();
        BiomeConfigBOP.biomeConfigBOPQuagmire = new BiomeConfigBOPQuagmire();
        BiomeConfigBOP.biomeConfigBOPScrubland = new BiomeConfigBOPScrubland();
        BiomeConfigBOP.biomeConfigBOPSeasonalForestClearing = new BiomeConfigBOPSeasonalForestClearing();
        BiomeConfigBOP.biomeConfigBOPSilkglades = new BiomeConfigBOPSilkglades();
        BiomeConfigBOP.biomeConfigBOPSpruceWoods = new BiomeConfigBOPSpruceWoods();
        BiomeConfigBOP.biomeConfigBOPTropics = new BiomeConfigBOPTropics();
        BiomeConfigBOP.biomeConfigBOPVolcano = new BiomeConfigBOPVolcano();
        BiomeConfigBOP.biomeConfigBOPCoralReef = new BiomeConfigBOPCoralReef();
        BiomeConfigBOP.biomeConfigBOPKelpForest = new BiomeConfigBOPKelpForest();
    }
    
    public static void initBiomeConfigsBC()
    {
        BiomeConfigBC.biomeConfigBCDesertOilField = new BiomeConfigBCDesertOilField();
        BiomeConfigBC.biomeConfigBCOceanOilField = new BiomeConfigBCOceanOilField();
    }
    
    public static void initBiomeConfigsCC()
    {
        BiomeConfigCC.biomeConfigCCEnderForest = new BiomeConfigCCEnderForest();
        BiomeConfigCC.biomeConfigCCRainbowForest = new BiomeConfigCCRainbowForest();
    }
    
    public static void initBiomeConfigsEB()
    {
        BiomeConfigEB.biomeConfigEBAlpineMountains = new BiomeConfigEBAlpineMountains();
        BiomeConfigEB.biomeConfigEBAlpineMountainsEdge = new BiomeConfigEBAlpineMountainsEdge();
        BiomeConfigEB.biomeConfigEBAlpineMountainsM = new BiomeConfigEBAlpineMountainsM();
        BiomeConfigEB.biomeConfigEBAlpineTundra = new BiomeConfigEBAlpineTundra();
        BiomeConfigEB.biomeConfigEBAspenForest = new BiomeConfigEBAspenForest();
        BiomeConfigEB.biomeConfigEBAspenHills = new BiomeConfigEBAspenHills();
        BiomeConfigEB.biomeConfigEBBadlands = new BiomeConfigEBBadlands();
        BiomeConfigEB.biomeConfigEBBasin = new BiomeConfigEBBasin();
        BiomeConfigEB.biomeConfigEBBlossomHills = new BiomeConfigEBBlossomHills();
        BiomeConfigEB.biomeConfigEBBlossomWoods = new BiomeConfigEBBlossomWoods();
        BiomeConfigEB.biomeConfigEBBorealArchipelago = new BiomeConfigEBBorealArchipelago();
        BiomeConfigEB.biomeConfigEBBorealForest = new BiomeConfigEBBorealForest();
        BiomeConfigEB.biomeConfigEBBorealPlateau = new BiomeConfigEBBorealPlateau();
        BiomeConfigEB.biomeConfigEBBorealPlateauM = new BiomeConfigEBBorealPlateauM();
        BiomeConfigEB.biomeConfigEBCarr = new BiomeConfigEBCarr();
        BiomeConfigEB.biomeConfigEBClayHills = new BiomeConfigEBClayHills();
        BiomeConfigEB.biomeConfigEBClearing = new BiomeConfigEBClearing();
        BiomeConfigEB.biomeConfigEBColdBorealForest = new BiomeConfigEBColdBorealForest();
        BiomeConfigEB.biomeConfigEBColdCypressForest = new BiomeConfigEBColdCypressForest();
        BiomeConfigEB.biomeConfigEBColdFirForest = new BiomeConfigEBColdFirForest();
        BiomeConfigEB.biomeConfigEBColdPineForest = new BiomeConfigEBColdPineForest();
        BiomeConfigEB.biomeConfigEBCreekBed = new BiomeConfigEBCreekBed();
        BiomeConfigEB.biomeConfigEBCypressForest = new BiomeConfigEBCypressForest();
        BiomeConfigEB.biomeConfigEBDesertArchipelago = new BiomeConfigEBDesertArchipelago();
        BiomeConfigEB.biomeConfigEBEphemeralLake = new BiomeConfigEBEphemeralLake();
        BiomeConfigEB.biomeConfigEBEphemeralLakeEdge = new BiomeConfigEBEphemeralLakeEdge();
        BiomeConfigEB.biomeConfigEBFens = new BiomeConfigEBFens();
        BiomeConfigEB.biomeConfigEBFirForest = new BiomeConfigEBFirForest();
        BiomeConfigEB.biomeConfigEBFloweryArchipelago = new BiomeConfigEBFloweryArchipelago();
        BiomeConfigEB.biomeConfigEBForestedArchipelago = new BiomeConfigEBForestedArchipelago();
        BiomeConfigEB.biomeConfigEBForestedMountains = new BiomeConfigEBForestedMountains();
        BiomeConfigEB.biomeConfigEBForestedValley = new BiomeConfigEBForestedValley();
        BiomeConfigEB.biomeConfigEBFrozenArchipelago = new BiomeConfigEBFrozenArchipelago();
        BiomeConfigEB.biomeConfigEBGlacier = new BiomeConfigEBGlacier();
        BiomeConfigEB.biomeConfigEBGrassyArchipelago = new BiomeConfigEBGrassyArchipelago();
        BiomeConfigEB.biomeConfigEBIceSheet = new BiomeConfigEBIceSheet();
        BiomeConfigEB.biomeConfigEBKakadu = new BiomeConfigEBKakadu();
        BiomeConfigEB.biomeConfigEBLake = new BiomeConfigEBLake();
        BiomeConfigEB.biomeConfigEBLowHills = new BiomeConfigEBLowHills();
        BiomeConfigEB.biomeConfigEBMangroves = new BiomeConfigEBMangroves();
        BiomeConfigEB.biomeConfigEBMarsh = new BiomeConfigEBMarsh();
        BiomeConfigEB.biomeConfigEBMeadow = new BiomeConfigEBMeadow();
        BiomeConfigEB.biomeConfigEBMeadowM = new BiomeConfigEBMeadowM();
        BiomeConfigEB.biomeConfigEBMountainousArchipelago = new BiomeConfigEBMountainousArchipelago();
        BiomeConfigEB.biomeConfigEBMountains = new BiomeConfigEBMountains();
        BiomeConfigEB.biomeConfigEBMountainsEdge = new BiomeConfigEBMountainsEdge();
        BiomeConfigEB.biomeConfigEBOakForest = new BiomeConfigEBOakForest();
        BiomeConfigEB.biomeConfigEBOasis = new BiomeConfigEBOasis();
        BiomeConfigEB.biomeConfigEBPineForest = new BiomeConfigEBPineForest();
        BiomeConfigEB.biomeConfigEBPineForestArchipelago = new BiomeConfigEBPineForestArchipelago();
        BiomeConfigEB.biomeConfigEBPlateau = new BiomeConfigEBPlateau();
        BiomeConfigEB.biomeConfigEBPolarDesert = new BiomeConfigEBPolarDesert();
        BiomeConfigEB.biomeConfigEBPrairie = new BiomeConfigEBPrairie();
        BiomeConfigEB.biomeConfigEBRainforest = new BiomeConfigEBRainforest();
        BiomeConfigEB.biomeConfigEBRainforestValley = new BiomeConfigEBRainforestValley();
        BiomeConfigEB.biomeConfigEBRedDesert = new BiomeConfigEBRedDesert();
        BiomeConfigEB.biomeConfigEBRiparianZone = new BiomeConfigEBRiparianZone();
        BiomeConfigEB.biomeConfigEBRockyDesert = new BiomeConfigEBRockyDesert();
        BiomeConfigEB.biomeConfigEBRockyHills = new BiomeConfigEBRockyHills();
        BiomeConfigEB.biomeConfigEBRoofedShrublands = new BiomeConfigEBRoofedShrublands();
        BiomeConfigEB.biomeConfigEBSahara = new BiomeConfigEBSahara();
        BiomeConfigEB.biomeConfigEBSandstoneCanyon = new BiomeConfigEBSandstoneCanyon();
        BiomeConfigEB.biomeConfigEBSandstoneCanyon2 = new BiomeConfigEBSandstoneCanyon2();
        BiomeConfigEB.biomeConfigEBSandstoneRanges = new BiomeConfigEBSandstoneRanges();
        BiomeConfigEB.biomeConfigEBSandstoneRangesM = new BiomeConfigEBSandstoneRangesM();
        BiomeConfigEB.biomeConfigEBScree = new BiomeConfigEBScree();
        BiomeConfigEB.biomeConfigEBScrub = new BiomeConfigEBScrub();
        BiomeConfigEB.biomeConfigEBShield = new BiomeConfigEBShield();
        BiomeConfigEB.biomeConfigEBShrublands = new BiomeConfigEBShrublands();
        BiomeConfigEB.biomeConfigEBSilverPineForest = new BiomeConfigEBSilverPineForest();
        BiomeConfigEB.biomeConfigEBSilverPineHills = new BiomeConfigEBSilverPineHills();
        BiomeConfigEB.biomeConfigEBSnowyDesert = new BiomeConfigEBSnowyDesert();
        BiomeConfigEB.biomeConfigEBSnowyPlateau = new BiomeConfigEBSnowyPlateau();
        BiomeConfigEB.biomeConfigEBSnowyRanges = new BiomeConfigEBSnowyRanges();
        BiomeConfigEB.biomeConfigEBSnowyWastelands = new BiomeConfigEBSnowyWastelands();
        BiomeConfigEB.biomeConfigEBSteppe = new BiomeConfigEBSteppe();
        BiomeConfigEB.biomeConfigEBStoneCanyon = new BiomeConfigEBStoneCanyon();
        BiomeConfigEB.biomeConfigEBStoneCanyon2 = new BiomeConfigEBStoneCanyon2();
        BiomeConfigEB.biomeConfigEBTropicalArchipelago = new BiomeConfigEBTropicalArchipelago();
        BiomeConfigEB.biomeConfigEBTundra = new BiomeConfigEBTundra();
        BiomeConfigEB.biomeConfigEBVolcano = new BiomeConfigEBVolcano();
        BiomeConfigEB.biomeConfigEBVolcanoM = new BiomeConfigEBVolcanoM();
        BiomeConfigEB.biomeConfigEBWastelands = new BiomeConfigEBWastelands();
        BiomeConfigEB.biomeConfigEBWoodlandField = new BiomeConfigEBWoodlandField();
        BiomeConfigEB.biomeConfigEBWoodlandHills = new BiomeConfigEBWoodlandHills();
        BiomeConfigEB.biomeConfigEBWoodlandLake = new BiomeConfigEBWoodlandLake();
        BiomeConfigEB.biomeConfigEBWoodlandLakeEdge = new BiomeConfigEBWoodlandLakeEdge();
        BiomeConfigEB.biomeConfigEBWoodlands = new BiomeConfigEBWoodlands();
        BiomeConfigEB.biomeConfigEBXericSavanna = new BiomeConfigEBXericSavanna();
        BiomeConfigEB.biomeConfigEBXericShrubland = new BiomeConfigEBXericShrubland();
    }
    
    public static void initBiomeConfigsEBXL()
    {
        BiomeConfigEBXL.biomeConfigEBXLAlpine = new BiomeConfigEBXLAlpine();
        BiomeConfigEBXL.biomeConfigEBXLAutumnWoods = new BiomeConfigEBXLAutumnWoods();
        BiomeConfigEBXL.biomeConfigEBXLBirchForest = new BiomeConfigEBXLBirchForest();
        BiomeConfigEBXL.biomeConfigEBXLExtremeJungle = new BiomeConfigEBXLExtremeJungle();
        BiomeConfigEBXL.biomeConfigEBXLForestedHills = new BiomeConfigEBXLForestedHills();
        BiomeConfigEBXL.biomeConfigEBXLForestedIsland = new BiomeConfigEBXLForestedIsland();
        BiomeConfigEBXL.biomeConfigEBXLGlacier = new BiomeConfigEBXLGlacier();
        BiomeConfigEBXL.biomeConfigEBXLGreenHills = new BiomeConfigEBXLGreenHills();
        BiomeConfigEBXL.biomeConfigEBXLGreenSwamp = new BiomeConfigEBXLGreenSwamp();
        BiomeConfigEBXL.biomeConfigEBXLIceWasteland = new BiomeConfigEBXLIceWasteland();
        BiomeConfigEBXL.biomeConfigEBXLMarsh = new BiomeConfigEBXLMarsh();
        BiomeConfigEBXL.biomeConfigEBXLMeadow = new BiomeConfigEBXLMeadow();
        BiomeConfigEBXL.biomeConfigEBXLMiniJungle = new BiomeConfigEBXLMiniJungle();
        BiomeConfigEBXL.biomeConfigEBXLMountainDesert = new BiomeConfigEBXLMountainDesert();
        BiomeConfigEBXL.biomeConfigEBXLMountainRidge = new BiomeConfigEBXLMountainRidge();
        BiomeConfigEBXL.biomeConfigEBXLMountainTaiga = new BiomeConfigEBXLMountainTaiga();
        BiomeConfigEBXL.biomeConfigEBXLPineForest = new BiomeConfigEBXLPineForest();
        BiomeConfigEBXL.biomeConfigEBXLRainforest = new BiomeConfigEBXLRainforest();
        BiomeConfigEBXL.biomeConfigEBXLRedwoodForest = new BiomeConfigEBXLRedwoodForest();
        BiomeConfigEBXL.biomeConfigEBXLRedwoodLush = new BiomeConfigEBXLRedwoodLush();
        BiomeConfigEBXL.biomeConfigEBXLSavanna = new BiomeConfigEBXLSavanna();
        BiomeConfigEBXL.biomeConfigEBXLShrubland = new BiomeConfigEBXLShrubland();
        BiomeConfigEBXL.biomeConfigEBXLSnowForest = new BiomeConfigEBXLSnowForest();
        BiomeConfigEBXL.biomeConfigEBXLSnowyRainforest = new BiomeConfigEBXLSnowyRainforest();
        BiomeConfigEBXL.biomeConfigEBXLTemperateRainforest = new BiomeConfigEBXLTemperateRainforest();
        BiomeConfigEBXL.biomeConfigEBXLTundra = new BiomeConfigEBXLTundra();
        BiomeConfigEBXL.biomeConfigEBXLWasteland = new BiomeConfigEBXLWasteland();
        BiomeConfigEBXL.biomeConfigEBXLWoodlands = new BiomeConfigEBXLWoodlands();
    }
    
    public static void initBiomeConfigsGC()
    {
        BiomeConfigGC.biomeConfigGCBambooForest = new BiomeConfigGCBambooForest();
    }
    
    public static void initBiomeConfigsHL()
    {
        BiomeConfigHL.biomeConfigHLAlps = new BiomeConfigHLAlps();
        BiomeConfigHL.biomeConfigHLAutumnForest = new BiomeConfigHLAutumnForest();
        BiomeConfigHL.biomeConfigHLBadlands = new BiomeConfigHLBadlands();
        BiomeConfigHL.biomeConfigHLBaldHill = new BiomeConfigHLBaldHill();
        BiomeConfigHL.biomeConfigHLBirchHills = new BiomeConfigHLBirchHills();
        BiomeConfigHL.biomeConfigHLBog = new BiomeConfigHLBog();
        BiomeConfigHL.biomeConfigHLCanyon = new BiomeConfigHLCanyon();
        BiomeConfigHL.biomeConfigHLCliffs = new BiomeConfigHLCliffs();
        BiomeConfigHL.biomeConfigHLDesertIsland = new BiomeConfigHLDesertIsland();
        BiomeConfigHL.biomeConfigHLDesertMountains = new BiomeConfigHLDesertMountains();
        BiomeConfigHL.biomeConfigHLDunes = new BiomeConfigHLDunes();
        BiomeConfigHL.biomeConfigHLEstuary = new BiomeConfigHLEstuary();
        BiomeConfigHL.biomeConfigHLFlyingMountains = new BiomeConfigHLFlyingMountains();
        BiomeConfigHL.biomeConfigHLForestIsland = new BiomeConfigHLForestIsland();
        BiomeConfigHL.biomeConfigHLGlacier = new BiomeConfigHLGlacier();
        BiomeConfigHL.biomeConfigHLHighlandsB = new BiomeConfigHLHighlandsB();
        BiomeConfigHL.biomeConfigHLJungleIsland = new BiomeConfigHLJungleIsland();
        BiomeConfigHL.biomeConfigHLLake = new BiomeConfigHLLake();
        BiomeConfigHL.biomeConfigHLLowlands = new BiomeConfigHLLowlands();
        BiomeConfigHL.biomeConfigHLMeadow = new BiomeConfigHLMeadow();
        BiomeConfigHL.biomeConfigHLMesa = new BiomeConfigHLMesa();
        BiomeConfigHL.biomeConfigHLOasis = new BiomeConfigHLOasis();
        BiomeConfigHL.biomeConfigHLOutback = new BiomeConfigHLOutback();
        BiomeConfigHL.biomeConfigHLPinelands = new BiomeConfigHLPinelands();
        BiomeConfigHL.biomeConfigHLRainforest = new BiomeConfigHLRainforest();
        BiomeConfigHL.biomeConfigHLRedwoodForest = new BiomeConfigHLRedwoodForest();
        BiomeConfigHL.biomeConfigHLRockIsland = new BiomeConfigHLRockIsland();
        BiomeConfigHL.biomeConfigHLRockMountains = new BiomeConfigHLRockMountains();
        BiomeConfigHL.biomeConfigHLSahel = new BiomeConfigHLSahel();
        BiomeConfigHL.biomeConfigHLSavannah = new BiomeConfigHLSavannah();
        BiomeConfigHL.biomeConfigHLShrubland = new BiomeConfigHLShrubland();
        BiomeConfigHL.biomeConfigHLSnowIsland = new BiomeConfigHLSnowIsland();
        BiomeConfigHL.biomeConfigHLSnowMountains = new BiomeConfigHLSnowMountains();
        BiomeConfigHL.biomeConfigHLSteppe = new BiomeConfigHLSteppe();
        BiomeConfigHL.biomeConfigHLTallPineForest = new BiomeConfigHLTallPineForest();
        BiomeConfigHL.biomeConfigHLTropicalIslands = new BiomeConfigHLTropicalIslands();
        BiomeConfigHL.biomeConfigHLTropics = new BiomeConfigHLTropics();
        BiomeConfigHL.biomeConfigHLTundra = new BiomeConfigHLTundra();
        BiomeConfigHL.biomeConfigHLValley = new BiomeConfigHLValley();
        BiomeConfigHL.biomeConfigHLVolcanoIsland = new BiomeConfigHLVolcanoIsland();
        BiomeConfigHL.biomeConfigHLWindyIsland = new BiomeConfigHLWindyIsland();
        BiomeConfigHL.biomeConfigHLWoodlands = new BiomeConfigHLWoodlands();
        BiomeConfigHL.biomeConfigHLWoodsMountains = new BiomeConfigHLWoodsMountains();
    }

    public static void initBiomeConfigsIC()
    {
        BiomeConfigIC.biomeConfigICIceCream = new BiomeConfigICIceCream();
    }
    
    public static void initBiomeConfigsIDT()
    {
    	BiomeConfigIDT.biomeConfigIDTEbonyForest = new BiomeConfigIDTEbonyForest();
    	BiomeConfigIDT.biomeConfigIDTSilkwoodForest = new BiomeConfigIDTSilkwoodForest();
    	BiomeConfigIDT.biomeConfigIDTWillowForest = new BiomeConfigIDTWillowForest();
    }

    public static void initBiomeConfigsRW()
    {  
        BiomeConfigRW.biomeConfigRWBotanicalGarden = new BiomeConfigRWBotanicalGarden();
        BiomeConfigRW.biomeConfigRWMurica = new BiomeConfigRWMurica();
        BiomeConfigRW.biomeConfigRWMountainOfMadness = new BiomeConfigRWMountainOfMadness();
        BiomeConfigRW.biomeConfigRWOssuary = new BiomeConfigRWOssuary();
        BiomeConfigRW.biomeConfigRWRockCandyMountain = new BiomeConfigRWRockCandyMountain();
        BiomeConfigRW.biomeConfigRWShadowFen = new BiomeConfigRWShadowFen();
        BiomeConfigRW.biomeConfigRWSpookyForest = new BiomeConfigRWSpookyForest();
    }

    public static void initBiomeConfigsTC()
    {
        BiomeConfigTC.biomeConfigTCEerie = new BiomeConfigTCEerie();
        BiomeConfigTC.biomeConfigTCMagicalForest = new BiomeConfigTCMagicalForest();
        BiomeConfigTC.biomeConfigTCTaintedLand = new BiomeConfigTCTaintedLand();
    }
    
    public static void initBiomeConfigsLOM()
    {
        BiomeConfigLOM.biomeConfigLOMAntartica = new BiomeConfigLOMAntartica();
        BiomeConfigLOM.biomeConfigLOMTropicalBeach = new BiomeConfigLOMTropicalBeach();
    }

    public static void initBiomeConfigsVAMP()
    {
        BiomeConfigVAMP.biomeConfigVAMPVampireForest = new BiomeConfigVAMPVampireForest();
    }
    
    public static void initBiomeConfigsTOFU()
    {

        BiomeConfigTOFU.biomeConfigTOFULeekPlains = new BiomeConfigTOFULeekPlains();
        BiomeConfigTOFU.biomeConfigTOFUTofuBuildings = new BiomeConfigTOFUTofuBuildings();
        BiomeConfigTOFU.biomeConfigTOFUTofuExtremeHills = new BiomeConfigTOFUTofuExtremeHills();
        BiomeConfigTOFU.biomeConfigTOFUTofuExtremeHillsEdge = new BiomeConfigTOFUTofuExtremeHillsEdge();
        BiomeConfigTOFU.biomeConfigTOFUTofuForest = new BiomeConfigTOFUTofuForest();
        BiomeConfigTOFU.biomeConfigTOFUTofuForestHills = new BiomeConfigTOFUTofuForestHills();
        BiomeConfigTOFU.biomeConfigTOFUTofuPlainHills = new BiomeConfigTOFUTofuPlainHills();
        BiomeConfigTOFU.biomeConfigTOFUTofuPlains = new BiomeConfigTOFUTofuPlains();
        BiomeConfigTOFU.biomeConfigTOFUTofuRiver = new BiomeConfigTOFUTofuRiver();
    }
    
    public static void initBiomeConfigsFN()
    {

        BiomeConfigFN.biomeConfigFNCherryBlossomWoodland = new BiomeConfigFNCherryBlossomWoodland();
        BiomeConfigFN.biomeConfigFNCrystalForest = new BiomeConfigFNCrystalForest();
        BiomeConfigFN.biomeConfigFNEucalyptusForest = new BiomeConfigFNEucalyptusForest();
        BiomeConfigFN.biomeConfigFNGreatwoodForest = new BiomeConfigFNGreatwoodForest();
        BiomeConfigFN.biomeConfigFNMapleForest = new BiomeConfigFNMapleForest();
        BiomeConfigFN.biomeConfigFNRedwoodForest = new BiomeConfigFNRedwoodForest();
        BiomeConfigFN.biomeConfigFNRedwoodForestHills = new BiomeConfigFNRedwoodForestHills();
        BiomeConfigFN.biomeConfigFNTropicalForest = new BiomeConfigFNTropicalForest();
        BiomeConfigFN.biomeConfigFNTropicalForestHills = new BiomeConfigFNTropicalForestHills();
    }
    
    public static void initBiomeConfigsVanilla()
    {
        BiomeConfigVanilla.biomeConfigVanillaBeach = new BiomeConfigVanillaBeach();
        BiomeConfigVanilla.biomeConfigVanillaBirchForest = new BiomeConfigVanillaBirchForest();
        BiomeConfigVanilla.biomeConfigVanillaBirchForestHills = new BiomeConfigVanillaBirchForestHills();
        BiomeConfigVanilla.biomeConfigVanillaColdBeach = new BiomeConfigVanillaColdBeach();
        BiomeConfigVanilla.biomeConfigVanillaColdTaiga = new BiomeConfigVanillaColdTaiga();
        BiomeConfigVanilla.biomeConfigVanillaColdTaigaHills = new BiomeConfigVanillaColdTaigaHills();
        BiomeConfigVanilla.biomeConfigVanillaDeepOcean = new BiomeConfigVanillaDeepOcean();
        BiomeConfigVanilla.biomeConfigVanillaDesert = new BiomeConfigVanillaDesert();
        BiomeConfigVanilla.biomeConfigVanillaDesertHills = new BiomeConfigVanillaDesertHills();
        BiomeConfigVanilla.biomeConfigVanillaExtremeHills = new BiomeConfigVanillaExtremeHills();
        BiomeConfigVanilla.biomeConfigVanillaExtremeHillsEdge = new BiomeConfigVanillaExtremeHillsEdge();
        BiomeConfigVanilla.biomeConfigVanillaExtremeHillsPlus = new BiomeConfigVanillaExtremeHillsPlus();
        BiomeConfigVanilla.biomeConfigVanillaForest = new BiomeConfigVanillaForest();
        BiomeConfigVanilla.biomeConfigVanillaForestHills = new BiomeConfigVanillaForestHills();
        BiomeConfigVanilla.biomeConfigVanillaFrozenOcean = new BiomeConfigVanillaFrozenOcean();
        BiomeConfigVanilla.biomeConfigVanillaFrozenRiver = new BiomeConfigVanillaFrozenRiver();
        BiomeConfigVanilla.biomeConfigVanillaIcePlains = new BiomeConfigVanillaIcePlains();
        BiomeConfigVanilla.biomeConfigVanillaIceMountains = new BiomeConfigVanillaIceMountains();
        BiomeConfigVanilla.biomeConfigVanillaJungle = new BiomeConfigVanillaJungle();
        BiomeConfigVanilla.biomeConfigVanillaJungleEdge = new BiomeConfigVanillaJungleEdge();
        BiomeConfigVanilla.biomeConfigVanillaJungleHills = new BiomeConfigVanillaJungleHills();
        BiomeConfigVanilla.biomeConfigVanillaMegaTaiga = new BiomeConfigVanillaMegaTaiga();
        BiomeConfigVanilla.biomeConfigVanillaMegaTaigaHills = new BiomeConfigVanillaMegaTaigaHills();
        BiomeConfigVanilla.biomeConfigVanillaMesa = new BiomeConfigVanillaMesa();
        BiomeConfigVanilla.biomeConfigVanillaMesaPlateau = new BiomeConfigVanillaMesaPlateau();
        BiomeConfigVanilla.biomeConfigVanillaMesaPlateauF = new BiomeConfigVanillaMesaPlateauF();
        BiomeConfigVanilla.biomeConfigVanillaMushroomIsland = new BiomeConfigVanillaMushroomIsland();
        BiomeConfigVanilla.biomeConfigVanillaMushroomIslandShore = new BiomeConfigVanillaMushroomIslandShore();
        BiomeConfigVanilla.biomeConfigVanillaOcean = new BiomeConfigVanillaOcean();
        BiomeConfigVanilla.biomeConfigVanillaPlains = new BiomeConfigVanillaPlains();
        BiomeConfigVanilla.biomeConfigVanillaRiver = new BiomeConfigVanillaRiver();
        BiomeConfigVanilla.biomeConfigVanillaRoofedForest = new BiomeConfigVanillaRoofedForest();
        BiomeConfigVanilla.biomeConfigVanillaSavanna = new BiomeConfigVanillaSavanna();
        BiomeConfigVanilla.biomeConfigVanillaSavannaPlateau = new BiomeConfigVanillaSavannaPlateau();
        BiomeConfigVanilla.biomeConfigVanillaStoneBeach = new BiomeConfigVanillaStoneBeach();
        BiomeConfigVanilla.biomeConfigVanillaSwampland = new BiomeConfigVanillaSwampland();
        BiomeConfigVanilla.biomeConfigVanillaTaiga = new BiomeConfigVanillaTaiga();
        BiomeConfigVanilla.biomeConfigVanillaTaigaHills = new BiomeConfigVanillaTaigaHills();
        BiomeConfigVanilla.biomeConfigVanillaSunflowerPlains = new BiomeConfigVanillaSunflowerPlains();
        BiomeConfigVanilla.biomeConfigVanillaDesertM = new BiomeConfigVanillaDesertM();
        BiomeConfigVanilla.biomeConfigVanillaExtremeHillsM = new BiomeConfigVanillaExtremeHillsM();
        BiomeConfigVanilla.biomeConfigVanillaFlowerForest = new BiomeConfigVanillaFlowerForest();
        BiomeConfigVanilla.biomeConfigVanillaTaigaM = new BiomeConfigVanillaTaigaM();
        BiomeConfigVanilla.biomeConfigVanillaSwamplandM = new BiomeConfigVanillaSwamplandM();
        BiomeConfigVanilla.biomeConfigVanillaIcePlainsSpikes = new BiomeConfigVanillaIcePlainsSpikes();
        BiomeConfigVanilla.biomeConfigVanillaJungleM = new BiomeConfigVanillaJungleM();
        BiomeConfigVanilla.biomeConfigVanillaJungleEdgeM = new BiomeConfigVanillaJungleEdgeM();
        BiomeConfigVanilla.biomeConfigVanillaBirchForestM = new BiomeConfigVanillaBirchForestM();
        BiomeConfigVanilla.biomeConfigVanillaBirchForestHillsM = new BiomeConfigVanillaBirchForestHillsM();
        BiomeConfigVanilla.biomeConfigVanillaRoofedForestM = new BiomeConfigVanillaRoofedForestM();
        BiomeConfigVanilla.biomeConfigVanillaColdTaigaM = new BiomeConfigVanillaColdTaigaM();
        BiomeConfigVanilla.biomeConfigVanillaMegaSpruceTaiga = new BiomeConfigVanillaMegaSpruceTaiga();
        BiomeConfigVanilla.biomeConfigVanillaRedwoodTaigaHills = new BiomeConfigVanillaRedwoodTaigaHills();
        BiomeConfigVanilla.biomeConfigVanillaExtremeHillsPlusM = new BiomeConfigVanillaExtremeHillsPlusM();
        BiomeConfigVanilla.biomeConfigVanillaSavannaM = new BiomeConfigVanillaSavannaM();
        BiomeConfigVanilla.biomeConfigVanillaSavannaPlateauM = new BiomeConfigVanillaSavannaPlateauM();
        BiomeConfigVanilla.biomeConfigVanillaMesaBryce = new BiomeConfigVanillaMesaBryce();
        BiomeConfigVanilla.biomeConfigVanillaMesaPlateauFM = new BiomeConfigVanillaMesaPlateauFM();
        BiomeConfigVanilla.biomeConfigVanillaMesaPlateauM = new BiomeConfigVanillaMesaPlateauM();
    }
    
    public static void setBiomeConfigsFromUserConfigs(BiomeConfig[] biomeConfigs, Configuration config)
    {
        
        for (int i = 0; i < biomeConfigs.length; i++) {
            
            String categoryName = "biome." + biomeConfigs[i].modSlug + "." + biomeConfigs[i].biomeSlug;
            ArrayList<BiomeConfigProperty> properties = biomeConfigs[i].getProperties();
            
            for (int j = 0; j < properties.size(); j++) {
                
                BiomeConfigProperty prop = properties.get(j);
                                
                switch (prop.type) {
                    
                    case INTEGER:
                        
                        prop.valueInt = config.getInt(
                            prop.name,
                            categoryName,
                            prop.valueInt,
                            prop.minValue,
                            prop.maxValue,
                            prop.description
                        );
                        
                        break;
                        
                    case BOOLEAN:
                        
                        prop.valueBoolean = config.getBoolean(
                            prop.name,
                            categoryName,
                            prop.valueBoolean,
                            prop.description
                        );
                        
                        break;
                        
                    case STRING:
                        
                        prop.valueString = config.getString(
                            prop.name,
                            categoryName,
                            prop.valueString,
                            prop.description
                        );
                        
                        break;
                        
                    default:
                        throw new RuntimeException("BiomeConfigProperty type not supported.");
                }
            }
        }
    }
}

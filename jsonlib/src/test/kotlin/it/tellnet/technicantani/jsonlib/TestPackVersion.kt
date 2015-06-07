package it.tellnet.technicantani.jsonlib
import com.github.salomonbrys.kotson.typeToken
import it.admiral0.technicantani.data.MinecraftVersion
import it.admiral0.technicantani.data.ModPackVersion
import it.admiral0.technicantani.gson
import org.junit.Test
import org.junit.Assert as junit

/**
 * Created by admiral0 on 07/06/15.
 */
public class TestPackVersion {
    @Test
    public fun testBasicPack(){
        val pack : ModPackVersion = gson.builder.create().fromJson("""
            {
                "description": "Back To Antani Modpack",
                "forgever": "10.13.3.1428",
                "mcversion": "1.7.10",
                "mods": {
                    "AE2": "rv2-stable-5",
                    "AOBD": "2.6.3",
                    "BetterDungeons": "1.0",
                    "BiblioCraft": "1.10.4",
                    "BiblioWoodsForestryEdition": "1.7",
                    "BiblioWoodsNaturaEdition": "1.5",
                    "BigReactors": "0.4.3A",
                    "BinniesMods": "2.0-pre9",
                    "BloodMagic": "v1.3.3-4",
                    "Botania": "r1.6-191",
                    "Buildcraft": "7.0.7",
                    "CoFHCore": "3.0.2",
                    "CodeChickenCore": "1.0.6.43",
                    "ComputerCraft": "1.73",
                    "CustomMobSpawner": "3.3.0",
                    "DeathCounter": "4.0.0",
                    "DimensionalPockets": "0.10.4",
                    "DraconicEvolution": "v1.0.1c",
                    "EnchantingPlus": "1.7.10-3.0.1",
                    "EnderStorage": "1.4.7.33",
                    "ExtraUtilities": "1.2.5",
                    "ForbiddenMagic": "1.7.10-0.562",
                    "Forestry": "3.5.7.16",
                    "GuideAPI": "1.7.10-1.0.1-20",
                    "Hats": "4.0.1",
                    "IC2": "2.2.732-experimental",
                    "IntegratedCircuits": "1.7.10-0.8r34b",
                    "InventoryTweaks": "1.59-dev-152-cf6e263",
                    "IronChests": "6.0.62.742",
                    "Jabba": "1.2.1a",
                    "JourneyMap": "5.1.0rc3",
                    "Mantle": "0.3.2.jenkins184",
                    "MoCreatures": "6.3.1",
                    "MrTJPCore": "1.0.8.16",
                    "NEI": "1.0.4.95",
                    "NEIAddons": "1.12.8.31",
                    "NEIIntegration": "1.0.9",
                    "NEIThaumcraft": "1.7.10-1.7",
                    "Natura": "2.2.0.1",
                    "OmnisCore": "0.0.5",
                    "OpenBlocks": "1.4.3",
                    "OpenComputers": "1.5.12.26",
                    "OpenModsLib": "0.7.3",
                    "OpenPeripheralAddons": "0.3.1",
                    "OpenPeripheralCore": "1.1.1",
                    "OpenPeripheralIntegration": "0.2.2",
                    "PortalGun": "4.0.0-beta-4",
                    "ProjectRedBase": "4.6.2.82",
                    "ProjectRedCompat": "4.6.2.82",
                    "ProjectRedIntegration": "4.6.2.82",
                    "ProjectRedLighting": "4.6.2.82",
                    "ProjectRedWorld": "4.6.2.82",
                    "RFTools": "2.93",
                    "Railcraft": "9.6.1.0",
                    "Ruins": "1.7.10",
                    "SimplyJetpacks": "1.5.1",
                    "SolarExpansion": "1.6a",
                    "TMechworks": "0.2.14.100",
                    "Thaumcraft": "4.2.3.5",
                    "ThaumicEnergistics": "0.8.10.5",
                    "TheTwilightForest": "2.3.7",
                    "ThermalExpansion": "4.0.1",
                    "ThermalFoundation": "1.0.0",
                    "TinkersConstruct": "1.8.5",
                    "Waila": "1.5.10_1.7.10",
                    "Wawla": "1.2.1",
                    "Witchery": "1.7.10-0.24.1",
                    "iChunUtil": "4.2.2"
                },
                "url": "http://mosconidebugging.biz/antani",
                "version": "0.3.0"
            }
        """, typeToken<ModPackVersion>())
        junit.assertEquals(pack.description, "Back To Antani Modpack")
        junit.assertEquals(pack.forgever, "10.13.3.1428")
        junit.assertEquals(pack.mcversion, MinecraftVersion.v1_7_10)
        junit.assertEquals(pack.version, "0.3.0")
        junit.assertTrue(pack.mods.containsKey("Wawla"))
        junit.assertEquals(pack.mods.get("Wawla"), "1.2.1")
    }
}
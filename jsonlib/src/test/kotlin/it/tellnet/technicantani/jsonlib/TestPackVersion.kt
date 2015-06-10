package it.tellnet.technicantani.jsonlib
import com.github.salomonbrys.kotson.typeToken
import it.admiral0.technicantani.data.*
import it.tellnet.technicantani.jsonlib.util.createTempRepo
import it.tellnet.technicantani.jsonlib.util.invalidModpackMissingMod
import it.tellnet.technicantani.jsonlib.util.invalidModpackMissingVersion
import it.tellnet.technicantani.jsonlib.util.validModInfo
import java.util.*
import org.junit.Test as test
import org.junit.Assert as junit

class TestPackVersion {
    test fun testBasicPack(){
        val pack : ModPackVersion = gson.builder.create().fromJson(invalidModpackMissingMod, typeToken<ModPackVersion>())
        junit.assertEquals(pack.description, "Back To Antani Modpack")
        junit.assertEquals(pack.forgever.get(), "10.13.3.1428")
        junit.assertEquals(pack.mcversion, MinecraftVersion.v1_7_10)
        junit.assertEquals(pack.version.get(), "0.3.0")
        junit.assertTrue(pack.mods.containsKey("missingmod"))
        junit.assertEquals(pack.mods.get("missingmod"), "NOPE")
    }

    test fun testPackValidationMissingMod(){
        val pack : ModPackVersion = gson.builder.create().fromJson(invalidModpackMissingMod, typeToken<ModPackVersion>())
        val repoDir = createTempRepo()
        val repo = ModRepo.build(repoDir.getAbsolutePath())
        pack.name = Optional.of("BackToAntani")
        val errs = pack.validate(repo)
        junit.assertTrue(errs.size()==1)
    }

    test fun testPackValidationMissingVersion(){
        val pack : ModPackVersion = gson.builder.create().fromJson(invalidModpackMissingVersion, typeToken<ModPackVersion>())
        val repoDir = createTempRepo(Pair("testmod",validModInfo))
        val repo = ModRepo.build(repoDir.getAbsolutePath())
        pack.name = Optional.of("BackToAntani")
        val errs = pack.validate(repo)
        junit.assertTrue(errs.size()==1)
    }
}
package it.tellnet.technicantani.jsonlib

import com.github.salomonbrys.kotson.typeToken
import com.google.gson.JsonSyntaxException
import it.admiral0.technicantani.data.MinecraftVersion
import it.admiral0.technicantani.data.Mod
import it.admiral0.technicantani.data.ModType
import it.admiral0.technicantani.data.PackagingType
import it.admiral0.technicantani.data.gson
import it.tellnet.technicantani.jsonlib.util.validModInfo
import org.junit.Test as test
import org.junit.Assert as junit
import kotlin.test.assertEquals


class TestMod{
    test fun verifyModSerialize() {
        val mod : Mod = gson.builder.create().fromJson(validModInfo, typeToken<Mod>())
        junit.assertEquals(mod.type, PackagingType.PREPACKAGED)
        junit.assertEquals(mod.url, "asdomare")
        junit.assertEquals(mod.description, "Some description")
        junit.assertArrayEquals(mod.authors, arrayOf("author1", "author2"))
        junit.assertEquals(1, mod.versions.size())
        val version = mod.versions.values().first()
        junit.assertEquals(version.file, "test")
        junit.assertArrayEquals(version.minecraft, arrayOf(MinecraftVersion.v1_7_10))
        junit.assertEquals(version.type, ModType.CLIENT)
    }


    test fun verifyModSerializeDefaults() {
        val valid = """
        {

        }
        """
        var mod : Mod = gson.builder.create().fromJson(valid, typeToken<Mod>())
        junit.assertEquals(mod.type, PackagingType.MOD)
        junit.assertEquals(mod.url, "")
        junit.assertEquals(mod.description, "No description.")
        junit.assertArrayEquals(mod.authors, emptyArray())
        junit.assertEquals(0, mod.versions.size())
        val valid2 = """
        {
            "versions" : {
                "1.0" : {
                }
            }
        }
        """
        mod : Mod = gson.builder.create().fromJson(valid2, typeToken<Mod>())
        junit.assertEquals(mod.type, PackagingType.MOD)
        junit.assertEquals(mod.url, "")
        junit.assertEquals(mod.description, "No description.")
        junit.assertArrayEquals(mod.authors, emptyArray())
        junit.assertEquals(1, mod.versions.size())
        val version = mod.versions.values().first()
        junit.assertEquals("", version.file)
        junit.assertArrayEquals(arrayOf(MinecraftVersion.v1_8),version.minecraft)
        junit.assertEquals(version.type, ModType.UNIVERSAL)
    }

    test fun setModName() {
        val mod = Mod()
        mod.name = "Antani"
        junit.assertEquals(mod.name, "Antani")
    }

    test fun verifyFailSerialize(){
        val syntaxError = """
        {
        """
        try {
            val mod : Mod = gson.builder.create().fromJson(syntaxError, typeToken<Mod>())
            junit.assertNull(mod) // Compiler, Shut up!
        } catch(e : JsonSyntaxException){
            return;
        }
        junit.fail("JSONSyntaxException not thrown!")
    }
}
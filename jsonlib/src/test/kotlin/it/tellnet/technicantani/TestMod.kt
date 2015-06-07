package it.tellnet.technicantani

import com.github.salomonbrys.kotson.typeToken
import it.admiral0.technicantani.data.MinecraftVersion
import it.admiral0.technicantani.data.Mod
import it.admiral0.technicantani.data.ModType
import it.admiral0.technicantani.data.PackagingType
import it.admiral0.technicantani.gson
import org.junit.Test
import org.junit.Assert as junit
import kotlin.test.assertEquals


public class TestMod{
    @Test
    public fun verifyModSerialize() {
        val valid = """
        {
            "authors" : ["author1", "author2"],
            "description" : "Some description",
            "url" : "asdomare",
            "type" : "prepackaged",
            "versions": {
                "1.0" : {
                    "file" : "test",
                    "minecraft": ["1.7.10"],
                    "type": "client"
                }
            }
        }
        """
        val mod : Mod = gson.builder.create().fromJson(valid, typeToken<Mod>())
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
}
package it.tellnet.technicantani.jsonlib

import it.admiral0.technicantani.data.Mod
import it.admiral0.technicantani.data.ModRepo
import it.admiral0.technicantani.data.validate
import it.tellnet.technicantani.jsonlib.util.createTempRepo
import it.tellnet.technicantani.jsonlib.util.emptyMod
import org.junit.Test as test
import org.junit.Assert as junit
import java.io.File
import kotlin.io.createTempDir
import kotlin.test.assertEquals

class TestModRepo {
    test fun testModRepo(){
        val dir = createTempRepo(Pair("testmod",emptyMod))
        var repo = ModRepo.build(dir.getAbsolutePath())
        assertEquals(repo.authors.size(),2)
        junit.assertArrayEquals(repo.authors, arrayOf("antani1", "antani2"))
        assertEquals(repo.mods.size(),1)
        val m : Mod = repo.mods.values().first()
        assertEquals(m.name, "testmod")
        val errs = m.validate(File(dir.getAbsolutePath() + File.separator + "testmod"))
        junit.assertTrue(errs.contains("Zero authors."))
        junit.assertTrue(errs.contains("No declared versions"))
    }

}

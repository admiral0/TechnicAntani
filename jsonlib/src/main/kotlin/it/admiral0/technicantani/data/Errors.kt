package it.admiral0.technicantani.data

import org.eclipse.jgit.api.Git
import java.lang.reflect.Type

public class WrongType(
        val type: String,
        val expected: String,
        val context: String
) : Exception("In element \"$context\", was expecting a \"$expected\", instead got a ${type}")

public class NotAValidOption(
        val value: String,
        val options: Array<String>
) : Exception("\"$value\" is not valid. Valid options: ${ options.join(",","\"","\"") }")


public class MissingBranch(val branch : String, val repo : Git) : Exception(
        "There is no branch \"${branch}\" in the repo ${repo.getRepository().toString()}"
)

public class MissingTagForVersion(val version : String, val branch : String) : Exception(
        "Version $version is not tagged but in branch $branch"
)
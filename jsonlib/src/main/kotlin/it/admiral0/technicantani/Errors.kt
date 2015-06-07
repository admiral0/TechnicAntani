package it.admiral0.technicantani

import java.lang.reflect.Type

public class MissingProperty(
        val name: String,
        val where: String
) : Exception("$name property not found in \"$where\"")

public class WrongType(
        val type: String,
        val expected: String,
        val context: String
) : Exception("In element \"$context\", was expecting a \"$expected\", instead got a ${type}")

public class NotAValidOption(
        val value: String,
        val options: Array<String>
) : Exception("\"$value\" is not valid. Valid options: ${ options.join(",","\"","\"") }")
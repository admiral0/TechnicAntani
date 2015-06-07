package it.admiral0.technicantani.validation

public class NoAuthorsException : Exception("You need to define at least one author for the Mod")

public class NoVersionsException : Exception("You need to define at least one version for the Mod")
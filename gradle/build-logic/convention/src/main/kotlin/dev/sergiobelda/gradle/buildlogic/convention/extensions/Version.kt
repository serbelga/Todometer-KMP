package dev.sergiobelda.gradle.buildlogic.convention.extensions

import org.gradle.api.artifacts.VersionConstraint
import java.util.Optional

val Optional<VersionConstraint>.version
    get() = get().toString()

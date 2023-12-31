// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val kotlinVersion = "1.9.0"
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
}
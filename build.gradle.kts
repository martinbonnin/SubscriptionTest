// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.apollographql.apollo3").version("4.0.0-beta.5-SNAPSHOT").apply(false)
    id("co.touchlab.skie").version("0.6.1").apply(false)
}
package dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview

import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Preview(name = "English - Small Round", group = "Language", locale = "en", device = WearDevices.SMALL_ROUND)
@Preview(name = "Spanish - Small Round", group = "Language", locale = "es", device = WearDevices.SMALL_ROUND)
@Preview(name = "Catalan - Small Round", group = "Language", locale = "ca", device = WearDevices.SMALL_ROUND)
@Preview(name = "English - Large Round", group = "Language", locale = "en", device = WearDevices.LARGE_ROUND)
@Preview(name = "Spanish - Large Round", group = "Language", locale = "es", device = WearDevices.LARGE_ROUND)
@Preview(name = "Catalan - Large Round", group = "Language", locale = "ca", device = WearDevices.LARGE_ROUND)
annotation class PreviewWearLocales

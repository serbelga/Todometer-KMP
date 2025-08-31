package dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview

import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Preview(name = "Small Round", device = WearDevices.SMALL_ROUND)
@Preview(name = "Large Round", device = WearDevices.LARGE_ROUND)
@Preview(name = "Rect", device = WearDevices.RECT)
@Preview(name = "Square", device = WearDevices.SQUARE)
annotation class PreviewWearDevices

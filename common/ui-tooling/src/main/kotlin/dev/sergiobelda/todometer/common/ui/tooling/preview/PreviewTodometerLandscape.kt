package dev.sergiobelda.todometer.common.ui.tooling.preview

import androidx.compose.ui.tooling.preview.Preview

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Preview(name = "Landscape", device = "spec:parent=pixel_5,orientation=landscape")
annotation class PreviewTodometerLandscape

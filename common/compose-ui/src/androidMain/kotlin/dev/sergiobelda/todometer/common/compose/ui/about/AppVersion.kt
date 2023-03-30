package dev.sergiobelda.todometer.common.compose.ui.about

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun versionName(): String? {
    val context = LocalContext.current
    return context.getVersionName()
}

private fun Context.getVersionName(): String? =
    try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }

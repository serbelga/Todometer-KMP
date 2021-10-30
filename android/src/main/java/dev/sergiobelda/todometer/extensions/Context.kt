package dev.sergiobelda.todometer.extensions

import android.content.Context
import android.content.pm.PackageManager

fun Context.getVersionName(): String? =
    try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }

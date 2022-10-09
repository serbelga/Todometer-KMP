/*
 * Copyright 2022 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.todometer.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle

internal fun Context.getVersionName(): String? =
    try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }

/**
 * Launch a new Activity and runs the Intent body that will be passed to the new Activity.
 *
 * ```
 * launchActivity<MyActivity>()
 *
 * launchActivity<MyActivity> {
 *      putExtra(...)
 * }
 * ```
 *
 * @param options Additional options for how the Activity should be started.
 * @param block Intent body to start.
 */
internal inline fun <reified A : Activity> Context.launchActivity(
    options: Bundle? = null,
    block: Intent.() -> Unit = {}
) = startActivity(
    Intent(this, A::class.java).apply(block),
    options
)

/**
 * Open a Web page given a URL.
 *
 * ```
 * openWebPage(URL)
 * ```
 *
 * @param url The url of the website to be opened.
 */
internal fun Context.openWebPage(url: String) {
    val webpage: Uri = Uri.parse(url)
    startActivity(
        Intent(Intent.ACTION_VIEW, webpage),
        null
    )
}

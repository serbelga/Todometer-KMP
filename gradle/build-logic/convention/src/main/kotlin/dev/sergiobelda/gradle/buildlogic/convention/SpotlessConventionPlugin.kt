package dev.sergiobelda.gradle.buildlogic.convention
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

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class SpotlessConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val ktlintVersion = libs.findVersion("ktlint").get().toString()

            pluginManager.apply("com.diffplug.spotless")

            extensions.configure<SpotlessExtension> {
                kotlin {
                    target("**/*.kt")
                    targetExclude("**/build/**/*.kt")
                    ktlint(ktlintVersion)
                    licenseHeader(licenseHeaderKotlin)
                }
                format("kts") {
                    target("**/*.kts")
                    targetExclude("**/build/**/*.kts")
                    // Look for the first line that doesn't have a block comment (assumed to be the license)
                    licenseHeader(licenseHeaderKotlin, "(^(?![\\/ ]\\*).*$)")
                }
                format("xml") {
                    target("**/*.xml")
                    targetExclude("**/build/**/*.xml")
                    // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
                    licenseHeader(licenseHeaderXml, "(^(?![\\/ ]\\*).*$)")
                }
            }
        }
    }
}

private val licenseHeaderKotlin = buildString {
    append("/*\n")
    append(" * Copyright \$YEAR Sergio Belda\n")
    append(" *\n")
    append(" * Licensed under the Apache License, Version 2.0 (the \"License\");\n")
    append(" * you may not use this file except in compliance with the License.\n")
    append(" * You may obtain a copy of the License at\n")
    append(" *\n")
    append(" *     http://www.apache.org/licenses/LICENSE-2.0\n")
    append(" *\n")
    append(" * Unless required by applicable law or agreed to in writing, software\n")
    append(" * distributed under the License is distributed on an \"AS IS\" BASIS,\n")
    append(" * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n")
    append(" * See the License for the specific language governing permissions and\n")
    append(" * limitations under the License.\n")
    append(" */\n")
    append("\n")
}

private val licenseHeaderXml = buildString {
    append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
    append("<!--\n")
    append("     Copyright \$YEAR Sergio Belda\n")
    append("     Licensed under the Apache License, Version 2.0 (the \"License\");\n")
    append("     you may not use this file except in compliance with the License.\n")
    append("     You may obtain a copy of the License at\n")
    append("          http://www.apache.org/licenses/LICENSE-2.0\n")
    append("     Unless required by applicable law or agreed to in writing, software\n")
    append("     distributed under the License is distributed on an \"AS IS\" BASIS,\n")
    append("     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n")
    append("     See the License for the specific language governing permissions and\n")
    append("     limitations under the License.\n")
    append("-->")
}

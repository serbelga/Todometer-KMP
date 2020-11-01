/*
 * Copyright 2020 Sergio Belda
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

package com.sergiobelda.todometer.ui.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressUtilTest {

    @Test
    fun `getPercentage for a progress value between 0 and 1`() {
        var percentage = ProgressUtil.getPercentage(0.5f)
        assertEquals("50%", percentage)
        percentage = ProgressUtil.getPercentage(0f)
        assertEquals("0%", percentage)
        percentage = ProgressUtil.getPercentage(1f)
        assertEquals("100%", percentage)
    }

    @Test
    fun `getPercentage for a progress value less than 0`() {
        val percentage = ProgressUtil.getPercentage(-1f)
        assertEquals("-%", percentage)
    }

    @Test
    fun `getPercentage for a progress value greater than 1`() {
        val percentage = ProgressUtil.getPercentage(2f)
        assertEquals("-%", percentage)
    }
}

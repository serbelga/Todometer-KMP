package dev.sergiobelda.todometer.common.data.util

import kotlin.test.Test
import kotlin.test.assertNotNull

class UUIDTest {

    @Test
    fun testRandomUUID() {
        assertNotNull(randomUUIDString())
    }
}

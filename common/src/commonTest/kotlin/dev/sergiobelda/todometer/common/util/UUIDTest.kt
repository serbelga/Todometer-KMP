package dev.sergiobelda.todometer.common.util

import kotlin.test.Test
import kotlin.test.assertNotNull

class UUIDTest {

    @Test
    fun testRandomUUID() {
        assertNotNull(randomUUIDString())
    }
}

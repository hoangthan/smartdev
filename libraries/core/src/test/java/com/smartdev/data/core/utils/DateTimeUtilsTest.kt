package com.smartdev.data.core.utils

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateTimeUtilsTest {

    @Test
    fun formatStringInvalid() {
        val result = DateTimeUtils.formatDateString(
            dateString = "ahihia",
            sourceFormat = DateTimeUtils.ISO_8601,
            destinationFormat = DateTimeUtils.DATE_HOUR
        )

        assert(result == null)
    }

    @Test
    fun formatStringValid() {
        val result = DateTimeUtils.formatDateString(
            dateString = "2023-08-14T20:00:00.000Z",
            sourceFormat = DateTimeUtils.ISO_8601,
            destinationFormat = DateTimeUtils.DATE_HOUR
        )

        assert(result == "08/14/2023 20h:00")
    }

    @Test
    fun formatSourceInvalid() {
        val result = DateTimeUtils.formatDateString(
            dateString = "2023-08-14T20:00:00.000Z",
            sourceFormat = "Hello",
            destinationFormat = DateTimeUtils.DATE_HOUR
        )

        assert(result == null)
    }

    @Test
    fun formatDesInvalid() {
        val result = DateTimeUtils.formatDateString(
            dateString = "2023-08-14T20:00:00.000Z",
            sourceFormat = DateTimeUtils.ISO_8601,
            destinationFormat = "Hello"
        )

        assert(result == null)
    }
}

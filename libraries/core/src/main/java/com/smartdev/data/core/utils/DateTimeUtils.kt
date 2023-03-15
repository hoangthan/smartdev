package com.smartdev.data.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    const val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_HOUR = "MM/dd/yyy HH'h':mm"

    fun formatDateString(
        dateString: String,
        sourceFormat: String,
        destinationFormat: String,
    ): String? {

        val date = try {
            val sourceDateFormat = SimpleDateFormat(sourceFormat, Locale.getDefault())
            sourceDateFormat.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return try {
            val destDateFormat = SimpleDateFormat(destinationFormat, Locale.getDefault())
            destDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun convertStringToMillis(source: String, format: String): Long? {
        return try {
            val sourceDateFormat = SimpleDateFormat(format, Locale.getDefault())
            sourceDateFormat.parse(source)?.time
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}

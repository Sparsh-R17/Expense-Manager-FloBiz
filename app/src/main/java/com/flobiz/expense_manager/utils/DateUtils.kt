package com.flobiz.expense_manager.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    fun formatDate(timeMillis: Long): String {
        return dateFormat.format(Date(timeMillis))
    }

    fun parseDate(dateString: String): Date? {
        return try {
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }
}
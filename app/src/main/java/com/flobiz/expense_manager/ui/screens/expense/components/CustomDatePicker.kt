package com.flobiz.expense_manager.ui.screens.expense.components


import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.flobiz.expense_manager.ui.theme.ColorBackground
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.ColorSecondary
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    onDateSelected: (Long?)->Unit,
    onDismiss:()->Unit,
){

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis(),
        yearRange = IntRange(1900, LocalDate.now().year),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = LocalDate.ofEpochDay(utcTimeMillis / (24 * 60 * 60 * 1000))
                val currentDate = LocalDate.now()
                return !selectedDate.isAfter(currentDate)
            }
        })

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = ColorPrimary
                )
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = ColorPrimary
                )
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = ColorSecondary,
                todayDateBorderColor = ColorPrimary,
                selectedYearContainerColor = ColorPrimary,
                containerColor = ColorBackground,
                todayContentColor = ColorPrimary,
            )
        )
    }

}

object DateUtils {
    fun formatDate(timeMillis: Long): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(Date(timeMillis))
    }
}
package com.flobiz.expense_manager.ui.screens.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flobiz.expense_manager.utils.TransactionType

@Composable
fun FilterDialog(
    currentFilter: TransactionType?,
    onFilterSelected: (TransactionType?) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = Color(0xFFD3D2D2),
        titleContentColor = Color.Black,
        textContentColor = Color.Black,
        iconContentColor = Color.Black,
        onDismissRequest = onDismiss,
        title = { Text("Filter Transactions") },
        text = {
            Column {
                FilterOption("All", currentFilter == null) {
                    onFilterSelected(null)
                }
                FilterOption("Expense", currentFilter == TransactionType.Expense) {
                    onFilterSelected(TransactionType.Expense)
                }
                FilterOption("Income", currentFilter == TransactionType.Income) {
                    onFilterSelected(TransactionType.Income)
                }
            }
        },
        confirmButton = {TextButton(onClick = onDismiss, colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)) {
                Text("Close", )
            }
        }
    )
}

@Composable
private fun FilterOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            colors = RadioButtonDefaults.colors(
                Color.Black
            ),
            selected = isSelected,
            onClick = onClick
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
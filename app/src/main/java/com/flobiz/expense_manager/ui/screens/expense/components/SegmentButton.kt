package com.flobiz.expense_manager.ui.screens.expense.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.TextColorPrimary
import com.flobiz.expense_manager.utils.TransactionType

@Composable

fun SegmentButton(
    selectedOption : TransactionType,
    onOptionSelected:(TransactionType)->Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val options = listOf(TransactionType.Expense, TransactionType.Income)
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selectedOption == option) Color.Transparent else Color.White)
                    .border(width = 1.dp, color = if (selectedOption == option) ColorPrimary else Color.Gray.copy(alpha = 0.5F), shape = RoundedCornerShape(12.dp))
                    .clickable { onOptionSelected(option) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = ColorPrimary,
                        unselectedColor = Color.Gray

                    ),
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = option.toString(),
                    color = TextColorPrimary,
                    fontSize = 16.sp
                )
            }
        }
    }
}
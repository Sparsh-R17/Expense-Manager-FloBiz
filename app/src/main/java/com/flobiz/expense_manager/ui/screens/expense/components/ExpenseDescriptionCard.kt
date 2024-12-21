package com.flobiz.expense_manager.ui.screens.expense.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpenseDescription(
    text : String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column (
            modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Description", fontWeight = FontWeight.SemiBold, color = Color.DarkGray, fontSize = 16.sp)
            HorizontalDivider(
                color = Color.LightGray
            )
            Text(text,  fontWeight = FontWeight.SemiBold, color = Color.Gray, fontSize = 18.sp)
        }
    }
}
package com.flobiz.expense_manager.ui.screens.expense.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpenseAmountCard(
    amt : String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row (
            modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text("Total Amount", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = Color.DarkGray)
            Text("₹ $amt", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = Color.DarkGray)
        }
    }
}
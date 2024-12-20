package com.flobiz.expense_manager.ui.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.utils.TransactionType

@Composable
fun TransactionCard(
    text: String,
    amt: Double,
    id: String,
    date:String,
    type: TransactionType,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .height(110.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ColorPrimary.copy(.14f)),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
                Text(
                    if(type ==  TransactionType.Expense) "- ₹$amt"  else "+ ₹$amt",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (type == TransactionType.Expense) Color.Red else Color(0xFF0A5539)
                )
            }
            Box(modifier = Modifier.height(8.dp))
            Text(
                id,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
            Text(
                date,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray
            )
        }
    }
}
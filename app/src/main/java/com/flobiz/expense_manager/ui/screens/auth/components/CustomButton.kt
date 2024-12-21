package com.flobiz.expense_manager.ui.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flobiz.expense_manager.ui.theme.ColorSecondary

@Composable
fun CustomButton(
    text:String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorSecondary
        ),
        shape = RoundedCornerShape(12.dp)
    ){
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
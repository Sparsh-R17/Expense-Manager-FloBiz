package com.flobiz.expense_manager.ui.screens.auth.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flobiz.expense_manager.R
import com.flobiz.expense_manager.ui.theme.ColorSecondary

@Composable
fun GoogleSignInButton(
    onClick:  ()->Unit,
    modifier: Modifier = Modifier
){
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .wrapContentWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = ColorSecondary,
            contentColor = Color.White,
        ),
        border = BorderStroke(1.dp, color = ColorSecondary),
        shape = RoundedCornerShape(50.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google Logo",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Continue with Google",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}
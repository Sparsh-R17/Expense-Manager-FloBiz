package com.flobiz.expense_manager.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flobiz.expense_manager.ui.screens.settings.components.ProfileAvatar
import com.flobiz.expense_manager.ui.theme.ColorError
import com.flobiz.expense_manager.ui.theme.ColorOnBackground
import com.flobiz.expense_manager.ui.theme.ColorOnSecondary
import com.flobiz.expense_manager.ui.theme.ColorPrimary

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = ColorPrimary.copy(alpha = .05f),
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings",
                        modifier = Modifier.padding(start = 10.dp),
                        fontWeight = FontWeight.Medium,
                        color = ColorOnSecondary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorPrimary.copy(alpha = .8f)
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 30.dp, horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ){
                ProfileAvatar(null)
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    "Display Name",
                    color = ColorOnBackground,
                    fontSize = 30.sp,
                )
            }
            Spacer(modifier=Modifier.height(30.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp),
                color = Color.Gray.copy(alpha = 0.2f)
            )
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ColorError
                ),
                border = BorderStroke(1.dp, ColorError.copy(alpha = 0.2f))
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = ColorError
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign Out",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
package com.flobiz.expense_manager.ui.screens.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flobiz.expense_manager.ui.theme.ColorControlNormal
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.ColorSecondary

@Composable
fun SearchBarTextField() {
    var query = remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        value = query.value,
        onValueChange = { query.value = it },
        placeholder = { Text("Search...") },
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = ColorSecondary
            )
        },
        trailingIcon = {
            if (query.value.isNotEmpty()) {
                IconButton(onClick = { query.value = "" }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear Text")
                }
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = ColorControlNormal.copy(alpha = 0.3f),
            unfocusedContainerColor = ColorControlNormal.copy(alpha = 0.3f),
            focusedIndicatorColor = Color.Transparent,
            cursorColor = ColorPrimary,
            unfocusedTextColor = ColorPrimary,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    )
}
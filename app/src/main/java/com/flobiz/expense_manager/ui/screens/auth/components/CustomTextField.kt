package com.flobiz.expense_manager.ui.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.ColorSecondary
import com.flobiz.expense_manager.ui.theme.TextColorPrimary

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label:String,
    isPassword: Boolean=false,
    keyboardType: KeyboardType = KeyboardType.Text
){
    var passVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextColorPrimary)},
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if(isPassword && !passVisible)
            PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if(isPassword){
            {
                IconButton(onClick = { passVisible = !passVisible }) {
                    Icon(
                        imageVector = if (passVisible)
                            Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Pass"
                    )
                }
            }
        }else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ColorPrimary,
            unfocusedBorderColor = ColorSecondary,
        ),
    )
}
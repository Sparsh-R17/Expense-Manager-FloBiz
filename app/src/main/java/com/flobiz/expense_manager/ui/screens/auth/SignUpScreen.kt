package com.flobiz.expense_manager.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.flobiz.expense_manager.navigation.Screen
import com.flobiz.expense_manager.ui.screens.auth.components.CustomButton
import com.flobiz.expense_manager.ui.screens.auth.components.CustomTextField
import com.flobiz.expense_manager.ui.theme.ColorBackground
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.TextColorPrimary
import com.flobiz.expense_manager.viewModel.AuthViewModel


@Composable
fun SignUpScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confPass by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Create an Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextColorPrimary,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        CustomTextField(
            value = name,
            onValueChange = {name = it},
            label = "Full Name"
        )
        CustomTextField(
            value = email,
            onValueChange = {email = it},
            label = "Email",
            keyboardType = KeyboardType.Email,
        )
        CustomTextField(
            value = pass,
            onValueChange = {pass = it},
            label = "Password",
            isPassword = true,
        )
        CustomTextField(
            value = confPass,
            onValueChange = {confPass = it},
            label = "Confirm Password",
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Sign Up",
            onClick = {
                authViewModel.createWithEmail(name, email, pass) {
                    navController.navigate(Screen.Main.route)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Already have an account? ",
                color = TextColorPrimary
            )
            Text(
                text = "Login",
                color = ColorPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(Screen.Login.route)
                })
            )
        }
    }
}
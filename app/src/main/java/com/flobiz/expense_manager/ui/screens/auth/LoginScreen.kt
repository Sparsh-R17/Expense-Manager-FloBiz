package com.flobiz.expense_manager.ui.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.flobiz.expense_manager.navigation.Screen
import com.flobiz.expense_manager.ui.screens.auth.components.CustomButton
import com.flobiz.expense_manager.ui.screens.auth.components.CustomTextField
import com.flobiz.expense_manager.ui.screens.auth.components.GoogleSignInButton
import com.flobiz.expense_manager.ui.screens.auth.components.TextDivider
import com.flobiz.expense_manager.ui.theme.ColorBackground
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.TextColorPrimary
import com.flobiz.expense_manager.viewModel.AuthState
import com.flobiz.expense_manager.viewModel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel= viewModel()){
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val context = LocalContext.current


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                authViewModel.signInWithGoogle(account)
            }
        } catch (e: ApiException) {
            Log.e("LoginScreen", "Google sign-in failed: ${e.message}")
        }
    }

    val authState = authViewModel.authState.observeAsState()


    LaunchedEffect (authState.value){
        Log.d("LoginScreen2", "LoginScreen: $authState")
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigate(Screen.Main.route)
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).msg, Toast.LENGTH_LONG).show()
            else -> Unit
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Welcome Back",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextColorPrimary,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        CustomTextField(
            value=email,
            onValueChange = {email = it},
            label = "Email",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = pass,
            onValueChange = {pass = it},
            label = "Password",
            isPassword = true,
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomButton(
            text = "Login",
            onClick = {
                authViewModel.loginWithEmailAndPassword(email, pass)

            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Don't have an account? ",
                color = TextColorPrimary
            )
            Text(
                text = "Sign Up",
                color = ColorPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(Screen.SignUp.route)
                })
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        TextDivider()

        Spacer(modifier = Modifier.height(24.dp))

        GoogleSignInButton(
            onClick = {
                val googleSignInClient = getClient(
                    context,
                    Builder(DEFAULT_SIGN_IN)
                        .requestIdToken("967574067307-tqi23itabubika64g7ss3qvdqa0srffj.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
                )
                val intent = googleSignInClient.signInIntent
                launcher.launch(intent)
            }
        )
    }
}
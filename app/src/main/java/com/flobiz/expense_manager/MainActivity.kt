package com.flobiz.expense_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flobiz.expense_manager.navigation.NavItem
import com.flobiz.expense_manager.navigation.NavigationStack
import com.flobiz.expense_manager.navigation.Screen
import com.flobiz.expense_manager.ui.screens.dashboard.DashboardScreen
import com.flobiz.expense_manager.ui.screens.expense.TransactionViewModel
import com.flobiz.expense_manager.ui.screens.settings.SettingsScreen
import com.flobiz.expense_manager.ui.theme.ColorBackground
import com.flobiz.expense_manager.ui.theme.ColorOnSecondary
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.ColorSecondary
import com.flobiz.expense_manager.ui.theme.Flobiz_jcTheme
import com.flobiz.expense_manager.viewModel.AuthViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)

        // Initialize ViewModels
        val transactionViewModel = TransactionViewModel()
        val authViewModel = AuthViewModel()

        setContent {
            Flobiz_jcTheme(darkTheme = false) {
                NavigationStack(
                    transactionViewModel = transactionViewModel,
                    authViewModel = authViewModel
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    transactionViewModel: TransactionViewModel,
    authViewModel: AuthViewModel
) {
    val navItem = listOf(
        NavItem("Dashboard", Icons.Default.Home),
        NavItem("Settings", Icons.Default.Settings)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground,
        floatingActionButton = {
            if (selectedIndex == 0) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Add New") },
                    onClick = { navController.navigate(Screen.AddTransaction.route) },
                    containerColor = ColorSecondary,
                    contentColor = ColorOnSecondary,
                    icon = { Icon(Icons.Filled.Add, "") }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 2.dp,
            ) {
                navItem.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = Color.Gray,
                            selectedIconColor = ColorPrimary,
                            unselectedTextColor = Color.Gray,
                            selectedTextColor = ColorPrimary,
                            indicatorColor = Color.Transparent,
                        ),
                        onClick = { selectedIndex = index },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        when (selectedIndex) {
            0 -> DashboardScreen(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                transactionViewModel = transactionViewModel
            )
            1 -> SettingsScreen(
                navController = navController
            )
        }
    }
}
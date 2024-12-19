package com.flobiz.expense_manager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flobiz.expense_manager.MainScreen
import com.flobiz.expense_manager.ui.screens.expense.ExpenseDetailScreen
import com.flobiz.expense_manager.viewModel.TransactionViewModel

@Composable
fun NavigationStack(transactionViewModel: TransactionViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController, transactionViewModel = transactionViewModel)
        }
        composable(route = Screen.ExpenseDetail.route) {
            ExpenseDetailScreen(transactionViewModel = transactionViewModel, navController = navController)
        }
    }
}


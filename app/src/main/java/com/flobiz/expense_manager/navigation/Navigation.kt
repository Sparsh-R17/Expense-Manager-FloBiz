package com.flobiz.expense_manager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flobiz.expense_manager.MainScreen
import com.flobiz.expense_manager.ui.screens.auth.LoginScreen
import com.flobiz.expense_manager.ui.screens.auth.SignUpScreen
import com.flobiz.expense_manager.ui.screens.expense.ExpenseDetailScreen
import com.flobiz.expense_manager.viewModel.AuthViewModel
import com.flobiz.expense_manager.viewModel.TransactionViewModel

@Composable
fun NavigationStack(transactionViewModel: TransactionViewModel,authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController, transactionViewModel = transactionViewModel)
        }
        composable(route = Screen.ExpenseDetail.route) {
            ExpenseDetailScreen(transactionViewModel = transactionViewModel, navController = navController)
        }
        composable(route=Screen.Login.route) {
            LoginScreen(navController = navController, viewModel =  authViewModel)
        }
        composable(route=Screen.SignUp.route) {
            SignUpScreen(navController = navController,viewModel = authViewModel)
        }
//        composable(route=Screen.AddTransaction.route) {
//            AddExpenseScreen(navController=navController)
//        }
    }
}

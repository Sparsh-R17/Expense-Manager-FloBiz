package com.flobiz.expense_manager.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object ExpenseDetail : Screen("expense_detail_screen")
    object Login: Screen("login_screen")
    object SignUp: Screen("signup_screen")
    object AddTransaction : Screen("add_transaction")
    object EditTransaction : Screen("edit_transaction")
}
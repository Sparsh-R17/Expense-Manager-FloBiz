package com.flobiz.expense_manager.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object ExpenseDetail : Screen("expense_detail_screen/{transactionId}") {
        fun createRoute(transactionId: String): String {
            return "expense_detail_screen/$transactionId"
        }
    }
}

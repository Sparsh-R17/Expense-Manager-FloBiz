package com.flobiz.expense_manager.viewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.flobiz.expense_manager.model.Transaction

class TransactionViewModel : ViewModel() {
    val transactions = mutableStateListOf(
        Transaction(text = "EBill", amount = 10.22, date = "today", invoiceNumber = "123"),
        Transaction(text = "Grocery", amount = 45.67, date = "14 Dec 2024", invoiceNumber = "124"),
        Transaction(
            text = "Internet Bill",
            amount = 55.80,
            date = "13 Dec 2024",
            invoiceNumber = "125"
        ),
        Transaction(
            text = "Mobile Recharge",
            amount = 99.99,
            date = "12 Dec 2024",
            invoiceNumber = "126"
        ),
        Transaction(text = "EBill", amount = 10.22, date = "today", invoiceNumber = "127")
    )

    var selectedTransaction = mutableStateOf<Transaction?>(null)

    fun selectTransaction(transaction: Transaction) {
        selectedTransaction.value = transaction
    }
}

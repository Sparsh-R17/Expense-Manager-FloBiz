package com.flobiz.expense_manager.ui.screens.expense

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flobiz.expense_manager.model.Transaction
import com.flobiz.expense_manager.repository.TransactionRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repository: TransactionRepository = TransactionRepository()
) : ViewModel() {
    val transactions = mutableStateListOf<Transaction>()
    var selectedTransaction = mutableStateOf<Transaction?>(null)
    private var _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    init {
        loadTransactions()
//        if (transactions.isEmpty()) {
//            transactions.addAll(
//                listOf(
//                    Transaction(text = "EBill", amount = 10.22, date = "today", invoiceNumber = "123", type = TransactionType.Expense),
//                    Transaction(text = "Grocery", amount = 45.67, date = "14 Dec 2024", invoiceNumber = "124", type = TransactionType.Expense),
//                    Transaction(text = "Internet Bill", amount = 55.80, date = "13 Dec 2024", invoiceNumber = "125", type = TransactionType.Expense),
//                    Transaction(text = "Mobile Recharge", amount = 99.99, date = "12 Dec 2024", invoiceNumber = "126", type = TransactionType.Expense),
//                    Transaction(text = "EBill", amount = 10.22, date = "today", invoiceNumber = "127", type = TransactionType.Expense)
//                )
//            )
//        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllTransactions().collect { transactionList ->
                transactions.clear()
                transactions.addAll(transactionList)
            }
            _isLoading.value = false
        }
    }

    fun addTransaction(transaction: Transaction, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.addTransaction(transaction)
                .onSuccess {
                    transactions.add(transaction)
                    onSuccess()
                }
                .onFailure {
                    onError(it.message ?: "Failed to add transaction")
                }
            _isLoading.value = false
        }
    }

    fun updateTransaction(
        transaction: Transaction,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.updateTransaction(transaction)
                .onSuccess {
                    val index = transactions.indexOfFirst { it.invoiceNumber == transaction.invoiceNumber }
                    if (index != -1) {
                        transactions[index] = transaction
                    }
                    onSuccess()
                }
                .onFailure {
                    onError(it.message ?: "Failed to update transaction")
                }
            _isLoading.value = false
        }
    }

    fun selectTransaction(transaction: Transaction) {
        selectedTransaction.value = transaction
    }

    fun deleteTransaction(invoiceNumber: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.deleteTransaction(invoiceNumber)
                .onSuccess {
                    transactions.removeAll { it.invoiceNumber == invoiceNumber }
                    onSuccess()
                }
                .onFailure {
                    onError(it.message ?: "Failed to delete transaction")
                }
            _isLoading.value = false
        }
    }
}
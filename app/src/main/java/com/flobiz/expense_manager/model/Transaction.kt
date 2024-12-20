package com.flobiz.expense_manager.model

import com.flobiz.expense_manager.utils.TransactionType

data class Transaction(
    val text : String,
    val invoiceNumber: String,
    val amount : Double,
    val date: String,
    val type: TransactionType,
)
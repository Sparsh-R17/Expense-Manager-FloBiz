package com.flobiz.expense_manager.model

data class Transaction(
    val text : String,
    val invoiceNumber: String,
    val amount : Double,
    val date: String,
)
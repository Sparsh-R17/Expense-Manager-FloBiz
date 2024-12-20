package com.flobiz.expense_manager.repository

import com.flobiz.expense_manager.model.Transaction
import com.flobiz.expense_manager.utils.TransactionType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepository {
    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val userId: String
        get() = auth.currentUser?.uid ?: throw IllegalStateException("User must be logged in")

    private val userTransactionsCollection
        get() = firestore.collection("users").document(userId).collection("transactions")

    suspend fun addTransaction(transaction: Transaction): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val transactionMap = hashMapOf(
                "text" to transaction.text,
                "invoiceNumber" to transaction.invoiceNumber,
                "amount" to transaction.amount,
                "date" to transaction.date,
                "type" to transaction.type.toString(),
                "userId" to userId
            )

            userTransactionsCollection
                .document(transaction.invoiceNumber)
                .set(transactionMap)
                .await()

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getAllTransactions(): Flow<List<Transaction>> = flow {
        try {
            val snapshot = userTransactionsCollection.get().await()
            val transactions = snapshot.documents.mapNotNull { document ->
                try {
                    val text = document.getString("text") ?: return@mapNotNull null
                    val invoiceNumber = document.getString("invoiceNumber") ?: return@mapNotNull null
                    val amount = document.getDouble("amount") ?: return@mapNotNull null
                    val date = document.getString("date") ?: return@mapNotNull null
                    val typeStr = document.getString("type") ?: return@mapNotNull null

                    Transaction(
                        text = text,
                        invoiceNumber = invoiceNumber,
                        amount = amount,
                        date = date,
                        type = TransactionType.valueOf(typeStr)
                    )
                } catch (e: Exception) {
                    null
                }
            }
            emit(transactions)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun deleteTransaction(invoiceNumber: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            userTransactionsCollection
                .document(invoiceNumber)
                .delete()
                .await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
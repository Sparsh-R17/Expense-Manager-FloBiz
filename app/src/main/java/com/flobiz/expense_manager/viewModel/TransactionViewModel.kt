import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.flobiz.expense_manager.model.Transaction
import com.flobiz.expense_manager.utils.TransactionType

class TransactionViewModel : ViewModel() {
    val transactions = mutableStateListOf(
        Transaction(text = "EBill", amount = 10.22, date = "today", invoiceNumber = "123", type = TransactionType.Expense),
        Transaction(text = "Grocery", amount = 45.67, date = "14 Dec 2024", invoiceNumber = "124", type = TransactionType.Expense),
        Transaction(
            text = "Internet Bill",
            amount = 55.80,
            date = "13 Dec 2024",
            invoiceNumber = "125",
            type = TransactionType.Expense
        ),
        Transaction(
            text = "Mobile Recharge",
            amount = 99.99,
            date = "12 Dec 2024",
            invoiceNumber = "126",
            type = TransactionType.Expense
        ),
        Transaction(text = "EBill", amount = 10.22, date = "today", invoiceNumber = "127", type = TransactionType.Expense)
    )

    var selectedTransaction = mutableStateOf<Transaction?>(null)

    fun selectTransaction(transaction: Transaction) {
        selectedTransaction.value = transaction
    }
}
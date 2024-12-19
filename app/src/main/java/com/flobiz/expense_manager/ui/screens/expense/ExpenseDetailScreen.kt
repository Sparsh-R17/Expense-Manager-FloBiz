package com.flobiz.expense_manager.ui.screens.expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.flobiz.expense_manager.R
import com.flobiz.expense_manager.ui.screens.expense.components.ExpenseAmountCard
import com.flobiz.expense_manager.ui.screens.expense.components.ExpenseDescription
import com.flobiz.expense_manager.ui.screens.expense.components.ExpenseIdCard
import com.flobiz.expense_manager.ui.theme.ColorBackground
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.viewModel.TransactionViewModel

//@Composable
//fun ExpenseDetailScreen(transactionViewModel: TransactionViewModel) {
//    val transaction = transactionViewModel.selectedTransaction.value
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Expense Detail",
//            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        transaction?.let {
//            Text(text = "Transaction ID: ${it.invoiceNumber}")
//            Text(text = "Text: ${it.text}")
//            Text(text = "Amount: ${it.amount}")
//            Text(text = "Date: ${it.date}")
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetailScreen(
    transactionViewModel: TransactionViewModel,
    navController: NavHostController
) {
    val transaction = transactionViewModel.selectedTransaction.value

    Scaffold(
        containerColor = ColorPrimary.copy(alpha = .05f),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Expense",
                        modifier = Modifier.padding(start = 10.dp),
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                },
                navigationIcon = {
                    Image(
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }.padding(start = 8.dp),
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = ""
                    )
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 20.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = ""
                        )
                        Box(modifier = Modifier.width(25.dp))
                        Image(
                            painter = painterResource(R.drawable.delete),
                            contentDescription = ""
                        )
                    }
                }
            )

        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 20.dp, horizontal = 10.dp)
        ) {
            ExpenseIdCard(
                invoiceNumber = transaction?.invoiceNumber ?: "",
                invoiceDate = transaction?.date ?: ""
            )
            Box(modifier = Modifier.height(15.dp))
            ExpenseDescription(text = transaction?.text ?:"")
            Box(modifier = Modifier.height(15.dp))
            ExpenseAmountCard(amt = transaction?.amount.toString())
        }
    }
}

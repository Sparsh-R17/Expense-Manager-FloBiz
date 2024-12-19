package com.flobiz.expense_manager.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flobiz.expense_manager.R
import com.flobiz.expense_manager.model.Transaction
import com.flobiz.expense_manager.navigation.Screen
import com.flobiz.expense_manager.ui.screens.dashboard.components.SearchBarTextField
import com.flobiz.expense_manager.ui.screens.dashboard.components.TransactionCard
import com.flobiz.expense_manager.viewModel.TransactionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    transactionViewModel: TransactionViewModel
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp)
    ) {
        SearchBarTextField()
        Box(modifier = Modifier.height(15.dp))
        Text("Recent Transactions")
        Box(modifier = Modifier.height(15.dp))
        LazyColumn {
            items(transactionViewModel.transactions, key = { it.invoiceNumber }) { transaction ->
                SwipeToDismiss(
                    transaction = transaction,
                    onRemove = { transactionViewModel.transactions.remove(transaction) },
                    modifier = Modifier.clickable {
                        transactionViewModel.selectTransaction(transaction)
                        navController.navigate(Screen.ExpenseDetail.route)
                    }
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismiss(
    transaction: Transaction,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    val swipeDismiss = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart) {
                coroutineScope.launch {
                    delay(1.seconds) // Optional delay for animation
                    onRemove()
                }
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = swipeDismiss,
        backgroundContent = {
            val showDeleteIcon = swipeDismiss.currentValue == SwipeToDismissBoxValue.EndToStart

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White) // Set white background
            ) {
                if (showDeleteIcon) {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.CenterEnd)
                            .padding(12.dp)
                    ) {
                        Card(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                        ) {
                            Image(
                                painterResource(R.drawable.swipe_delete),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        TransactionCard(
            text = transaction.text,
            amt = transaction.amount,
            id = transaction.invoiceNumber,
            date = transaction.date
        )
    }
}


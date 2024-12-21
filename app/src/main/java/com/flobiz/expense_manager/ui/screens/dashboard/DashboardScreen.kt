package com.flobiz.expense_manager.ui.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flobiz.expense_manager.R
import com.flobiz.expense_manager.model.Transaction
import com.flobiz.expense_manager.navigation.Screen
import com.flobiz.expense_manager.ui.screens.dashboard.components.FilterDialog
import com.flobiz.expense_manager.ui.screens.dashboard.components.SearchBarTextField
import com.flobiz.expense_manager.ui.screens.dashboard.components.TransactionCard
import com.flobiz.expense_manager.ui.screens.expense.TransactionViewModel
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.utils.TransactionType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    transactionViewModel: TransactionViewModel
) {
    val context = LocalContext.current
    var search = remember { mutableStateOf("") }
    var selectedFilter = remember { mutableStateOf<TransactionType?>(null) }
    var showFilterDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(horizontal = 10.dp)
    ) {
        SearchBarTextField(search)
        Box(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recent Transactions", modifier = Modifier.padding(start = 8.dp))
            IconButton(onClick = {
                showFilterDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Filled.FilterAlt,
                    tint = ColorPrimary,
                    contentDescription = "Filter Button"
                )
            }
        }
        Box(modifier = Modifier.height(15.dp))

        if(transactionViewModel.transactions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("No Expenses Added", modifier = Modifier.align(Alignment.Center))
            }
        }else {
            if (transactionViewModel.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ColorPrimary)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    val filteredTransactions = transactionViewModel.transactions.filter { transaction ->
                        val matchesSearch = transaction.text.contains(search.value, ignoreCase = true) ||
                                transaction.amount.toString().contains(search.value, ignoreCase = true)

                        val matchesFilter = selectedFilter.value?.let { filter ->
                            transaction.type == filter
                        } ?: true

                        matchesSearch && matchesFilter
                    }

                    items(
                        items = filteredTransactions,
                        key = { it.invoiceNumber }
                    ) { transaction ->
                        SwipeToDismiss(
                            transaction = transaction,
                            onRemove = {
                                transactionViewModel.deleteTransaction(
                                    invoiceNumber = transaction.invoiceNumber,
                                    onSuccess = {
                                        // Success handled in ViewModel
                                    },
                                    onError = { error ->
                                        // Show error toast
                                        Toast.makeText(
                                            context,
                                            "Failed to delete: $error",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                )
                            },
                            modifier = Modifier.clickable {
                                transactionViewModel.selectTransaction(transaction)
                                navController.navigate(Screen.ExpenseDetail.route)
                            }
                        )
                    }
                }
            }
        }

        if (showFilterDialog.value) {
            FilterDialog(
                currentFilter = selectedFilter.value,
                onFilterSelected = { filter ->
                    selectedFilter.value = filter
                    showFilterDialog.value = false
                },
                onDismiss = { showFilterDialog.value = false }
            )
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
                    delay(300)
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
                    .background(Color.White)
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
                                contentDescription = "Delete Transaction",
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
            date = transaction.date,
            type = transaction.type
        )
    }
}
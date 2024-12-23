package com.flobiz.expense_manager.ui.screens.expense

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.flobiz.expense_manager.model.Transaction
import com.flobiz.expense_manager.navigation.Screen
import com.flobiz.expense_manager.ui.screens.expense.components.CustomDatePicker
import com.flobiz.expense_manager.ui.screens.expense.components.SegmentButton
import com.flobiz.expense_manager.ui.theme.ColorBackground
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.ColorSecondary
import com.flobiz.expense_manager.ui.theme.TextColorPrimary
import com.flobiz.expense_manager.utils.DateUtils
import com.flobiz.expense_manager.utils.TransactionType
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseScreen(navController: NavController, transactionViewModel: TransactionViewModel) {
    val transaction = transactionViewModel.selectedTransaction.value
    var selectedOption by remember { mutableStateOf(transaction?.type ?: TransactionType.Expense) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableLongStateOf(DateUtils.parseDate(transaction?.date ?: "")?.time ?: System.currentTimeMillis()) }
    var description by remember { mutableStateOf(transaction?.text ?: "") }
    var amount by remember { mutableStateOf(transaction?.amount?.toString() ?: "") }
    val context = LocalContext.current

    val formattedDate = remember(selectedDateMillis) {
        DateUtils.formatDate(selectedDateMillis)
    }

    if (showDatePicker) {
        CustomDatePicker(
            onDateSelected = { dateMillis ->
                dateMillis?.let { selectedDateMillis = it }
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Scaffold(
        containerColor = Color.White.copy(alpha = 0.9f),
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(
                    elevation = 5.dp,
                    spotColor = Color.DarkGray,
                ),
                title = {
                    Text(
                        "Edit Transaction",
                        color = TextColorPrimary,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = ColorPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(paddingValues)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SegmentButton(
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 25.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Date ")
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("*")
                            }
                        },
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = TextColorPrimary,
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = formattedDate,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.clickable(onClick = {
                                    showDatePicker = true
                                }),
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Select Date",
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ColorPrimary,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 25.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Description")
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("*")
                            }
                        },
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = TextColorPrimary,
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ColorPrimary,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "Total Amount",
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = TextColorPrimary,
                    )
                    TextField(
                        value = amount,
                        onValueChange = { amount = it },
                        modifier = Modifier.width(150.dp),
                        singleLine = true,
                        leadingIcon = {
                            Text(
                                "₹",
                                color = TextColorPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = ColorBackground,
                            unfocusedContainerColor = ColorBackground,
                            focusedIndicatorColor = ColorPrimary,
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.White)
            ) {
                Button(
                    onClick = {
                        if (formattedDate.isEmpty() || amount.isEmpty() || description.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Fields are empty. Please ensure all fields are entered",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            transaction?.let { existingTransaction ->
                                val updatedTransaction = existingTransaction.copy(
                                    text = description,
                                    amount = amount.toDouble(),
                                    date = formattedDate,
                                    type = selectedOption
                                )

                                transactionViewModel.updateTransaction(
                                    transaction = updatedTransaction,
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "Transaction updated successfully!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.popBackStack()
                                    },
                                    onError = { error ->
                                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorSecondary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (transactionViewModel.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            "Update",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

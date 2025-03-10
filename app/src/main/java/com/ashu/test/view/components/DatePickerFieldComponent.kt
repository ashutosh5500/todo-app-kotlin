package com.ashu.test.view.components

import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashu.test.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    selectedDate: LocalDate?, onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateText = selectedDate?.format(dateFormatter) ?: "Todo Date"
    val showDialog = remember { mutableStateOf(false) }


    Column {
        Text(
            "Due date",
            color = colorResource(R.color.customOrange),
            style = TextStyle(
                fontSize = 14.sp
            )
        )
        Row {
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Gray, unfocusedTextColor = Color.Gray
                ),
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date"
                    )
                },
                value = dateText,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                onDateSelected(
                                    LocalDate.of(
                                        year,
                                        month + 1,
                                        dayOfMonth
                                    )
                                ) // Month is 0-based
                                showDialog.value = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },  // Trigger dialog on click
                readOnly = true  // Prevents manual text input
            )
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date"
            )
        }
    }
}
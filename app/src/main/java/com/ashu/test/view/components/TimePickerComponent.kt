package com.ashu.test.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerTextField(
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")
    val timeText = selectedTime?.format(timeFormatter) ?: "Todo time"
    val showDialog = remember { mutableStateOf(false) }

    // Function to handle showing the DatePickerDialog
    if (showDialog.value) {
        val calendar = Calendar.getInstance()
        android.app.TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                onTimeSelected(LocalTime.of(hourOfDay, minute, 0)) // Month is 0-based
                showDialog.value = false
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    Column {
        Text(
            "Set time?",
            color = colorResource(R.color.customOrange),
            style = TextStyle(
                fontSize = 14.sp
            )
        )
        TextField(
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                unfocusedTextColor = Color.Gray
            ),

            enabled = false,
            value = timeText,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog.value = true },  // Trigger dialog on click
            readOnly = true  // Prevents manual text input
        )
    }
}
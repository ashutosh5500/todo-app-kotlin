package com.ashu.test.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashu.test.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    onChange: (String) -> Unit,
    value: String,
    error: String?,
    label: String,
    maxLines: Int = 1
) {
    Column {

        Text(
            label,
            color = colorResource(R.color.customOrange),
            style = TextStyle(
                fontSize = 14.sp
            )
        )
        TextField(
            onValueChange = onChange,
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, if (error != null) Color.Red else Color.Gray, RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent, // Removes bottom border
                unfocusedIndicatorColor = Color.Transparent, // Removes bottom border
                errorIndicatorColor = Color.Red
            ),
            maxLines = maxLines,
            singleLine = true,
            isError = error != null,
            textStyle = TextStyle(fontSize = 14.sp),
            placeholder = {
                Text(
                    "Enter here...",
                    style = TextStyle(color = Color.Gray.copy(alpha = 0.5f))
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        }
    }
}
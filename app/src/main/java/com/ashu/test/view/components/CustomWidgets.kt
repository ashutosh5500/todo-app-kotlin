package com.ashu.test.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TitleComponent(title: String) {
    Text(
        text = title,
        color = Color.White,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

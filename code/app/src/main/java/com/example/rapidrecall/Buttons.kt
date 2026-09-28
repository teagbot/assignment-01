package com.example.rapidrecall

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = Color.Blue,
    fontSize: Int? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        if (fontSize != null) Text(text, fontSize = fontSize.sp) else Text(text)
    }
}

@Composable
fun TabButton(
    label: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color.Blue else Color.Gray
        ),
        border = if (selected) BorderStroke(2.dp, Color.Black) else null
    ) {
        Text(label)
    }
}
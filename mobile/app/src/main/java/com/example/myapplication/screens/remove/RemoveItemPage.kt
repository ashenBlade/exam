package com.example.myapplication.screens.remove

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color

@Composable
fun RemoveItemPage(viewModel: RemoveItemViewModel) {
    val id by viewModel.id.observeAsState()
    RemoveItemPageInner(id = id, onValueChange = {
        viewModel.id.value = it
    }, onButtonClick = {
        viewModel.removeItem()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveItemPageInner(id: Int?, onValueChange: (Int?) -> Unit, onButtonClick: () -> Unit) {
    Column {
        Row {
            TextField(value = id?.toString() ?: "", onValueChange = {
                if (it.isEmpty()) {
                    onValueChange(null)
                } else {
                    val parsed = it.toIntOrNull()
                    if (parsed != null) {
                        onValueChange(parsed)
                    }
                }
            })
        }
        Row {
            Button(onClick = onButtonClick, enabled = id != null) {
                Text(text = "Удалить", color = Color.Red)
            }
        }
    }
}

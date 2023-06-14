package com.example.myapplication.screens.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun EditItemPage(viewModel: EditItemViewModel) {
    val id = viewModel.id.observeAsState()
    val value = viewModel.value.observeAsState()
    EditItemPageInner(id = id.value, value = value.value, onIdChange = {
        viewModel.id.value = it
    }, onValueChange = {
        viewModel.value.value = it
    }, onEditButtonClick = {
        viewModel.editItem()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItemPageInner(id: Int?, value: String?, onIdChange: (Int) -> Unit, onValueChange: (String) -> Unit, onEditButtonClick: () -> Unit) {
    Column {
        Row {
            TextField(value = id?.toString() ?: "", onValueChange = {
                val parsed = it.toIntOrNull()
                if (parsed != null) {
                    onIdChange(parsed)
                }
            }, placeholder = {
                Text(text = "Введите Id вещи")
            } )
        }
        Row() {
            TextField(value = value ?: "", onValueChange = {
                onValueChange(it)
            }, placeholder = {
                Text(text = "Введите новое значение вещи")
            } )
        }
        Button(onClick = onEditButtonClick, enabled = value?.isNotEmpty() ?: false) {
            Text(text = "Обновить вещь")
        }
    }
}
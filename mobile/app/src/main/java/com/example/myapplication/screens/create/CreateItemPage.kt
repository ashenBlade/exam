package com.example.myapplication.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateItemPage(viewModel: CreateItemViewModel) {
    val value by viewModel.value.observeAsState()
    Column {
        Row {
            TextField(value = value ?: "", onValueChange = {
                viewModel.value.value = it
            })
        }
        Row {
            Button(onClick = {
                viewModel.createItem()
            }, enabled = value?.isNotEmpty() ?: false) {
                Text(text = "Создать вещь")
            }
        }
    }
}

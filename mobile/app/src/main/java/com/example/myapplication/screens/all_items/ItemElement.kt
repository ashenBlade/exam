package com.example.myapplication.screens.all_items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.Item

@Composable
fun ItemRecord(item: Item) {
    Row {
        Column {
            Text(text = "${item.id}: ")
        }
        Column(modifier = Modifier.weight(weight = 1f)) {
            Text(text = item.value)
        }
    }
}

@Preview
@Composable
fun ItemRecordPreview() {
    ItemRecord(item = Item(10, "Hello, world"))
}

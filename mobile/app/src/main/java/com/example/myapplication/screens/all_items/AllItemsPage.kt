package com.example.myapplication.screens.all_items

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import com.example.core.Item


@Composable
fun AllItemsPage(viewModel: AllItemsViewModel) {
    val items = viewModel.items.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getAllItems()
    }

    AllItemsPageInner(items.value ?: arrayListOf())
}

@Composable
fun AllItemsPageInner(x: ArrayList<Item>) {
    LazyColumn {
        items(x.sortedBy { it.id }) {
            ItemRecord(item = it)
        }
    }
}


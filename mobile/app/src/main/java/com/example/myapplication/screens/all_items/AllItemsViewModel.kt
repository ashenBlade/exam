package com.example.myapplication.screens.all_items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.IItemRepository
import com.example.core.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllItemsViewModel @Inject constructor(private val repository: IItemRepository): ViewModel() {
    val items = MutableLiveData(ArrayList<Item>())

    fun getAllItems() {
        viewModelScope.launch {
            items.value = repository.getAllItems()
        }
    }
}
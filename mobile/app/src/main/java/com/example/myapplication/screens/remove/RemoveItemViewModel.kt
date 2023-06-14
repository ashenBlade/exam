package com.example.myapplication.screens.remove

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.IItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoveItemViewModel @Inject constructor(val repository: IItemRepository): ViewModel() {
    val id = MutableLiveData<Int>()

    fun removeItem() {
        val id = id.value ?: return
        viewModelScope.launch {
            repository.removeItem(id)
            this@RemoveItemViewModel.id.value = null
        }
    }
}
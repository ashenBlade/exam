package com.example.myapplication.screens.create

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.IItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateItemViewModel @Inject constructor(val repository: IItemRepository): ViewModel() {
    val value = MutableLiveData("")
    val id = MutableLiveData<Int>()

    fun createItem() {
        val value = value.value ?: return
        viewModelScope.launch {
            val created = repository.createItem(value)
            id.value = created.id
            this@CreateItemViewModel.value.value = null
        }
    }

    fun setValue(value: String) {
        this.value.value = value
    }
}
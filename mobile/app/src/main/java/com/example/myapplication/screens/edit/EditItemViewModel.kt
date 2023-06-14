package com.example.myapplication.screens.edit

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.IItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditItemViewModel @Inject constructor(val repository: IItemRepository): ViewModel() {
    var id = MutableLiveData<Int>()
    var value = MutableLiveData<String>()

    fun editItem() {
        viewModelScope.launch {
            val id = id.value ?: return@launch
            val value = value.value ?: return@launch
            repository.editItem(id, value)
            this@EditItemViewModel.id.value = 0
            this@EditItemViewModel.value.value = ""
        }
    }
}
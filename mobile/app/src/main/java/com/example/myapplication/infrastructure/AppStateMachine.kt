package com.example.myapplication.infrastructure

import androidx.lifecycle.MutableLiveData

class AppStateMachine {
    val currentState = MutableLiveData<AppState>()

    fun update(state: AppState) {
        if (currentState.value != state) {
            currentState.value = state
        }
    }
}
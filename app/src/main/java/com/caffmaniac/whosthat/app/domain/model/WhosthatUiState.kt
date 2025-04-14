package com.caffmaniac.whosthat.app.domain.model

sealed class WhosthatUiState<out T> {
    data object Loading : WhosthatUiState<Nothing>()
    data object Idle : WhosthatUiState<Nothing>()
    data class Error(val message: String) : WhosthatUiState<Nothing>()
    data class Success<out T>(val data: T) : WhosthatUiState<T>()
}
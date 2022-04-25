package com.android.interviewtask.candyspace.utils

sealed class UIState {
    class SUCCESS(val success: Any): UIState()
    class LOADING(val isLoading: Boolean = true) : UIState()
    class ERROR(val error: Throwable): UIState()
}
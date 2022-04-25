package com.android.interviewtask.candyspace.ui.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.interviewtask.candyspace.rest.StackExchangeRepository
import com.android.interviewtask.candyspace.utils.UIState
import com.android.interviewtask.candyspace.utils.getOutputData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
class UserViewModel(
    private val stackExchangeRepository: StackExchangeRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)
) : CoroutineScope by coroutineScope, ViewModel(){

    private val _usersLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING())
    val usersLiveData: LiveData<UIState> get() = _usersLiveData

    private val _tagsLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING())
    val tagsLiveData: LiveData<UIState> get() = _tagsLiveData

    fun subscribeToUsersList(username:String="") {
        _usersLiveData.postValue(UIState.LOADING())
        collectUsersList()
        launch {
            stackExchangeRepository.getUsersList(username)
        }
    }

    private fun collectUsersList() {
        launch {
            /******
             *  ! Important - Before start testing please uncomment the testing codes.
             */
            //_usersLiveData.postValue(UIState.SUCCESS(getOutputData()))

            /******
             *  ! Important - Before start testing please comment the testing codes.
             */
            stackExchangeRepository.userresponseFlow.collect { uiState ->
                when(uiState) {
                    is UIState.LOADING -> { _usersLiveData.postValue(uiState) }
                    is UIState.SUCCESS -> { _usersLiveData.postValue(uiState) }
                    is UIState.ERROR -> { _usersLiveData.postValue(uiState) }
                }
            }
        }
    }

    fun subscribeToTagsList(userid:String) {
        _tagsLiveData.postValue(UIState.LOADING())
        collectTagsList()
        launch {
            stackExchangeRepository.getTagList(userid)
        }
    }

    private fun collectTagsList() {
        launch {
            stackExchangeRepository.tagresponseFlow.collect { uiState ->
                when(uiState) {
                    is UIState.LOADING -> { _tagsLiveData.postValue(uiState) }
                    is UIState.SUCCESS -> { _tagsLiveData.postValue(uiState) }
                    is UIState.ERROR -> { _tagsLiveData.postValue(uiState) }
                }
            }
        }
    }
}
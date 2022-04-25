package com.android.interviewtask.candyspace.rest

import com.android.interviewtask.candyspace.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface StackExchangeRepository {
    val userresponseFlow: StateFlow<UIState>
    val tagresponseFlow: StateFlow<UIState>
    suspend fun getUsersList(username:String="")
    suspend fun getTagList(userid:String)
}

class StackExchangeRepositoryImpl(
    private val stackExchangeApi: StackExchangeApi
) : StackExchangeRepository {

    private val _userresponseFlow: MutableStateFlow<UIState> = MutableStateFlow(UIState.LOADING())

    override val userresponseFlow: StateFlow<UIState>
        get() = _userresponseFlow

    private val _tagresponseFlow: MutableStateFlow<UIState> = MutableStateFlow(UIState.LOADING())

    override val tagresponseFlow: StateFlow<UIState>
        get() = _tagresponseFlow

    override suspend fun getUsersList(username:String) {
        try {
            val response = stackExchangeApi.getUsersList(username)

            if (response.isSuccessful) {
                response.body()?.let {
                    _userresponseFlow.value = UIState.SUCCESS(it)
                } ?: run {
                    _userresponseFlow.value = UIState.ERROR(IllegalStateException("User details are coming as null!"))
                }
            } else {
                _userresponseFlow.value = UIState.ERROR(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            _userresponseFlow.value = UIState.ERROR(e)
        }
    }

    override suspend fun getTagList(userid: String) {
        try {
            val response = stackExchangeApi.getTopTags(userid)

            if (response.isSuccessful) {
                response.body()?.let {
                    _tagresponseFlow.value = UIState.SUCCESS(it)
                } ?: run {
                    _tagresponseFlow.value = UIState.ERROR(IllegalStateException("Tag details are coming as null!"))
                }

            } else {
                _tagresponseFlow.value = UIState.ERROR(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            _tagresponseFlow.value = UIState.ERROR(e)
        }
    }


}
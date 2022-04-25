package com.android.interviewtask.candyspace.interviewtestcase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.interviewtask.candyspace.rest.StackExchangeRepository
import com.android.interviewtask.candyspace.ui.userslist.UserViewModel
import com.android.interviewtask.candyspace.utils.UIState
import com.android.interviewtask.candyspace.utils.getOutputData
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InterviewTaskViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testCoroutineRule = TestCoroutinesRule()

    @Mock
    lateinit var getUsersInfo: StackExchangeRepository
    private val outputdata= getOutputData()
    @Mock
    lateinit var observer: Observer<UIState>

    private lateinit var viewModel: UserViewModel
    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = UserViewModel(getUsersInfo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `make sure the result is not null`() =
        testCoroutineRule.runBlockingTest {
            val expectedOutput = UIState.SUCCESS(outputdata)
            viewModel.usersLiveData.observeForever(observer)
            Mockito.`when`(viewModel.usersLiveData.value).thenAnswer {
                flowOf(expectedOutput)
            }
            viewModel.subscribeToUsersList()

            Assert.assertNotNull(viewModel.usersLiveData.value)
        }
}
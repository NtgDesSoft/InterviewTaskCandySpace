package com.android.interviewtask.candyspace.ui.userslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.interviewtask.candyspace.R
import com.android.interviewtask.candyspace.adapter.UserListAdapter
import com.android.interviewtask.candyspace.databinding.FragmentUsersListBinding
import com.android.interviewtask.candyspace.model.UsersList
import com.android.interviewtask.candyspace.utils.ARG_USERITEM
import com.android.interviewtask.candyspace.utils.UIState
import com.android.interviewtask.candyspace.utils.showErrorMsg
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersListFragment : Fragment() {

    private val binding by lazy{
        FragmentUsersListBinding.inflate(layoutInflater)
    }
    private val userViewModel: UserViewModel by viewModel()
    private lateinit var usersAdapter: UserListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        usersAdapter=UserListAdapter()
        binding.rcvUserslist.adapter = usersAdapter
        userViewModel.usersLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is UIState.LOADING -> {binding.progressBar.visibility=View.VISIBLE}
                is UIState.SUCCESS -> {
                    binding.progressBar.visibility=View.GONE
                    val usersList=response.success as UsersList
                    usersAdapter.loadData(usersList.userItems)
                }
                is UIState.ERROR -> {
                    binding.progressBar.visibility=View.GONE
                    showErrorMsg(binding.root,response.error.localizedMessage.toString())
                }
            }
        })
        binding.btnSearch.setOnClickListener{
            userViewModel.subscribeToUsersList(binding.edtSearch.text.toString())
        }
        usersAdapter.onItemClick={useritem ->
            val bundle=Bundle().apply{
                putSerializable(ARG_USERITEM,useritem)
            }
            findNavController().navigate(R.id.action_interviewtaskListFragment_to_interviewtaskDetailFragment,bundle)
        }
        userViewModel.subscribeToUsersList(binding.edtSearch.text.toString())

        return binding.root
    }
}
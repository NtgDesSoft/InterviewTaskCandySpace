package com.android.interviewtask.candyspace.ui.userdetails

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.interviewtask.candyspace.MainActivity
import com.android.interviewtask.candyspace.R
import com.android.interviewtask.candyspace.databinding.FragmentUserDetailsBinding
import com.android.interviewtask.candyspace.model.TagItem
import com.android.interviewtask.candyspace.model.TopTags
import com.android.interviewtask.candyspace.model.UserItem
import com.android.interviewtask.candyspace.ui.userslist.UserViewModel
import com.android.interviewtask.candyspace.utils.*
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var userItem: UserItem? = null
    private val binding by lazy{
        FragmentUserDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userItem = it.getSerializable(ARG_USERITEM) as UserItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.supportActionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)

        userItem?.let{user->
            activity?.supportActionBar?.setTitle(user.displayName)
            binding.userName.text=user.displayName+"(${user.userId})"
            binding.userImage.loadImagefromUrl(user.profileImage)
            binding.lastseen.text= context?.getString(R.string.lastseen)+user.lastAccessDate?.getDateTime()
            binding.reputation.text=user.reputation.toString()
            binding.creationDate.text= getDisplayDate(user.creationDate)
            user.creationDate?.let {
                binding.userMembersince.text = getTimeDiff(it)
            }
            user.location?.let{
                binding.location.text=it
            }?: run{
                binding.location.text=context?.getString(R.string.notavailable)
            }



            if(user.badgeCounts.gold>0){
                binding.badgegold.visibility=View.VISIBLE
                binding.userGoldbadge.visibility=View.VISIBLE
                binding.badgegold.text=user.badgeCounts.gold.toString()
            }else{
                binding.badgegold.visibility=View.GONE
                binding.userGoldbadge.visibility=View.GONE
            }

            if(user.badgeCounts.silver>0){
                binding.badgesilver.visibility=View.VISIBLE
                binding.userSilverbadge.visibility=View.VISIBLE
                binding.badgesilver.text=user.badgeCounts.silver.toString()
            }else{
                binding.userSilverbadge.visibility=View.GONE
                binding.badgesilver.visibility=View.GONE
            }

            if(user.badgeCounts.bronze>0){
                binding.badgebronze.visibility=View.VISIBLE
                binding.userBronzebadge.visibility=View.VISIBLE
                binding.badgebronze.text=user.badgeCounts.bronze.toString()
            }else{
                binding.userBronzebadge.visibility=View.GONE
                binding.badgebronze.visibility=View.GONE
            }
            userViewModel.subscribeToTagsList(user.userId.toString())
            userViewModel.tagsLiveData.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is UIState.LOADING -> {}
                    is UIState.SUCCESS -> {
                        val topTags=response.success as TopTags
                        setTags(topTags.tagItems)
                    }
                    is UIState.ERROR -> {
                        showErrorMsg(binding.root,response.error.localizedMessage.toString())
                    }
                }
            })
        }
        return binding.root
    }

    fun setTags(tags: List<TagItem>) {
        if (tags.isNotEmpty()) {
            binding.tagItems.visibility = View.VISIBLE
            for (tag in tags) {
                val mChip =
                    this.layoutInflater.inflate(R.layout.item_tags, binding.root, false) as Chip
                mChip.text = tag.tagName.uppercase()
                val paddingDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f,
                    resources.displayMetrics
                ).toInt()
                mChip.setPadding(paddingDp, 0, paddingDp, 0)
                binding.tagItems.addView(mChip)
            }
        } else {
            binding.tagItems.visibility = View.GONE
        }
    }
}
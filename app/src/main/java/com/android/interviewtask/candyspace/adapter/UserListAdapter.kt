package com.android.interviewtask.candyspace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.interviewtask.candyspace.databinding.LayUseritemBinding
import com.android.interviewtask.candyspace.model.UserItem
import com.android.interviewtask.candyspace.utils.getDateTime
import com.android.interviewtask.candyspace.utils.getTimeDiff
import com.android.interviewtask.candyspace.utils.loadImagefromUrl

class UserListAdapter(
    private val listofUsers:MutableList<UserItem> = mutableListOf()
): RecyclerView.Adapter<UserViewHolder>() {

    var onItemClick: ((UserItem) -> Unit)? = null

    fun loadData(listOfAlerts: MutableList<UserItem>){
        this.listofUsers.clear()
        this.listofUsers.addAll(listOfAlerts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayUseritemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setAlertData(listofUsers[position])
        holder.itemView.rootView.setOnClickListener {
            onItemClick?.invoke(listofUsers[position])
        }
    }

    override fun getItemCount(): Int {
        return listofUsers.size
    }
}

class UserViewHolder(itemView: LayUseritemBinding) : RecyclerView.ViewHolder(itemView.root) {
    val binding=itemView
    fun setAlertData(userItem: UserItem) {
        binding.userName.text=userItem.displayName+"(${userItem.userId})"
        binding.userImage.loadImagefromUrl(userItem.profileImage)
        binding.lastseen.text= userItem.lastAccessDate?.getDateTime()
        userItem.creationDate?.let {
            binding.userMembersince.text = getTimeDiff(it)
        }
        if(userItem.badgeCounts.gold>0){
            binding.badgegold.visibility=View.VISIBLE
            binding.userGoldbadge.visibility=View.VISIBLE
            binding.badgegold.text=userItem.badgeCounts.gold.toString()
        }else{
            binding.badgegold.visibility=View.GONE
            binding.userGoldbadge.visibility=View.GONE
        }

        if(userItem.badgeCounts.silver>0){
            binding.badgesilver.visibility=View.VISIBLE
            binding.userSilverbadge.visibility=View.VISIBLE
            binding.badgesilver.text=userItem.badgeCounts.silver.toString()
        }else{
            binding.userSilverbadge.visibility=View.GONE
            binding.badgesilver.visibility=View.GONE
        }

        if(userItem.badgeCounts.bronze>0){
            binding.badgebronze.visibility=View.VISIBLE
            binding.userBronzebadge.visibility=View.VISIBLE
            binding.badgebronze.text=userItem.badgeCounts.bronze.toString()
        }else{
            binding.userBronzebadge.visibility=View.GONE
            binding.badgebronze.visibility=View.GONE
        }
    }
}
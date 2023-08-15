package com.apex.codeassesment.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.apex.codeassesment.data.model.User

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apex.codeassesment.R

class UserAdapter(
    private var userList: List<User>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView,itemClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.setData(currentUser)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(users: List<User>) {
        this.userList = users
        notifyDataSetChanged()
    }


    class UserViewHolder(itemView: View, private val itemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private var currentUser: User? = null
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    currentUser.let {
                        itemClickListener.onItemClick(it!!)
                    }
                }
            }
        }

         fun setData(user:User){
            currentUser = user
             textViewUserName.text = currentUser?.email
         }
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

}

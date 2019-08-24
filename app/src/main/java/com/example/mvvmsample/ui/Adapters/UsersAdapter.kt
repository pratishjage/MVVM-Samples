package com.example.mvvmsample.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmsample.R
import com.example.mvvmsample.db.User

class UsersAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>() // Cached copy of users

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameText: TextView = itemView.findViewById(R.id.name_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = users[position]
        holder.userNameText.text = current.name
    }

    internal fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size
}
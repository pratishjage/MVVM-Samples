package com.example.mvvmsample.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmsample.R
import com.example.mvvmsample.db.User

class UsersAdapter internal constructor(
    context: Context, val listener: (User) -> Unit, val favlistener: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>() // Cached copy of users

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameText: TextView = itemView.findViewById(R.id.name_txt)
        val phoneText: TextView = itemView.findViewById(R.id.phone_txt)

        val viewonMapBtn: Button = itemView.findViewById(R.id.view_on_map_btn)
        val likeBtn: ImageView = itemView.findViewById(R.id.like_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = users[position]
        holder.userNameText.text = current.name
        holder.viewonMapBtn.setOnClickListener {
            listener(current)
        }
        holder.likeBtn.setOnClickListener {
            current.isFavourite = !current.isFavourite
            favlistener(current)
        }
        when {
            current.isFavourite -> holder.likeBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
            else -> holder.likeBtn.setBackgroundResource(R.drawable.ic_favorite_border_grey_24dp)
        }

        holder.phoneText.text = current.phone
    }

    internal fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }


    override fun getItemCount() = users.size
}
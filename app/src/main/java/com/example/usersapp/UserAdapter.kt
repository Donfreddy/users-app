package com.example.usersapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.usersapp.model.User


/**
 * RecyclerView Adapter for setting up data binding on the users in the list.
 */
class UserAdapter(private val users: List<User>) :
  RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

  // Return the size of data (invoked by the layout manager)
  override fun getItemCount() = users.size

  /**
   * Called by RecyclerView to display the users at the specified position. This method should
   * update the contents of the {@link ViewHolder#userView} to reflect the user at the given
   * position.
   */
  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    val user = users[position]

    holder.userName.text = user.name
    holder.userUserName.text = user.username
    holder.userMobile.text = user.phone
  }

  /**
   * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
   * an item.
   */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val view = layoutInflater.inflate(R.layout.user_list_item, parent, false)
    return UserViewHolder(view);
  }

  /**
   * ViewHolder for Users. All work is done by data binding.
   */
  class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userName: TextView = itemView.findViewById(R.id.user_name)
    val userUserName: TextView = itemView.findViewById(R.id.user_username)
    val userMobile: TextView = itemView.findViewById(R.id.user_phone)
  }
}


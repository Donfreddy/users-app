package com.example.usersapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.usersapp.model.User


/**
 * RecyclerView Adapter for setting up data binding on the users in the list.
 */
class UserAdapter(private val users: List<User>, private val context: Context) :
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

    holder.userCardView.setOnClickListener {
      // Create an intent with a destination of DetailActivity
      val intent = Intent(context, DetailActivity::class.java)

      // Add the selected user to the intent as extra data
      intent.putExtra(DetailActivity.USER_NAME, user.name)
      intent.putExtra(DetailActivity.USER_USERNAME, user.username)
      intent.putExtra(DetailActivity.USER_PHONE,user.phone)
      intent.putExtra(DetailActivity.USER_EMAIL, user.email)

      // Start an activity using the data and destination from the Intent.
      context.startActivity(intent)
    }
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
  class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userName: TextView = view.findViewById(R.id.user_name)
    val userCardView: CardView = view.findViewById(R.id.cv_user)
  }
}


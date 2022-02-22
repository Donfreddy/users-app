package com.example.usersapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.usersapp.R

class DetailActivity : AppCompatActivity() {

  /**
   * Provides global access to these variables from anywhere in the app
   * via DetailActivity.<variable> without needing to create
   * a DetailActivity instance.
   */
  companion object {
    const val USER_NAME = "name"
    const val USER_USERNAME = "username"
    const val USER_PHONE = "phone"
    const val USER_EMAIL = "email"
  }

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    // calling and showing the back button in action bar
    val actionBar: ActionBar? = supportActionBar
    actionBar?.setDisplayHomeAsUpEnabled(true);

    // Retrieve the User from the Intent extras
    val name = intent?.extras?.getString(USER_NAME).toString()
    val username = intent?.extras?.getString(USER_USERNAME).toString()
    val email = intent?.extras?.getString(USER_EMAIL).toString()
    val phone = intent?.extras?.getString(USER_PHONE).toString()

    val userName: TextView = findViewById(R.id.name)
    val userUserName: TextView = findViewById(R.id.username)
    val userEmail: TextView = findViewById(R.id.email)
    val userMobile: TextView = findViewById(R.id.userPhone)

    userName.text = "✔ Name: $name"
    userUserName.text = "✔ Username: $username"
    userEmail.text = "✔ Email: $email"
    userMobile.text = "✔ Phone: $phone"

    title = name
  }

  /**
   * This event will enable the back function to the button on press
   */
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        this.finish()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}
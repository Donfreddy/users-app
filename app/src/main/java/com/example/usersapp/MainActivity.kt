package com.example.usersapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersapp.network.Resource
import com.google.android.material.snackbar.Snackbar
import com.sevengps.sdn_mobile_app.repository.Repository


class MainActivity : AppCompatActivity() {
  private lateinit var viewModel: MainViewModel
  private lateinit var constraintLayout: ConstraintLayout
  private lateinit var userRecyclerView: RecyclerView

  private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
      val notConnected = intent.getBooleanExtra(
        ConnectivityManager
          .EXTRA_NO_CONNECTIVITY, false
      )
      if (notConnected) {
        showSnackBar("Internet Disconnected. 👎", Color.RED)
      } else {
        showSnackBar("Internet Connected. 👍", Color.GREEN)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    //
    registerNetworkBroadcast()

    userRecyclerView = findViewById(R.id.user_lists)
    constraintLayout = findViewById(R.id.root)


    val factory = MainViewModel.Factory(Repository())
    viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java];

    viewModel.getAllUsers()
    viewModel.usersLiveData.observe(this, Observer { res ->
      when (res) {
        is Resource.Loading -> {
          Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show()
        }
        is Resource.Success -> {
          res.data?.let { result ->
            val adapter = UserAdapter(result)
            userRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            userRecyclerView.adapter = adapter
//            val userCardView: CardView = userRecyclerView.findViewById(R.id.cv_user)
//            userCardView.setOnClickListener{
//              Toast.makeText(this, "View User Detail", Toast.LENGTH_LONG).show()
//            }

          }
        }
        is Resource.Error -> {
          println(res)
          res.message?.let { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
          }
        }
      }
    })
  }

  private fun registerNetworkBroadcast() {
    val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    registerReceiver(broadcastReceiver, filter)
  }

  override fun onDestroy() {
    super.onDestroy()
    unregisterReceiver(broadcastReceiver)
  }

  fun showSnackBar(message: String, color: Int) {
    val snackBar: Snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_LONG)
    snackBar.setBackgroundTint(ContextCompat.getColor(applicationContext, color))
    snackBar.setActionTextColor(Color.WHITE)
    snackBar.show()
  }
}
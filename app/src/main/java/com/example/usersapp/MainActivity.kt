package com.example.usersapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersapp.network.Resource
import com.sevengps.sdn_mobile_app.repository.Repository


class MainActivity : AppCompatActivity() {
  private lateinit var viewModel: MainViewModel
  private lateinit var userRecyclerView: RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    userRecyclerView = findViewById(R.id.user_lists)

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
}
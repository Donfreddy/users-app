package com.example.usersapp

import com.example.usersapp.model.User
import com.example.usersapp.network.Network
import retrofit2.Response

class Repository {
  suspend fun getAllUsers(): Response<List<User>> {
    return Network.users.getAll()
  }
}

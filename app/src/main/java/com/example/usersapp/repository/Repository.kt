package com.sevengps.sdn_mobile_app.repository

import android.view.SurfaceControl
import com.example.usersapp.model.User
import com.example.usersapp.network.Network
import retrofit2.Response

class Repository {
  suspend fun getAllUsers(): Response<List<User>> {
    return Network.users.getAll()
  }

  suspend fun getOne(id: Int): Response<User> {
    return Network.users.getById(id)
  }
}

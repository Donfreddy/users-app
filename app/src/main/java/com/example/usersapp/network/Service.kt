package com.example.usersapp.network

import com.example.usersapp.model.User
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * A retrofit service to fetch users.
 */
interface UserService {

  @GET("users")
  suspend fun getAll(): Response<List<User>>

  @GET("users/:id")
  suspend fun getById(@Path("id") id: Int): Response<User>
}


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

/**
 * Main entry point for network access. Call like `Network.users.getAll()`
 */
object Network {
  // Configure retrofit to parse JSON and use coroutines
  private val retrofit = Retrofit.Builder()
    .baseUrl("https://jsonplaceholder.typicode.com/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

  val users: UserService = retrofit.create(UserService::class.java)
}
package com.example.usersapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.usersapp.model.User
import com.example.usersapp.network.Resource
import com.sevengps.sdn_mobile_app.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(private val repo: Repository) : ViewModel() {
  val usersLiveData: MutableLiveData<Resource<List<User>>> = MutableLiveData()

  fun getAllUsers() {
    usersLiveData.value = Resource.Loading()

    viewModelScope.launch(Dispatchers.IO) {
      try {
        val response = repo.getAllUsers()

        if (!response.isSuccessful) {
          usersLiveData.value = Resource.Error("Something Went Wrong")
        } else {
          viewModelScope.launch(Dispatchers.Main) {
            response.body()?.let { result ->
              usersLiveData.value = Resource.Success(result)
            }
          }
        }
      } catch (e: Exception) {
        viewModelScope.launch(Dispatchers.Main) {
          usersLiveData.value = Resource.Error(e.message.orEmpty())
        }
      }
    }
  }

  /**
   * Factory for constructing MainViewModel with parameter
   */
  class Factory(private val repo: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(repo) as T
      }
      throw IllegalArgumentException("Unable to construct viewModel")
    }
  }
}
package com.fincare.shaadikaro.ui.persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class PersonsViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonsViewModel(repository) as T
    }
}
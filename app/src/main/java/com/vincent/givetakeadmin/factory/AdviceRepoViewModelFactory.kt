package com.vincent.givetakeadmin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.givetakeadmin.data.repository.advice.AdviceRepository
import com.vincent.givetakeadmin.di.Injection
import com.vincent.givetakeadmin.ui.fragment.advice.AdviceViewModel

class AdviceRepoViewModelFactory(private val repository: AdviceRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdviceViewModel::class.java)) {
            return AdviceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: AdviceRepoViewModelFactory? = null

        fun getInstance(): AdviceRepoViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AdviceRepoViewModelFactory(Injection.provideAdviceRepository())
            }.also { instance = it }
    }
}
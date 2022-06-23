package com.vincent.givetakeadmin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.di.Injection
import com.vincent.givetakeadmin.preference.UserPreferences
import com.vincent.givetakeadmin.ui.activity.splash.SplashViewModel
import com.vincent.givetakeadmin.ui.fragment.home.HomeViewModel

class RewardRepoViewModelFactory(private val rewardRepository: RewardRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(rewardRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: RewardRepoViewModelFactory? = null

        fun getInstance(): RewardRepoViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: RewardRepoViewModelFactory(Injection.provideRewardRepository())
            }.also { instance = it }
    }

}
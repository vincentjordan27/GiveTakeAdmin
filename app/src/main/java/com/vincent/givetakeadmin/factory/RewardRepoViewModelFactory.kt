package com.vincent.givetakeadmin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.di.Injection
import com.vincent.givetakeadmin.ui.activity.reward.RewardViewModel
import com.vincent.givetakeadmin.ui.fragment.home.HomeViewModel
import com.vincent.givetakeadmin.ui.fragment.reward.RewardRequestViewModel

class RewardRepoViewModelFactory(private val rewardRepository: RewardRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RewardRequestViewModel::class.java)) {
            return RewardRequestViewModel(rewardRepository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(rewardRepository) as T
        } else if (modelClass.isAssignableFrom(RewardViewModel::class.java)) {
            return RewardViewModel(rewardRepository) as T
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
package com.vincent.givetakeadmin.di

import com.vincent.givetakeadmin.data.repository.admin.AdminRepository
import com.vincent.givetakeadmin.data.repository.advice.AdviceRepository
import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.data.source.ApiClient

object Injection {
    fun provideRewardRepository() : RewardRepository {
        val apiService = ApiClient.getRewardService()
        return RewardRepository.getInstance(apiService)
    }

    fun provideAdviceRepository() : AdviceRepository {
        val apiService = ApiClient.getAdviceService()
        return AdviceRepository.getInstance(apiService)
    }

    fun provideAdminRepository() : AdminRepository {
        val apiService = ApiClient.getAdminService()
        return AdminRepository.getInstance(apiService)
    }
}
package com.vincent.givetakeadmin.ui.activity.request

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.response.request.RewardRequest
import com.vincent.givetakeadmin.databinding.ActivityRewardRequestDetailBinding
import com.vincent.givetakeadmin.factory.RewardRepoViewModelFactory
import com.vincent.givetakeadmin.ui.activity.reward.RewardViewModel
import com.vincent.givetakeadmin.ui.fragment.reward.RewardRequestViewModel
import com.vincent.givetakeadmin.utils.Constant
import com.vincent.givetakeadmin.utils.Result

class RewardRequestDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRewardRequestDetailBinding
    private lateinit var viewModel: RewardRequestViewModel
    private var data: RewardRequest? = null
    private var confirm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardRequestDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra(Constant.REQUEST_REWARD_ITEM)
        if (data == null) {
            Toast.makeText(this, "Terjadi kesalahan. Silahkan coba lagi", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            setData(data!!)
        }
        val factory = RewardRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[RewardRequestViewModel::class.java]

        setListener()
        setObserver()
    }

    private fun setData(data: RewardRequest) {
        Glide.with(this)
            .load(data.photo)
            .placeholder(R.drawable.ic_load)
            .into(binding.img)
        binding.txtName.text = data.name
        binding.tvNameReward.text = data.rewardName
        binding.txtDesc.text = data.desc
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnDone.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this, R.style.CostumDialog)
                .setTitle("Konfirmasi ?")
                .setMessage("Konfirmasi penyelesaian hadiah ?")
                .setCancelable(false)
                .setPositiveButton("Ya"){_ , _ ->
                    confirm = true
                }
                .setNegativeButton("Tidak"){dialog, _ ->
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    if (confirm) {
                        viewModel.finishRequestReward(data?.id!!)
                    }
                }
            alertDialog.show()
        }
    }

    private fun setObserver() {
        viewModel.finishRequestRewardResult.observe(this) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(this, it.data?.message!!, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    Snackbar.make(binding.txtError, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pg.visibility = View.VISIBLE
        } else {
            binding.pg.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
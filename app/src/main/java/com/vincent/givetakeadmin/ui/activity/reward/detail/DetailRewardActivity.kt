package com.vincent.givetakeadmin.ui.activity.reward.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.response.reward.RewardItem
import com.vincent.givetakeadmin.databinding.ActivityDetailRewardBinding
import com.vincent.givetakeadmin.factory.RewardRepoViewModelFactory
import com.vincent.givetakeadmin.ui.activity.reward.RewardViewModel
import com.vincent.givetakeadmin.ui.activity.reward.edit.EditRewardActivity
import com.vincent.givetakeadmin.utils.Constant
import com.vincent.givetakeadmin.utils.Result

class DetailRewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRewardBinding
    private lateinit var viewModel: RewardViewModel
    private var id: String = ""
    private var detailItem: RewardItem?= null
    private var delete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(Constant.DETAIL_REWARD_ID) ?: ""
        val factory = RewardRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[RewardViewModel::class.java]
        setListener()
        setObserver()

        viewModel.rewardById(id)

    }

    private fun setListener() {
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditRewardActivity::class.java)
            intent.putExtra(Constant.DETAIL_REWARD_ITEM, detailItem)
            startActivity(intent)
        }
        binding.btnDelete.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this, R.style.CostumDialog)
                .setTitle("Hapus ?")
                .setMessage("Anda yakin ingin menghapus hadiah ?")
                .setCancelable(false)
                .setPositiveButton("Ya"){_ , _ ->
                    delete = true
                }
                .setNegativeButton("Tidak"){dialog, _ ->
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    if (delete) {
                        viewModel.deleteReward(id)
                    }
                }
            alertDialog.show()
        }
    }

    private fun setObserver() {
        viewModel.detailRewardResult.observe(this) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    detailItem = it.data?.data!!
                    setData(it.data.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    Snackbar.make(binding.txtError, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
        viewModel.deleteRewardResult.observe(this) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(this, "Berhasil menghapus hadiah", Toast.LENGTH_SHORT).show()
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

    private fun setData(data: RewardItem) {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        Glide.with(this)
            .load(data.photo)
            .placeholder(R.drawable.ic_load)
            .into(binding.img)
        binding.tvName.text = data.name
        binding.txtDesc.text = data.desc
        binding.txtStock.text = data.stock.toString()
        binding.txtPoint.text = data.price.toString()
    }

    override fun onResume() {
        super.onResume()
        viewModel.rewardById(id)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
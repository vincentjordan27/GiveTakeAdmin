package com.vincent.givetakeadmin.ui.activity.reward.edit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.request.AddUpdateRewardRequest
import com.vincent.givetakeadmin.data.source.response.reward.RewardItem
import com.vincent.givetakeadmin.databinding.ActivityEditRewardBinding
import com.vincent.givetakeadmin.factory.RewardRepoViewModelFactory
import com.vincent.givetakeadmin.ui.activity.reward.RewardViewModel
import com.vincent.givetakeadmin.utils.Constant
import com.vincent.givetakeadmin.utils.Result
import com.vincent.givetakeadmin.utils.uriToFile

class EditRewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRewardBinding
    private lateinit var viewModel: RewardViewModel
    private var url: String = ""
    private var currentUri: Uri? = null
    private var data: RewardItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<RewardItem>(Constant.DETAIL_REWARD_ITEM)
        if (data != null) {
            setData(data!!)
        } else {
            Toast.makeText(this, "Terjadi kesalahan. Silahkan coba lagi", Toast.LENGTH_SHORT).show()
            finish()
        }

        val factory = RewardRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[RewardViewModel::class.java]
        setObserver()
        setListener()
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            when {
                binding.edtName.text.toString().isEmpty() -> {
                    binding.edtName.error = "Nama hadiah harus diisi"
                }
                binding.edtPoint.text.toString().isEmpty() -> {
                    binding.edtPoint.error = "Poin hadiah harus diisi"
                }
                binding.edtDesc.text.toString().isEmpty() -> {
                    binding.edtDesc.error = "Deskripsi hadiah harus diisi"
                }
                binding.edtStock.text.toString().isEmpty() -> {
                    binding.edtStock.error = "Stok hadiah harus diisi"
                }
                else -> {
                    if (url == "") url = data!!.photo else url
                    val addUpdateRewardRequest = AddUpdateRewardRequest(
                        binding.edtName.text.toString(),
                        url,
                        binding.edtDesc.text.toString(),
                        binding.edtPoint.text.toString().toInt(),
                        binding.edtStock.text.toString().toInt()
                    )
                    viewModel.updateReward(data!!.id, addUpdateRewardRequest)
                }
            }
        }
        binding.img.setOnClickListener {
            startGallery()
        }
    }

    private fun setObserver() {
        viewModel.uploadImageRewardResult.observe(this) {
            when (it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    url = it.data?.data?.url!!
                    binding.img.setImageURI(currentUri)
                }
                is Result.Error -> {
                    showLoading(false)
                    Snackbar.make(binding.txtError, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
        viewModel.updateRewardResult.observe(this) {
            when (it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(this, "Berhasil memperbarui hadiah", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    Snackbar.make(binding.txtError, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun startGallery() {
        showLoading(true)
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih Gambar")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        showLoading(false)
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val file = uriToFile(selectedImg, this)
            currentUri = selectedImg
            if (file.length() / 1048576F > 5F) {
                val dialog = AlertDialog.Builder(this)
                dialog.setCancelable(false)
                dialog.setTitle("Ukuran Gambar Harus Dibawah 5 MB")
                dialog.setPositiveButton("Ok"){ dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                dialog.show()
            } else {
                viewModel.uploadImageReward(file)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setData(data: RewardItem) {
        Glide.with(this)
            .load(data.photo)
            .placeholder(R.drawable.ic_load)
            .into(binding.img)

        binding.edtName.setText(data.name)
        binding.edtDesc.setText(data.desc)
        binding.edtStock.setText(data.stock.toString())
        binding.edtPoint.setText(data.price.toString())
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pg.visibility = View.VISIBLE
        } else {
            binding.pg.visibility = View.GONE
        }
    }
}
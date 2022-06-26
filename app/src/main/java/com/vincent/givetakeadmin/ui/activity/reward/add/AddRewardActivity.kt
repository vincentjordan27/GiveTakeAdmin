package com.vincent.givetakeadmin.ui.activity.reward.add

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.request.AddRewardRequest
import com.vincent.givetakeadmin.databinding.ActivityAddRewardBinding
import com.vincent.givetakeadmin.factory.RewardRepoViewModelFactory
import com.vincent.givetakeadmin.ui.activity.reward.RewardViewModel
import com.vincent.givetakeadmin.utils.Result
import com.vincent.givetakeadmin.utils.uriToFile

class AddRewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRewardBinding
    private lateinit var viewModel: RewardViewModel
    private var url: String = ""
    private var currentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = RewardRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[RewardViewModel::class.java]
        setObserver()
        setListener()
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
        viewModel.addRewardRequest.observe(this) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(this, "Berhasil menambahkan hadiah", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    Snackbar.make(binding.txtError, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.img.setOnClickListener {
            startGallery()
        }
        binding.btnSave.setOnClickListener {
            when {
                currentUri == null -> {
                    Snackbar.make(binding.txtError, "Silahkan pilih gambar terlebih dahulu", Snackbar.LENGTH_LONG).show()
                }
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
                    val addRewardRequest = AddRewardRequest(
                        binding.edtName.text.toString(),
                        url,
                        binding.edtDesc.text.toString(),
                        binding.edtPoint.text.toString().toInt(),
                        binding.edtStock.text.toString().toInt()
                    )
                    viewModel.addReward(addRewardRequest)
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
                if (currentUri != null) {
                    binding.img.setImageURI(currentUri)
                }
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
}
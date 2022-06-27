package com.vincent.givetakeadmin.ui.activity.advice.reply

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.request.ReplyAdviceRequest
import com.vincent.givetakeadmin.data.source.response.advice.AdviceItem
import com.vincent.givetakeadmin.databinding.ActivityReplyAdviceBinding
import com.vincent.givetakeadmin.factory.AdviceRepoViewModelFactory
import com.vincent.givetakeadmin.ui.activity.advice.detail.DetailAdviceActivity
import com.vincent.givetakeadmin.ui.fragment.advice.AdviceViewModel
import com.vincent.givetakeadmin.utils.Constant
import com.vincent.givetakeadmin.utils.Result

class ReplyAdviceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReplyAdviceBinding
    private lateinit var viewModel: AdviceViewModel
    private var data: AdviceItem? = null
    private var isSend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReplyAdviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

       data = intent.getParcelableExtra(Constant.REPLY_ADVICE_ITEM)
        if (data == null) {
            Toast.makeText(this, "Terjadi kesalahan. Silahkan coba lagi", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            setData(data!!)
        }
        setListener()

        val factory = AdviceRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[AdviceViewModel::class.java]

        setObserver()
    }

    private fun setData(data: AdviceItem) {
        if (data.category == Constant.ADVICE_TYPE_SARAN) {
            binding.tvHeader.text = "Balas Saran"
        } else {
            binding.tvHeader.text = "Balas Keluhan"
        }
        binding.txtTitle.text = data.title
        binding.edtDesc.text = data.description
        binding.edtType.text = data.category
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnSend.setOnClickListener {
            if (binding.edtMessage.text.toString().isEmpty()) {
                binding.edtMessage.error = "Pesan balasan harus diisi"
            } else {
                val replyAdviceRequest = ReplyAdviceRequest(
                    binding.edtMessage.text.toString()
                )
                val alertDialog = AlertDialog.Builder(this, R.style.CostumDialog)
                    .setMessage("Konfirmasi kirim pesan balasan ?")
                    .setCancelable(false)
                    .setPositiveButton("Ya"){_ , _ ->
                        isSend = true
                    }
                    .setNegativeButton("Tidak"){dialog, _ ->
                        dialog.dismiss()
                    }
                    .setOnDismissListener {
                        if (isSend) {
                            viewModel.replyAdvice(data?.id!!, replyAdviceRequest)
                        }
                    }
                alertDialog.show()
            }
        }
    }

    private fun setObserver() {
        viewModel.replyAdviceResult.observe(this) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(this, it.data?.message, Toast.LENGTH_SHORT).show()
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
        val intent = Intent(this, DetailAdviceActivity::class.java)
        intent.putExtra(Constant.DETAIL_ADVICE_ITEM, data)
        startActivity(intent)
        finish()
    }
}
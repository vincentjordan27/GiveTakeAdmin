package com.vincent.givetakeadmin.ui.activity.advice.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.response.advice.AdviceItem
import com.vincent.givetakeadmin.databinding.ActivityDetailAdviceBinding
import com.vincent.givetakeadmin.ui.activity.advice.reply.ReplyAdviceActivity
import com.vincent.givetakeadmin.utils.Constant

class DetailAdviceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAdviceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAdviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<AdviceItem>(Constant.DETAIL_ADVICE_ITEM)
        if (data == null) {
            Toast.makeText(this, "Terjadi kesalahan. Silahkan coba lagi", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            setData(data)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnReply.setOnClickListener {
            val intent = Intent(this, ReplyAdviceActivity::class.java)
            intent.putExtra(Constant.REPLY_ADVICE_ITEM, data)
            startActivity(intent)
            finish()
        }
    }

    private fun setData(data: AdviceItem) {
        if (data.category == Constant.ADVICE_TYPE_SARAN) {
            binding.tvHeader.text = "Detail Saran"
        } else {
            binding.tvHeader.text = "Detail Keluhan"
        }
        binding.txtTitle.text = data.title
        binding.edtDesc.text = data.description
        binding.edtType.text = data.category
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
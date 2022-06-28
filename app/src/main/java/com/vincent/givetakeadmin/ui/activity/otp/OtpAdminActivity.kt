package com.vincent.givetakeadmin.ui.activity.otp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.request.AdminOtpRequest
import com.vincent.givetakeadmin.databinding.ActivityOtpAdminBinding
import com.vincent.givetakeadmin.factory.AdminRepoViewModelFactory
import com.vincent.givetakeadmin.factory.PrefAdminRepoViewModelFactory
import com.vincent.givetakeadmin.preference.UserPreferences
import com.vincent.givetakeadmin.ui.activity.home.MainActivity
import com.vincent.givetakeadmin.utils.Constant
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
class OtpAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpAdminBinding
    private lateinit var viewModel: OtpAdminViewModel
    private var email = ""
    private var key: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)

        email = intent.getStringExtra(Constant.ADMIN_EMAIL) ?: ""
        val factory = PrefAdminRepoViewModelFactory.getInstance(pref)
        viewModel = ViewModelProvider(this, factory)[OtpAdminViewModel::class.java]
        setListener()
        setObserver()
        sendOtp()
    }

    private fun sendOtp() {
        key = Random(System.currentTimeMillis()).nextInt(1000..9999).toString()
        val body = AdminOtpRequest(
            key,
            email
        )
        viewModel.sendOtpAdmin(body)
    }

    private fun setListener() {
        binding.btnVerify.setOnClickListener {
            val inputUser = binding.otp1.text.toString() + binding.otp2.text.toString() + binding.otp3.text.toString() + binding.otp4.text.toString()
            if (inputUser == key) {
                binding.pg.visibility = View.VISIBLE
                lifecycleScope.launch {
                    viewModel.setLogged()
                    delay(2L)
                    binding.pg.visibility = View.VISIBLE
                    val intent = Intent(this@OtpAdminActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            } else {
                Snackbar.make(binding.txtSnackbar, "OTP tidak sesuai ${key} ${inputUser}", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.tvResend.setOnClickListener {
            sendOtp()
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setObserver() {
        viewModel.otpAdminResult.observe(this) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                    binding.backBtn.isEnabled = false
                    binding.btnVerify.isEnabled = false
                }
                is Result.Success -> {
                    showLoading(false)
                    binding.backBtn.isEnabled = true
                    binding.btnVerify.isEnabled = true
                    Snackbar.make(binding.txtSnackbar, it.data?.message!!, Snackbar.LENGTH_LONG).show()
                }
                is Result.Error -> {
                    showLoading(false)
                    binding.backBtn.isEnabled = true
                    binding.btnVerify.isEnabled = true
                    Snackbar.make(binding.txtSnackbar, it.errorMessage, Snackbar.LENGTH_LONG).show()
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
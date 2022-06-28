package com.vincent.givetakeadmin.ui.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.request.AdminLoginRequest
import com.vincent.givetakeadmin.databinding.ActivityLoginBinding
import com.vincent.givetakeadmin.factory.AdminRepoViewModelFactory
import com.vincent.givetakeadmin.ui.activity.home.MainActivity
import com.vincent.givetakeadmin.ui.activity.otp.OtpAdminActivity
import com.vincent.givetakeadmin.utils.Constant
import com.vincent.givetakeadmin.utils.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = AdminRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        setListener()
        setObserver()
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener {
            when {
                binding.edtUsernameLogin.text.toString().isEmpty() -> {
                    binding.edtUsernameLogin.error = "Username wajib diisi"
                }
                binding.edtPasswordLogin.text.toString().isEmpty() -> {
                    binding.edtPasswordLogin.error = "Password wajib diisi"
                }
                else -> {
                    val body = AdminLoginRequest(
                        binding.edtUsernameLogin.text.toString(),
                        binding.edtPasswordLogin.text.toString()
                    )
                    viewModel.loginAdmin(body)
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.loginAdminResult.observe(this) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val intent = Intent(this, OtpAdminActivity::class.java)
                    intent.putExtra(Constant.ADMIN_EMAIL, it.data?.email)
                    startActivity(intent)
                }
                is Result.Error -> {
                    showLoading(false)
                    Snackbar.make(binding.txtErrorLogin, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pgLogin.visibility = View.VISIBLE
        } else {
            binding.pgLogin.visibility = View.GONE
        }
    }
}
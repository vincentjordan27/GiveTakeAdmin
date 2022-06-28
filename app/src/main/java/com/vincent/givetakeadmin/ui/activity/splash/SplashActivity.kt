package com.vincent.givetakeadmin.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.vincent.givetakeadmin.databinding.ActivitySplashBinding
import com.vincent.givetakeadmin.factory.PrefViewModelFactory
import com.vincent.givetakeadmin.preference.UserPreferences
import com.vincent.givetakeadmin.ui.activity.home.MainActivity
import com.vincent.givetakeadmin.ui.activity.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val factory = PrefViewModelFactory.getInstance(pref)
        val viewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]

        viewModel.isUserLogged().observe(this) {
            scope.launch {
                if (it != null) {
                    delay(3000)
                    if (!it) {
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
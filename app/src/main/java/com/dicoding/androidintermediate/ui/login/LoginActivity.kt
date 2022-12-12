package com.dicoding.androidintermediate.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.androidintermediate.R
import com.dicoding.androidintermediate.databinding.ActivityLoginBinding
import com.dicoding.androidintermediate.request.UserSession
import com.dicoding.androidintermediate.ui.main.MainActivity
import com.dicoding.androidintermediate.ui.register.RegisterActivity
import com.dicoding.androidintermediate.util.LoginPreference
import com.dicoding.androidintermediate.util.SHARED_PREFERENCES

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var pref: SharedPreferences
    private lateinit var userPref: LoginPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Login"

        setupPreference()
        setupViewModel()
        action()
        playAnimation()

    }

    private fun setupPreference() {
        pref = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        userPref = LoginPreference(this)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.toastMessage.observe(this) {
            if (it != null) {
                toast(it)
            }
        }
    }


    private fun action() {
        binding.btnLogin.setOnClickListener {
            login()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        when {
            email.isEmpty() -> {
                binding.etEmail.error = getString(R.string.input_email)
            }
            password.isEmpty() -> {
                binding.etPassword.error = getString(R.string.input_password)
            }
            else -> {
                viewModel.loginUser(email, password)
                viewModel.userLogin.observe(this) {
                    binding.progressBar.visibility = View.VISIBLE
                    if (it != null) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(
                            intent,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(this@LoginActivity as Activity)
                                .toBundle()
                        )
                        saveSession(
                            UserSession(
                                it.name,
                                it.token,
                                it.userId,
                                true
                            )
                        )
                    }

                }
            }
        }
    }

    private fun saveSession(user: UserSession) {
        userPref.setUser(user)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title)
            startDelay = 500
        }.start()
    }
}
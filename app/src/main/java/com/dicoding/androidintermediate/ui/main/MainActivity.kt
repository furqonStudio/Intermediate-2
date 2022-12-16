package com.dicoding.androidintermediate.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.androidintermediate.R
import com.dicoding.androidintermediate.databinding.ActivityMainBinding
import com.dicoding.androidintermediate.response.Story
import com.dicoding.androidintermediate.ui.addstory.AddStoryActivity
import com.dicoding.androidintermediate.ui.login.LoginActivity
import com.dicoding.androidintermediate.ui.map.MapActivity
import com.dicoding.androidintermediate.util.LoadingStateAdapter
import com.dicoding.androidintermediate.util.LoginPreference
import com.dicoding.androidintermediate.util.PREF_TOKEN
import com.dicoding.androidintermediate.util.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    private lateinit var viewModel: MainViewModel
private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mLoginPreference: LoginPreference
    private lateinit var adapter: StoryAdapter
    private lateinit var recyclerView: RecyclerView
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Home"

//        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mLoginPreference = LoginPreference(this)

        val prefUtils = LoginPreference(this)
        token = prefUtils.getString(PREF_TOKEN)

        Log.d("Muncul", "Jalan")
        setRecyclerView()
        setupViewModel()
        validate()
        action()
    }

    private fun action() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity as Activity).toBundle()
            )
        }
    }

    private fun validate() {
        if (!mLoginPreference.getUser().isLogin) {
            val login = mLoginPreference.getUser().isLogin
            Log.d("tag", login.toString())
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity as Activity).toBundle()
            )
            finish()
        }
    }

    private fun setupViewModel() {

        Log.d("Muncul", "Keluar")




//        viewModel.getAllStories(mLoginPreference.getUser().token)
        viewModel.listStory.observe(this) {
            if (it != null) {
                adapter.setData(it)
            }
        }

        viewModel.getAllStories(token).observe(this){
            adapter.submitData(lifecycle, it)
        }

        viewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun setRecyclerView() {
        adapter = StoryAdapter()
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_logout -> {
                mLoginPreference.logout()
                Log.d("tag", getString(R.string.token_deleted))
                Log.d("tag", mLoginPreference.getUser().isLogin.toString())
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.menu_setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.menu_maps -> {
                startActivity(Intent(this, MapActivity::class.java))
            }
            else -> {return super.onOptionsItemSelected(item)}
        }
        return true
    }
}
package com.dicoding.androidintermediate.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.androidintermediate.R
import com.dicoding.androidintermediate.databinding.ActivityDetailBinding
import com.dicoding.androidintermediate.util.EXTRA_DESCRIPTION
import com.dicoding.androidintermediate.util.EXTRA_IMAGE
import com.dicoding.androidintermediate.util.STORY_DATE
import com.dicoding.androidintermediate.util.STORY_NAME

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail"
        setView()
    }

    private fun setView() {
        val name = intent.getStringExtra(STORY_NAME)
        val desc = intent.getStringExtra(EXTRA_DESCRIPTION)
        val image = intent.getStringExtra(EXTRA_IMAGE)
        val date = intent.getStringExtra(STORY_DATE)

        binding.apply {
            tvName.text = name
            tvDescription.text = desc
            tvDate.text = getString(R.string.created_at, date?.split("T")?.get(0) ?: "")
            Glide.with(this@DetailActivity)
                .load(image)
                .into(ivImage)
        }
    }
}
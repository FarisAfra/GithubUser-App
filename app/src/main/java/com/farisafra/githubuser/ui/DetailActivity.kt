package com.farisafra.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.farisafra.githubuser.R
import com.farisafra.githubuser.databinding.ActivityDetailBinding
import com.farisafra.githubuser.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val btnBack: ImageButton = findViewById(R.id.btn_back)
        btnBack.setOnClickListener(this)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)

        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this, {
            if (it != null){

                binding.apply {
                    tvNama.text = it.name
                    tvUsername.text = it.login
                    tvFollower.text = "${ it.followers } Followers"
                    tvFollowing.text = "${ it.following } Following"

                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgAvatar)
                }

                binding.progressBar.visibility = View.GONE
            }
        })

        val pagerAdapter = PagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            vpFoll.adapter = pagerAdapter
            tlFoll.setupWithViewPager(vpFoll)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                val moveBack = Intent(this@DetailActivity, MainActivity::class.java)
                startActivity(moveBack)
            }
        }
    }
}
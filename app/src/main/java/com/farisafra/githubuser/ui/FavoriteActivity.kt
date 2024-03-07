package com.farisafra.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farisafra.githubuser.R
import com.farisafra.githubuser.data.db.FavoriteUser
import com.farisafra.githubuser.data.response.User
import com.farisafra.githubuser.data.viewmodel.FavoriteViewModel
import com.farisafra.githubuser.databinding.ActivityFavoriteBinding
import com.farisafra.githubuser.ui.adapter.UserAdapter

class FavoriteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener(this)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    it.putExtra(DetailActivity.EXTRA_HTML, data.htmlUrl)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvFavUser.setHasFixedSize(true)
            rvFavUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavUser.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this, {
            if (it!=null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatarUrl,
                user.htmlUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                val moveBack = Intent(this@FavoriteActivity, MainActivity::class.java)
                startActivity(moveBack)
            }
        }
    }
}
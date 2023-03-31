package ru.netology.myappnetologyhome.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.myappnetologyhome.R
import ru.netology.myappnetologyhome.adapter.PostAdapter
import ru.netology.myappnetologyhome.databinding.ActivityMainBinding
import ru.netology.myappnetologyhome.databinding.CardPostBinding
import ru.netology.myappnetologyhome.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter (
            onLikeClicked = {
                viewModel.likeById(it.id)
            },
            onRepostClicked = {
                viewModel.repostById(it.id)
            },
        )

        viewModel.data.observe(this) { posts ->

            adapter.submitList(posts)

        }

        activityMainBinding.list.adapter = adapter

    }
}
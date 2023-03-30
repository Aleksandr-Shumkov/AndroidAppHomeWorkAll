package ru.netology.myappnetologyhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netology.myappnetologyhome.databinding.ActivityMainBinding
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) { post ->

            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                countViews.text = post.getNumberToString(post.countViews)
                countLike.text = post.getNumberToString(post.likes)
                countRepost.text = post.getNumberToString(post.countRepost)

                if (post.likedByMe) {
                    like.setImageResource(R.drawable.ic_heart_liked_24)
                } else {
                    like.setImageResource(R.drawable.ic_heart_border_24)
                }

            }

            binding.like.setOnClickListener{
                viewModel.like()
            }

            binding.repost.setOnClickListener{
                viewModel.repost()
            }
            
        }

    }
}
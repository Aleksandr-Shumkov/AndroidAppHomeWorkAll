package ru.netology.myappnetologyhome.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.myappnetologyhome.R
import ru.netology.myappnetologyhome.adapter.PostAdapter
import ru.netology.myappnetologyhome.adapter.PostListener
import ru.netology.myappnetologyhome.databinding.ActivityMainBinding
import ru.netology.myappnetologyhome.databinding.CardPostBinding
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.utils.AndroidUtils
import ru.netology.myappnetologyhome.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter (
            object : PostListener{
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removePostById(post.id)
                }

                override fun onEdit(post: Post) {
                   viewModel.edit(post)
                }

                override fun onRepost(post: Post) {
                    viewModel.repostById(post.id)
                }

                override fun onCancel(post: Post) {
                    viewModel.cancelEdit(post)
                }

            }
        )

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }

            activityMainBinding.content.requestFocus()
            activityMainBinding.content.setText(it.content)
        }

        activityMainBinding.save.setOnClickListener {
            with(activityMainBinding.content) {
                val content = text?.toString()
                if (content.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.error_empty_content,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(content)
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        viewModel.data.observe(this) { posts ->

            adapter.submitList(posts)

        }

        activityMainBinding.list.adapter = adapter

    }
}
package ru.netology.myappnetologyhome.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
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

        val newPostContract = registerForActivityResult(NewPostActivity.Contract) {result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        val adapter = PostAdapter (
            object : PostListener{
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removePostById(post.id)
                }

                override fun onEdit(post: Post) {
                    newPostContract.launch(post.content)
                    viewModel.edit(post)
                }

                override fun onRepost(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    val startIntent = Intent.createChooser(intent, getString(R.string.description_post_share))
                    startActivity(startIntent)

                    viewModel.repostById(post.id)
                }

                override fun onCancel() {
                    viewModel.cancelEdit()
                }

                override fun onCreate(post: Post) {
                    viewModel.createPost(post)
                }

                override fun onVideoPost(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))

                    val startIntent = Intent.createChooser(intent, getString(R.string.openLinkVideo))
                    startActivity(startIntent)
                }

            }
        )

        activityMainBinding.create.setOnClickListener {
            newPostContract.launch("")
        }

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        activityMainBinding.list.adapter = adapter

    }

}
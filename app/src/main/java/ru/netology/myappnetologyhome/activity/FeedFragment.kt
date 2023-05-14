package ru.netology.myappnetologyhome.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myappnetologyhome.R
import ru.netology.myappnetologyhome.activity.NewPostFragment.Companion.textArg
import ru.netology.myappnetologyhome.activity.PostDetailsFragment.Companion.postId
import ru.netology.myappnetologyhome.adapter.PostAdapter
import ru.netology.myappnetologyhome.adapter.PostListener
import ru.netology.myappnetologyhome.databinding.FragmentFeedBinding
import ru.netology.myappnetologyhome.databinding.FragmentNewPostBinding
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        super.onCreate(savedInstanceState)
        val activityMainBinding = FragmentFeedBinding.inflate(layoutInflater)

        val adapter = PostAdapter (

            object : PostListener{
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removePostById(post.id)
                }

                override fun onEdit(post: Post) {
                    findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        }
                    )
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

                override fun onDetailsClicked(post: Post) {
                    findNavController().navigate(R.id.action_feedFragment_to_PostDetailsFragment,
                        Bundle().apply {
                            postId = post.id.toString()
                        }
                    )
                }

            }
        )

        activityMainBinding.create.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        activityMainBinding.list.adapter = adapter

        return activityMainBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.cancelEdit()
    }

}
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
import ru.netology.myappnetologyhome.adapter.PostListener
import ru.netology.myappnetologyhome.adapter.PostViewHolder
import ru.netology.myappnetologyhome.databinding.PostDetailsFragmentBinding
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.utils.TextArg
import ru.netology.myappnetologyhome.viewmodel.PostViewModel

class PostDetailsFragment : Fragment() {

    companion object {
        var Bundle.postId: String? by TextArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = PostDetailsFragmentBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by activityViewModels()

        val tmpId = arguments?.postId
        if (!tmpId.isNullOrBlank()) {

            viewModel.data.observe(viewLifecycleOwner) { list ->
                list.find { it.id == tmpId.toLong() }?.let { post ->
                    binding.singlePost.apply { //this - cardPostBinding
                        PostViewHolder(
                            this,
                            object : PostListener {
                                override fun onLike(post: Post) {
                                    viewModel.likeById(post.id)
                                }

                                override fun onRemove(post: Post) {
                                    viewModel.removePostById(post.id)
                                    findNavController().navigateUp()
                                }

                                override fun onEdit(post: Post) {
                                    findNavController().navigate(R.id.action_cardPostDetails_to_newPostFragment,
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

                                }

                            }

                        ).bind(post)
                    }

                }

            }

        }

        return binding.root
    }

}
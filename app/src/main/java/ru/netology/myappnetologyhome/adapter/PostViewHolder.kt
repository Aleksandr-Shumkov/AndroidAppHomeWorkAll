package ru.netology.myappnetologyhome.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.myappnetologyhome.R
import ru.netology.myappnetologyhome.databinding.CardPostBinding
import ru.netology.myappnetologyhome.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener
) : ViewHolder(binding.root) {

    fun bind(post: Post) {
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

        binding.like.setOnClickListener {
            listener.onLike(post)
        }

        binding.repost.setOnClickListener {
            listener.onRepost(post)
        }

        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.post_options)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            listener.onRemove(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEdit(post)
                            true
                        }
                        R.id.create -> {
                            listener.onCreate(post)
                            true
                        }
                        else -> false
                    }

                }
            }.show()
        }

    }
}
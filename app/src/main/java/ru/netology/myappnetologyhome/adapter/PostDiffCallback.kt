package ru.netology.myappnetologyhome.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.netology.myappnetologyhome.dto.Post

class PostDiffCallback: DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem

}
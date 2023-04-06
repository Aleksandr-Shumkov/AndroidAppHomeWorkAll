package ru.netology.myappnetologyhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.myappnetologyhome.databinding.CardPostBinding
import ru.netology.myappnetologyhome.dto.Post

class PostAdapter(
    private val listener: PostListener
): ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return PostViewHolder(
            binding = binding,
            listener = listener,
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
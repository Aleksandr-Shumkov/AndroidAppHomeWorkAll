package ru.netology.myappnetologyhome.adapter

import ru.netology.myappnetologyhome.dto.Post

interface PostListener {

    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onRepost(post: Post)
    fun onCancel()
    fun onCreate(post: Post)
    fun onVideoPost(post: Post)
    fun onDetailsClicked(post: Post)

}
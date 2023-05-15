package ru.netology.myappnetologyhome.dao

import ru.netology.myappnetologyhome.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun repostById(id: Long)
    fun saveDraft(text: String)
    fun getDraft(): String
    fun clearDraft()
}
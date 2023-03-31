package ru.netology.myappnetologyhome.repository

import androidx.lifecycle.LiveData
import ru.netology.myappnetologyhome.dto.Post

interface PostRepository {

    fun getData(): LiveData<List<Post>>

    fun likeById(id: Long)

    fun repostById(id: Long)

    fun viewPostById(id: Long)

}
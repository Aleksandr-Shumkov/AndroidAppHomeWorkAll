package ru.netology.myappnetologyhome.repository

import androidx.lifecycle.LiveData
import ru.netology.myappnetologyhome.dto.Post

interface PostRepository {

    fun getData(): LiveData<Post>

    fun like()

    fun repost()

    fun viewPost()

}
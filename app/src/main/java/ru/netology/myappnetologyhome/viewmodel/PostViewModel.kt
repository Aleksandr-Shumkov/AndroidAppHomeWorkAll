package ru.netology.myappnetologyhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.repository.PostRepository
import ru.netology.myappnetologyhome.repository.PostReposytoryInMemory

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostReposytoryInMemory()

    val data: LiveData<Post> = repository.getData()

    fun like() = repository.like()

    fun repost() = repository.repost()

    fun viewPost() = repository.viewPost()
}
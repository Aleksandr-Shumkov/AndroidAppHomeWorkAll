package ru.netology.myappnetologyhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.repository.PostRepository
import ru.netology.myappnetologyhome.repository.PostReposytoryInMemory

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostReposytoryInMemory()

    val data: LiveData<List<Post>> = repository.getData()

    fun likeById(id: Long) = repository.likeById(id)

    fun repostById(id: Long) = repository.repostById(id)

    fun viewPostById(id: Long) = repository.viewPostById(id)
}
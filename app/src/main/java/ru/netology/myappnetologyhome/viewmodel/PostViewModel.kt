package ru.netology.myappnetologyhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.myappnetologyhome.R
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.repository.PostRepository
import ru.netology.myappnetologyhome.repository.PostReposytoryInMemory

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = ""
)

class PostViewModel: ViewModel() {

    val edited = MutableLiveData(empty)

    private val repository: PostRepository = PostReposytoryInMemory()

    val data: LiveData<List<Post>> = repository.getData()

    fun likeById(id: Long) = repository.likeById(id)

    fun repostById(id: Long) = repository.repostById(id)

    fun viewPostById(id: Long) = repository.viewPostById(id)

    fun removePostById(id: Long) = repository.removePostById(id)

    fun edit(post: Post) {
        edited.value = post
    }

    fun createPost(post: Post) {

    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value?.let {post ->
            if (content != post.content) {
                edited.value = post.copy(
                    content = content
                )
            }
        }
    }

    fun cancelEdit() {
        edited.value = empty
    }
}
package ru.netology.myappnetologyhome.viewmodel

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.myappnetologyhome.db.AppDb
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.repository.PostRepository
import ru.netology.myappnetologyhome.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = ""
)

class PostViewModel(application: Application): AndroidViewModel(application) {

    val edited = MutableLiveData(empty)

    //private val repository: PostRepository = PostRepositoryInMemoryImpl(application)
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )

    val data: LiveData<List<Post>> = repository.getData()

    fun likeById(id: Long) = repository.likeById(id)

    fun repostById(id: Long) = repository.repostById(id)

    fun saveDraft(text: String) = repository.saveDraft(text)

    fun getDraft() = repository.getDraft()

    fun viewPostById(id: Long) = repository.viewPostById(id)

    fun removePostById(id: Long) = repository.removePostById(id)

    fun edit(post: Post) {
        edited.value = post
    }

    fun createPost(post: Post) {}

    fun save() {
        edited.value?.let {post ->
            if (post.id == 0L) {
                repository.clearDraft()
            }
            repository.save(post)
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
        if (edited.value?.id == 0L) {
            val text = edited.value?.content
            if (!text.isNullOrBlank()) {
                repository.saveDraft(text)
            }
        }
    }
}
package ru.netology.myappnetologyhome.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.myappnetologyhome.dto.Post

class PostRepositoryInMemoryImpl(
    private val context: Context,
): PostRepository {

    companion object {
        private const val FILE_NAME = "posts.json"
    }

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var posts: List<Post> = readPosts()
        set(value) {
            field = value
            sync()
        }
    private val data = MutableLiveData(posts)

    init {
        readPosts()
    }
    private fun sync() {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    private fun readPosts(): List<Post> {
        val file = context.filesDir.resolve(FILE_NAME)
        return if (file.exists()) {
            context.openFileInput(FILE_NAME).bufferedReader().use {
                gson.fromJson(it, type)
            }
        } else {
            emptyList()
        }
    }

    override fun getData(): LiveData<List<Post>> = data

    override fun viewPostById(id: Long){

    }

    override fun removePostById(id: Long) {

        posts = posts.filter {
            it.id != id
        }

        data.value = posts
    }

    override fun save(post: Post) {

        if (post.id == 0L) {

            posts = listOf(
                post.copy(
                    id = (posts.firstOrNull()?.id ?: 0L) + 1
                )
            ) + posts
            data.value = posts
            return

        } else {
            posts = posts.map {
                if (it.id == post.id) {
                    post.copy(
                        content = post.content
                    )
                } else {
                    it
                }
            }
            data.value = posts
        }

    }

    override fun repostById(id: Long) {

        posts = posts.map { post ->
            if (id == post.id) {
                post.copy(
                    countRepost = post.countRepost + 1
                )
            } else {
                post
            }
        }

        data.value = posts

    }

    override fun likeById(id: Long) {

        posts = posts.map { post ->
            if (id == post.id) {
                post.copy(
                    likes = if (post.likedByMe) {
                        post.likes - 1
                    } else {
                        post.likes + 1
                    },
                    likedByMe = !post.likedByMe
                )
            } else {
                post
            }
        }

        data.value = posts

    }

}
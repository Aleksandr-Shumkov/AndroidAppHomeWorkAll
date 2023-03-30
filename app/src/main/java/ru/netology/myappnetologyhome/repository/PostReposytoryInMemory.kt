package ru.netology.myappnetologyhome.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.myappnetologyhome.dto.Post

class PostReposytoryInMemory: PostRepository {

    private var post = Post(
        0,
        "Нетология. Университет интернет-профессий будущего",
        "21 мая в 18:36",
        "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
    )
    private val data = MutableLiveData(post)

    override fun getData(): LiveData<Post> = data

    override fun viewPost(){}

    override fun repost() {

        post = post.copy(
            countRepost = post.countRepost + 1
        )

        data.value = post
    }

    override fun like() {
        post = post.copy(
            likes = if (post.likedByMe) {
                post.likes - 1
            } else {
                post.likes + 1
            },
            likedByMe = !post.likedByMe
        )

        data.value = post
    }


}
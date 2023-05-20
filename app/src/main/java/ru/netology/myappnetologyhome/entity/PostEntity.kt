package ru.netology.myappnetologyhome.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.myappnetologyhome.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val countViews: Int = 1,
    val countRepost: Int = 0,
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val video: String? = null
) {
    fun toDto() = Post(id, author, published, content, countViews, countRepost, likedByMe, likes, video)

    companion object {
        fun fromDto(post: Post) = with(post) {
            PostEntity(id, author, published, content, countViews, countRepost, likedByMe, likes, video)
        }
    }
}
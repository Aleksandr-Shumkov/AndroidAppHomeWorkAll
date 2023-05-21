package ru.netology.myappnetologyhome.repository

import androidx.lifecycle.map
import ru.netology.myappnetologyhome.dao.PostDao
import ru.netology.myappnetologyhome.dto.Post
import ru.netology.myappnetologyhome.entity.PostEntity

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getData() = dao.getAll().map { list ->
        list.map { it.toDto() }
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun saveDraft(text: String) {
        dao.saveDraft(text)
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun repostById(id: Long) {
        dao.repostById(id)
    }

    override fun viewPostById(id: Long) {

    }

    override fun getDraft(): String {
        return dao.getDraft()
    }

    override fun clearDraft() {
        return dao.clearDraft()
    }

    override fun removePostById(id: Long) {
        dao.removeById(id)
    }

}

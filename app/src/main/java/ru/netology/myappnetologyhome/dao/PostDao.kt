package ru.netology.myappnetologyhome.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.myappnetologyhome.entity.Draft
import ru.netology.myappnetologyhome.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content =:contentText WHERE id = :id")
    fun updateContentById(id: Long, contentText: String)
    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

    @Query(
        """UPDATE PostEntity SET
               likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = :id """
    )
    fun likeById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id ")
    fun removeById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               countRepost = countRepost + 1
           WHERE id = :id;
        """
    )
    fun repostById(id: Long)


    fun saveDraft(text: String) {

        val count = countDraft().count()

        if (count == 0) {
            insertDraft(text)
        } else {
            updateDraft(text)
        }

    }

    @Query("UPDATE draft SET text = :text WHERE id = 1")
    fun updateDraft(text: String)

    @Query("SELECT * FROM DRAFT")
    fun countDraft(): List<Draft>

    @Query("""INSERT INTO DRAFT (id, text) VALUES (1, :text)""")
    fun insertDraft(text: String = "")

    @Query("SELECT text FROM DRAFT Where id = 1")
    fun getDraft(): String

    @Query("""
           UPDATE draft SET
               text = ""
           WHERE id = 1;
        """)
    fun clearDraft()
}
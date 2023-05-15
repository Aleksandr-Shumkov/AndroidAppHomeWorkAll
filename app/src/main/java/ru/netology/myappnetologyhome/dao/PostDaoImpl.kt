package ru.netology.myappnetologyhome.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getStringOrNull
import ru.netology.myappnetologyhome.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {

    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_COUNTVIEWS} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_COUNTREPOST} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIDEO} TEXT DEFAULT NULL
        );
        """.trimIndent()

        val PCL = """CREATE TABLE IF NOT EXISTS ${DraftColumns.TABLE} (
            ${DraftColumns.COLUMN_ID}  INTEGER PRIMARY KEY,
            ${DraftColumns.COLUMN_TEXT} TEXT
        );
        """.trimIndent()
    }

    object DraftColumns {
        const val TABLE = "draft"
        const val COLUMN_ID = "id"
        const val COLUMN_TEXT = "text"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_TEXT
        )
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        const val COLUMN_LIKES = "likes"
        const val COLUMN_COUNTVIEWS = "countViews"
        const val COLUMN_COUNTREPOST = "countRepost"
        const val COLUMN_VIDEO = "video"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKED_BY_ME,
            COLUMN_LIKES,
            COLUMN_COUNTVIEWS,
            COLUMN_COUNTREPOST,
            COLUMN_VIDEO
        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, "now")
        }
        val id = if (post.id != 0L) {
            db.update(
                PostColumns.TABLE,
                values,
                "${PostColumns.COLUMN_ID} = ?",
                arrayOf(post.id.toString()),
            )
            post.id
        } else {
            db.insert(PostColumns.TABLE, null, values)
        }

        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun repostById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               countRepost = countRepost + 1
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun saveDraft(text: String) {
        //clearDraft(text)
        db.execSQL(
            """
           UPDATE draft SET
               text = ?
           WHERE id = 1;
        """.trimIndent(), arrayOf(text)
        )
    }

    override fun getDraft(): String {
        db.query(
            DraftColumns.TABLE,
            arrayOf(DraftColumns.COLUMN_TEXT),
            "${PostColumns.COLUMN_ID} = 1",
            null,
            null,
            null,
            null,
        ).use {
            if (it.moveToNext()) {
                return checkDraft(it)
            } else {
                return ""
            }

        }
    }

    override fun clearDraft() {
        db.execSQL(
            """
           UPDATE draft SET
               text = ?
           WHERE id = 1;
        """.trimIndent(), arrayOf("")
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    private fun checkDraft(cursor: Cursor): String {
        with(cursor) {
            return getString(getColumnIndexOrThrow(DraftColumns.COLUMN_TEXT)) ?: ""
        }
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
                likes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                countRepost = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_COUNTREPOST)),
                countViews = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_COUNTVIEWS)),
                video = getStringOrNull(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO)),
            )
        }
    }
}
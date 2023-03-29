package ru.netology.myappnetologyhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.myappnetologyhome.databinding.ActivityMainBinding
import ru.netology.myappnetologyhome.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        val post = Post(
            0,
            "Нетология. Университет интернет-профессий будущего",
            "21 мая в 18:36",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        )

        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            countViews.text = post.getNumberToString(post.countViews)
            countLike.text = post.getNumberToString(post.likes)
            countRepost.text = post.getNumberToString(post.countRepost)

            if (post.likedByMe) {
                heart.setImageResource(R.drawable.ic_heart_liked_24)
            }

            heart.setOnClickListener{
                post.likes = if (post.likedByMe) {
                    post.likes - 1
                } else {
                    post.likes + 1
                }

                countLike.text = post.getNumberToString(post.likes)

                post.likedByMe = !post.likedByMe

                heart.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_heart_liked_24
                    } else {
                        R.drawable.ic_heart_border_24
                    }
                )

                println("Clicked heart")
            }

            binding.root.setOnClickListener{
                Log.d("rootClick", "rootClick")
            }

            repost.setOnClickListener{
                Log.d("repostClick", "repostClick")
                post.countRepost += 100
                countRepost.text = post.getNumberToString(post.countRepost)
            }

        }



    }
}
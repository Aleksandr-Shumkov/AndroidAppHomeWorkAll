package ru.netology.myappnetologyhome.dto

data class Post (
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val countViews: Int = 1,
    val countRepost: Int = 0,
    val likedByMe: Boolean = false,
    val likes: Int = 0
) {
    fun getNumberToString(number: Int): String {

        val countStr = number.toString().count() / 3.0
        val remDivisionK = (number / 1_000.0).toString().split(".")[1][0].toString().toInt()
        val remDivisionM = if(number > 1_000_000) (number / 1_000_000.0).toString().split(".")[1][0].toString().toString().toInt() else 0
        //println("number $number")

        return when {
            0 < countStr && countStr <= 1 -> "$number"
            1 < countStr && countStr <= 2 -> "${number / 1_000}${if (remDivisionK > 0 && number < 10_000) ",$remDivisionK" else ""}K"
            else -> "${number / 1_000_000}${if (remDivisionM > 0 && number > 1_000_000) ",$remDivisionM" else ""}M"
        }

    }

}
package ru.netology.myappnetologyhome.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Draft(
    @PrimaryKey()
    val id: Long,
    val text: String,
)
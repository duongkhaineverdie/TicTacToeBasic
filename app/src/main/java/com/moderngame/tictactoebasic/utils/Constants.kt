package com.moderngame.tictactoebasic.utils

import com.moderngame.tictactoebasic.R

object Constants {
    const val TIME_OUT = 10000L
    const val TIME_OUT_MIN = 1000L
    const val TIME_OUT_MAX = 3000L

    fun listImageAvatars(): List<Int> {
        return listOf(
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,
        )
    }
}


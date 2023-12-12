package com.ndiman.classmath.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultQuizBody(
    val username: String,
    val idQuiz: String,
    val answers: List<String?>
): Parcelable

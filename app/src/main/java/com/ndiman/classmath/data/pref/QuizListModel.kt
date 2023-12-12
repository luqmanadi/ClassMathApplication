package com.ndiman.classmath.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizListModel(
    val idGrade: String,
    val idTutorial: String,
    val idQuiz: String,
    val title: String
): Parcelable

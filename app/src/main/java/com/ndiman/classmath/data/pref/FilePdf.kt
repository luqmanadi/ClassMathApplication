package com.ndiman.classmath.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilePdf(
    val idTutorial: String,
    val grade: String,
    val title: String,
    val tutorialImage: String,
    val tutorialFile: String
): Parcelable

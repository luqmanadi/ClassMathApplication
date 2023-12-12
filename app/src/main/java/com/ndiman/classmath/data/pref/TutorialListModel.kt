package com.ndiman.classmath.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TutorialListModel(
    val idGrade: String,
    val idTutorial: String,
    val tutorialImage: String,
    val titleTutorial: String
): Parcelable
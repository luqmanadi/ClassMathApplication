package com.ndiman.classmath.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FavoritMateri")
@Parcelize
data class FavoritMateri(

    @PrimaryKey
    @ColumnInfo("idTutorial")
    var idTutorial:String = "",

    @ColumnInfo("grade")
    var grade: String? = null,

    @ColumnInfo("title")
    var title: String? = null,

    @ColumnInfo("tutorialImage")
    var tutorialImage: String? = null,

    @ColumnInfo("tutorialFile")
    var tutorialFile: String? = null

): Parcelable

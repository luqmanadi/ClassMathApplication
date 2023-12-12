package com.ndiman.classmath.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ndiman.classmath.data.local.entity.FavoritMateri
import com.ndiman.classmath.data.local.entity.HistoriMateri

@Dao
interface ClassMathDao {

    @Query("SELECT * FROM FavoritMateri ORDER BY idTutorial DESC ")
    fun getFavoriteMateri(): LiveData<List<FavoritMateri>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMateriFavorit(favoriteUser: FavoritMateri)

    @Delete
    suspend fun deleteMateriFavorit(favoriteUser: FavoritMateri)

    @Query("SELECT EXISTS (SELECT * FROM favoritmateri WHERE idTutorial = :idTutorial)")
    fun isFavoriteMateri(idTutorial: String): LiveData<Boolean>


    @Query("SELECT * FROM HistoryStudy ORDER BY id DESC ")
    fun getHistoryMateri(): LiveData<List<HistoriMateri>>

    @Insert
    suspend fun insertHistoryMateri(historiMateri: HistoriMateri)
}
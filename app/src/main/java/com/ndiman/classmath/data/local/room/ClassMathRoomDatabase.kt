package com.ndiman.classmath.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ndiman.classmath.data.local.entity.FavoritMateri
import com.ndiman.classmath.data.local.entity.HistoriMateri

@Database(entities = [FavoritMateri::class, HistoriMateri::class], version = 2)
abstract class ClassMathRoomDatabase: RoomDatabase() {

    abstract fun classMathDao(): ClassMathDao

    companion object{
        @Volatile
        private var INSTANCE: ClassMathRoomDatabase? = null


        @JvmStatic
        fun getInstance(context: Context): ClassMathRoomDatabase {
            if (INSTANCE == null){
                synchronized(ClassMathRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ClassMathRoomDatabase::class.java, "ClassMathDatabase")
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE as ClassMathRoomDatabase
        }

        private val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE HistoryStudy ADD COLUMN idTutorial TEXT")
            }

        }
    }
}
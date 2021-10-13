package com.fengyuhe.notebook.model.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fengyuhe.notebook.model.Note

@Database(version = 1, entities = [Note::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    //单例模式
    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }

            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database").fallbackToDestructiveMigration()
                .build().apply {
                    instance = this
                }
        }
    }
}
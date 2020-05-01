package ru.wilddisk.roommvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.wilddisk.roommvvm.data.dao.NoteDao
import ru.wilddisk.roommvvm.data.model.Note

@Database(
    entities = [Note::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "note_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) synchronized(AppDatabase::class.java) {
                if (INSTANCE == null) INSTANCE = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()
            }
            return INSTANCE
        }

        private val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { dbAsyncTask(it) }
            }
        }

        private fun dbAsyncTask(db: AppDatabase) = GlobalScope.launch {
            val noteDao: NoteDao = db.noteDao()
//            noteDao.insert(Note("Title 1", "Description 1", 1))
//            noteDao.insert(Note("Title 2", "Description 2", 2))
//            noteDao.insert(Note("Title 3", "Description 3", 3))
        }
    }
}
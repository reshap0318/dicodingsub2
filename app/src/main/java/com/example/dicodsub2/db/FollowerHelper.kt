package com.example.dicodsub2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class FollowerHelper(context : Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.UserColumn.TABLE_NAME_FOLLOWER
        private var INSTANCE: FollowerHelper? = null

        fun getInstance(context: Context): FollowerHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FollowerHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.UserColumn._ID} ASC",
            null)
    }

    fun queryByFollowerUsername(username: String): Cursor {
        return database.query(DATABASE_TABLE, null, "${DatabaseContract.UserColumn.FOLLOWER_USERNAME} = ?", arrayOf(username), null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteByFollowerUsername(username: String): Int {
        return database.delete(DATABASE_TABLE, "${DatabaseContract.UserColumn.FOLLOWER_USERNAME} = '$username'", null)
    }
}
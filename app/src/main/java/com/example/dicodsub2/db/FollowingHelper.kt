package com.example.dicodsub2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class FollowingHelper(context : Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.UserColumn.TABLE_NAME_FOLLOWING
        private var INSTANCE: FollowingHelper? = null

        fun getInstance(context: Context): FollowingHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FollowingHelper(context)
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

    fun queryByFollowingUsername(username: String): Cursor {
        return database.query(DATABASE_TABLE, null, "${DatabaseContract.UserColumn.FOLLOWING_USERNAME} = ?", arrayOf(username), null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteByFollowingUsername(username: String): Int {
        return database.delete(DATABASE_TABLE, "${DatabaseContract.UserColumn.FOLLOWING_USERNAME} = '$username'", null)
    }
}
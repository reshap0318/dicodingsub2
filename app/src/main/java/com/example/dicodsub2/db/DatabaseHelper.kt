package com.example.dicodsub2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbLocal"
        private const val DATABASE_VERSION = 3
        private val SQL_CREATE_TABLE_USER = "CREATE TABLE ${DatabaseContract.UserColumn.TABLE_NAME_USER}" +
                " (${DatabaseContract.UserColumn._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.UserColumn.USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.AVATAR} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.REPOSITORY} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumn.FOLLOWER} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumn.FOLLOWING} INTEGER NOT NULL)"

        private val SQL_CREATE_TABLE_FOLLOWER = "CREATE TABLE ${DatabaseContract.UserColumn.TABLE_NAME_FOLLOWER}" +
                " (${DatabaseContract.UserColumn._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.UserColumn.USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.AVATAR} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.REPOSITORY} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumn.FOLLOWER} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumn.FOLLOWING} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumn.FOLLOWER_USERNAME} INTEGER NOT NULL)"

        private val SQL_CREATE_TABLE_FOLLOWING = "CREATE TABLE ${DatabaseContract.UserColumn.TABLE_NAME_FOLLOWING}" +
                " (${DatabaseContract.UserColumn._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.UserColumn.USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.AVATAR} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumn.REPOSITORY} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumn.FOLLOWER} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumn.FOLLOWING} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumn.FOLLOWING_USERNAME} INTEGER NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
        db.execSQL(SQL_CREATE_TABLE_FOLLOWER)
        db.execSQL(SQL_CREATE_TABLE_FOLLOWING)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.UserColumn.TABLE_NAME_USER}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.UserColumn.TABLE_NAME_FOLLOWER}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.UserColumn.TABLE_NAME_FOLLOWING}")
        onCreate(db)
    }
}
package com.example.appcustomer

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserLite> {
        val userList = ArrayList<UserLite>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumn._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumn.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumn.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumn.AVATAR))
                val repository = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumn.REPOSITORY))
                val follower = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumn.FOLLOWER))
                val following = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumn.FOLLOWING))
                userList.add(UserLite(id, username, name, avatar, repository, follower, following))
            }
        }
        return userList
    }

    fun mapCursorToObject(notesCursor: Cursor?): UserLite {
        var user = UserLite()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumn._ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumn.USERNAME))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumn.NAME))
            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumn.AVATAR))
            val repository = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumn.REPOSITORY))
            val follower = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumn.FOLLOWER))
            val following = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumn.FOLLOWING))
            user = UserLite(id, username, name, avatar, repository, follower, following)
        }
        return user
    }
}
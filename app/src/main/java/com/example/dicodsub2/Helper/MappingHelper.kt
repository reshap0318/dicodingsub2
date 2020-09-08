package com.example.dicodsub2.Helper

import android.database.Cursor
import com.example.dicodsub2.Model.UserLite
import com.example.dicodsub2.db.DatabaseContract

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
}
package com.example.dicodsub2.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class UserColumn : BaseColumns {
        companion object {
            const val TABLE_NAME_USER = "user"
            const val TABLE_NAME_FOLLOWER = "follower"
            const val TABLE_NAME_FOLLOWING = "following"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR = "avatar"
            const val REPOSITORY = "repository"
            const val FOLLOWER = "follower"
            const val FOLLOWING = "following"
            const val FOLLOWER_USERNAME = "follower_username"
            const val FOLLOWING_USERNAME = "following_username"
        }
    }

}
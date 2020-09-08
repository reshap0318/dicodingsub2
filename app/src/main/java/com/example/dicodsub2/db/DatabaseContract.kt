package com.example.dicodsub2.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.reshap0318.dicodsub3"
    const val SCHEME = "content"

    class UserColumn : BaseColumns {
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

            val CONTENT_URI_USER: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME_USER)
                .build()

            val CONTENT_URI_FOLLOWER: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME_FOLLOWER)
                .build()

            val CONTENT_URI_FOLLOWING: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME_FOLLOWING)
                .build()
        }
    }

}
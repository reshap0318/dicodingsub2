package com.example.dicodsub2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.dicodsub2.db.DatabaseContract
import com.example.dicodsub2.db.DatabaseContract.AUTHORITY
import com.example.dicodsub2.db.DatabaseContract.UserColumn.Companion.CONTENT_URI_FOLLOWER
import com.example.dicodsub2.db.DatabaseContract.UserColumn.Companion.CONTENT_URI_FOLLOWING
import com.example.dicodsub2.db.DatabaseContract.UserColumn.Companion.CONTENT_URI_USER
import com.example.dicodsub2.db.FollowerHelper
import com.example.dicodsub2.db.FollowingHelper
import com.example.dicodsub2.db.UserHelper

class UserProvider : ContentProvider() {

    companion object {
        private const val USERS = 1
        private const val USER = 2
        private const val FOLLOWERS = 3
        private const val FOLLOWER = 4
        private const val FOLLOWINGS = 5
        private const val FOLLOWING = 6
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userHelper: UserHelper
        private lateinit var followerHelper: FollowerHelper
        private lateinit var followingHelper: FollowingHelper
        init {
            uriMatcher.addURI(AUTHORITY, DatabaseContract.UserColumn.TABLE_NAME_USER, USERS)
            uriMatcher.addURI(AUTHORITY, "${DatabaseContract.UserColumn.TABLE_NAME_USER}/*", USER)
            uriMatcher.addURI(AUTHORITY, DatabaseContract.UserColumn.TABLE_NAME_FOLLOWER, FOLLOWERS)
            uriMatcher.addURI(AUTHORITY, "${DatabaseContract.UserColumn.TABLE_NAME_FOLLOWER}/*", FOLLOWER)
            uriMatcher.addURI(AUTHORITY, DatabaseContract.UserColumn.TABLE_NAME_FOLLOWING, FOLLOWINGS)
            uriMatcher.addURI(AUTHORITY, "${DatabaseContract.UserColumn.TABLE_NAME_FOLLOWING}/*", FOLLOWING)
        }
    }

    override fun onCreate(): Boolean {
        userHelper = UserHelper.getInstance(context as Context)
        userHelper.open()
        followerHelper = FollowerHelper.getInstance(context as Context)
        followerHelper.open()
        followingHelper = FollowingHelper.getInstance(context as Context)
        followingHelper.open()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            USERS -> cursor = userHelper.queryAll()
            USER -> cursor = userHelper.queryByUsername(uri.lastPathSegment.toString())
            FOLLOWERS -> cursor = followerHelper.queryAll()
            FOLLOWER -> cursor = followerHelper.queryByFollowerUsername(uri.lastPathSegment.toString())
            FOLLOWINGS -> cursor = followingHelper.queryAll()
            FOLLOWING -> cursor = followingHelper.queryByFollowingUsername(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var myUri : Uri = CONTENT_URI_USER
        var added: Long = 0

        when (uriMatcher.match(uri)) {
            USERS -> {
                added = userHelper.insert(values)
                myUri = CONTENT_URI_USER
            }
            FOLLOWERS -> {
                added = followerHelper.insert(values)
                myUri = CONTENT_URI_FOLLOWER
            }
            FOLLOWINGS -> {
                added = followingHelper.insert(values)
                myUri = CONTENT_URI_FOLLOWING
            }
            else -> 0
        }

        context?.contentResolver?.notifyChange(myUri, null)

        return Uri.parse("$myUri/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var myUri : Uri = CONTENT_URI_USER
        var deleted: Int = 0
        when (uriMatcher.match(uri)) {
            USER -> {
                deleted = userHelper.deleteByUsername(uri.lastPathSegment.toString())
                myUri = CONTENT_URI_USER
            }
            FOLLOWER -> {
                deleted = followerHelper.deleteByFollowerUsername(uri.lastPathSegment.toString())
                myUri = CONTENT_URI_FOLLOWER
            }
            FOLLOWING -> {
                deleted = followingHelper.deleteByFollowingUsername(uri.lastPathSegment.toString())
                myUri = CONTENT_URI_FOLLOWING
            }
            else -> 0
        }

        context?.contentResolver?.notifyChange(myUri, null)

        return deleted
    }

}

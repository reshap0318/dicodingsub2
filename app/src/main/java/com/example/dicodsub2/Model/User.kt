package com.example.dicodsub2.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val username: String,
    val name: String,
    val avatar: String,
    val compony: String,
    val location: String,
    val repository: Int,
    val follower: Int,
    val following: Int
) : Parcelable
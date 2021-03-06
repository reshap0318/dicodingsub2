package com.example.dicodsub2.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val username: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val compony: String? = null,
    val location: String? = null,
    val repository: Int = 0,
    val follower: Int = 0,
    val following: Int = 0
) : Parcelable
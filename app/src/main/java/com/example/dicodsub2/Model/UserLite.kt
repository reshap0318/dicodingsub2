package com.example.dicodsub2.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserLite(
    val id: Int = 0,
    val username: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val repository: Int = 0,
    val follower: Int = 0,
    val following: Int = 0
) : Parcelable
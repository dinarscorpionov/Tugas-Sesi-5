package com.example.tugas_sesi_5

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("login") var username: String = "",
    @SerializedName("avatar_url") var avatarUrl: String? = "",
    val name: String? = "",
    val email: String? = "",
    val company: String? = ""
)

package com.example.tugas_sesi_5

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("users")
    fun getData(): Call<List<Users>>

    @GET("users/{username}")
    fun getDetailData(@Path("username") username : String) : Call<Users>

    @GET("users/{username}/following")
    fun getFollowingData(@Path("username") username : String) : Call<List<Users>>

    @GET("users/{username}/followers")
    fun getFollowersData(@Path("username") username : String) : Call<List<Users>>
}
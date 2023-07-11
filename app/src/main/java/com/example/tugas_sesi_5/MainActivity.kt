package com.example.tugas_sesi_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Users>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUserData()
    }

    private fun getUserData() {
        var adapter = UserAdapter()
        userArrayList = arrayListOf<Users>()

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        userRecyclerView = findViewById(R.id.listMainUsers)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userRecyclerView.adapter = adapter

        retrofitData.enqueue(object: Callback<List<Users>?> {
            override fun onResponse(call: Call<List<Users>?>, response: Response<List<Users>?>) {
                val responseBody = response.body()!!

                for (user in responseBody) {
                    adapter.addedListOfUsers(responseBody)
                    userArrayList.add(user)
                }
            }

            override fun onFailure(call: Call<List<Users>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })

        adapter.setOnItemClickListener(object : UserAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("MainActivity", position.toString())
                val retrofitBuilder = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()
                    .create(ApiInterface::class.java)

                val retrofitDetailData = retrofitBuilder.getDetailData(userArrayList[position].username)

                retrofitDetailData.enqueue(object: Callback<Users> {
                    override fun onResponse(call: Call<Users>, response: Response<Users>) {
                        val responseBody = response.body()!!

                        val intent = Intent(this@MainActivity, UserActivity::class.java)
                        intent.putExtra("imageDetailFoto", responseBody.avatarUrl)
                        intent.putExtra("textDetailUsername", "Username: " + responseBody.username)
                        intent.putExtra("textDetailName", "Name: " + responseBody.name)
                        intent.putExtra("textDetailEmail", "Email: " + responseBody.email)
                        intent.putExtra("textDetailCompany", "Company: " + responseBody.company)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<Users>, t: Throwable) {
                        Log.d("MainActivity", "onFailure: " + t.message)
                    }
                })

            }
        })
    }
}
package com.example.tugas_sesi_5.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas_sesi_5.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FollowerFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Users>
    lateinit var imageId : Array<Int>
    lateinit var textUsername : Array<String>
    lateinit var textName : Array<String>
    lateinit var textEmail : Array<String>
    lateinit var textCompany : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var usernameView = activity?.findViewById<TextView>(R.id.textDetailUsername)
        val username = usernameView?.text
        getFollowerData(view, username.toString().replace("Username: ", ""))
    }

    private fun getFollowerData(view: View, username: String) {
        var adapter = UserAdapter()
        userArrayList = arrayListOf<Users>()

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getFollowersData(username)

        val layoutManager = LinearLayoutManager(context)
        userRecyclerView = view.findViewById(R.id.listFollower)
        userRecyclerView.layoutManager = layoutManager
        userRecyclerView.setHasFixedSize(true)
        userRecyclerView.adapter = adapter

        retrofitData.enqueue(object: Callback<List<Users>?> {
            override fun onResponse(call: Call<List<Users>?>, response: Response<List<Users>?>) {
                Log.d("MainActivity", "onFailure: " + response)
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
            }
        })
    }
}
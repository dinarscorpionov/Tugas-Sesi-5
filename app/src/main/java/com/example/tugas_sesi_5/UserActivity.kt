package com.example.tugas_sesi_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.tugas_sesi_5.fragments.FollowerFragment
import com.example.tugas_sesi_5.fragments.FollowingFragment
import com.example.tugas_sesi_5.fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val imageDetailFoto : ImageView = findViewById(R.id.imageDetailFoto)
        val textDetailUsername : TextView = findViewById(R.id.textDetailUsername)
        val textDetailName : TextView = findViewById(R.id.textDetailName)
        val textDetailEmail : TextView = findViewById(R.id.textDetailEmail)
        val textDetailCompany : TextView = findViewById(R.id.textDetailCompany)

        val bundle : Bundle?= intent.extras
        val detailFoto = bundle!!.getString("imageDetailFoto")
        val detailUsername = bundle.getString("textDetailUsername")
        val detailName = bundle.getString("textDetailName")
        val detailEmail = bundle.getString("textDetailEmail")
        val detailCompany = bundle.getString("textDetailCompany")

        Glide
            .with(imageDetailFoto)
            .load(detailFoto)
            .into(imageDetailFoto)

        textDetailUsername.text = detailUsername
        textDetailName.text = detailName
        textDetailEmail.text = detailEmail
        textDetailCompany.text = detailCompany

        setUpTabs()
    }

    private fun setUpTabs() {
        val viewPager : ViewPager = findViewById(R.id.viewPagerUser)
        val tabLayoutUser : TabLayout = findViewById(R.id.tabLayoutUser)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FollowerFragment(), "Follower")
        adapter.addFragment(FollowingFragment(), "Following")
        viewPager.adapter = adapter
        tabLayoutUser.setupWithViewPager(viewPager)
    }
}
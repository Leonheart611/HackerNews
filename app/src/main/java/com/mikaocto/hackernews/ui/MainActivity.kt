package com.mikaocto.hackernews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mikaocto.hackernews.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object{
        const val TITLE_KEY = "TITLE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
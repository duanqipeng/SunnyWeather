package com.example.sunnyweather

import android.os.Bundle
import com.example.sunnyweather.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    override val bind by getBind<ActivityMainBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}
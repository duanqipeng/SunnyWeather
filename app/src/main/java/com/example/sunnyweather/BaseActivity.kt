package com.example.sunnyweather

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity : AppCompatActivity() {
    abstract val bind: ViewBinding?

    inline fun <reified T> getBind(): Lazy<T> where T : ViewBinding {
        return lazy {
            val classz = T::class.java
            val method = classz.getMethod("inflate", LayoutInflater::class.java)
            method.invoke(null, layoutInflater) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind?.root?.apply {
            setContentView(this)
        }
    }

}
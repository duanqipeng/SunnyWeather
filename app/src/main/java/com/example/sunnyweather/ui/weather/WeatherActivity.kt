package com.example.sunnyweather.ui.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweather.BaseActivity
import com.example.sunnyweather.R
import com.example.sunnyweather.databinding.ActivityMainBinding
import com.example.sunnyweather.databinding.ActivityWeatherBinding
import com.example.sunnyweather.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : BaseActivity() {
    override val bind by getBind<ActivityWeatherBinding>()
    val viewmodel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewmodel.locationLat.isEmpty()) {
            viewmodel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewmodel.locationLng.isEmpty()) {
            viewmodel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewmodel.placeName.isEmpty()) {
            viewmodel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewmodel.weatherLiveData.observe(this) {
            val weather = it.getOrNull()
            if (weather != null) {
                refreshUI(weather)
            } else {
                Toast.makeText(this, "无法获取天气", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
            bind.swipeRefresh.isRefreshing = false
        }
        bind.swipeRefresh.setColorSchemeResources(R.color.black)
        refreshWeather()
        bind.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        bind.now.navBtn.setOnClickListener {
            bind.drawerLayout.openDrawer(GravityCompat.START)
        }

        bind.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })
    }

    fun refreshWeather() {
        viewmodel.refreshWeather(viewmodel.locationLng, viewmodel.locationLat)
        bind.swipeRefresh.isRefreshing = true
    }

    fun refreshUI(weather: Weather) {
        bind.now.placeName.text = viewmodel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now.xml布局中数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        bind.now.currentTemp.text = currentTempText
        bind.now.currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.aqi.toInt()}"
        bind.now.currentAQI.text = currentPM25Text
        bind.now.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast.xml布局中的数据
        bind.forecast.forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view =
                LayoutInflater.from(this)
                    .inflate(R.layout.forecast_item, bind.forecast.forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            bind.forecast.forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        bind.lifeIndex.coldRiskText.text = daily.coldRisk[0].desc
        bind.lifeIndex.dressingText.text = daily.dressing[0].desc
        bind.lifeIndex.ultravioletText.text = daily.ultraviolet[0].desc
        bind.lifeIndex.carWashingText.text = daily.carWashing[0].desc
        bind.weatherLayout.visibility = View.VISIBLE
    }
}
package com.example.sunnyweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.example.sunnyweather.ui.weather.WeatherActivity
import com.example.sunnyweather.logic.model.PlaceResponse
import kotlinx.android.synthetic.main.activity_weather.*

class PlaceAdapter(
    private val fragment: PlaceFragment,
    private val placeList: List<PlaceResponse.Place>
) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName = view.findViewById<TextView>(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val viewHolder = PlaceViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val activity = fragment.activity
            val position = viewHolder.absoluteAdapterPosition
            val place = placeList[position]
            if (activity is WeatherActivity) {
                activity.drawerLayout.closeDrawers()
                activity.viewmodel.locationLng = place.location.lng
                activity.viewmodel.locationLat = place.location.lat
                activity.viewmodel.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                fragment.startActivity(intent)
                activity?.finish()
            }
            fragment.viewModel.savePlace(place)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.placeName.text = placeList[position].name
        holder.placeAddress.text = placeList[position].address
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}
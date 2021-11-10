package com.rezwan.weatherapptest.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.rezwan.weatherapptest.R
import com.rezwan.weatherapptest.ui.WeatherDetailsActivity
import com.rezwan.weatherapptest.utility.Util
import com.rezwan.weatherapptest.model.DataList

class WeatherResultListAdepter(activity: Activity) :
    RecyclerView.Adapter<WeatherResultListAdepter.WeatherResultListViewHolder?>() {
    private var weatherResultLists: List<DataList<Any?>>? = null
    private val activity: Activity
    private val typeface: Typeface
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): WeatherResultListViewHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_weather_result_list, parent, false)
        return WeatherResultListViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: WeatherResultListViewHolder, position: Int) {
        val dataList: com.rezwan.weatherapptest.model.DataList<Any?> = weatherResultLists!![position]
        holder.placeNameText!!.setTypeface(typeface)
        holder.weatherStatusText!!.setTypeface(typeface)
        holder.weatherTemperatureText!!.setTypeface(typeface)
        val placeName: String? = dataList.name
        val weatherDescription: String? = dataList.weather!!.get(0)!!.description
        val currentTemperature: String? =
            java.lang.String.valueOf(dataList.main!!.temp) + activity.getResources()
                .getString(R.string.celsius)
        val maxTemp = "Max Temp: " + java.lang.String.valueOf(
            dataList!!.main!!.tempMax
        ) + activity.getResources().getString(R.string.celsius)
        val minTemp = "Min Temp: " + java.lang.String.valueOf(
            dataList!!.main!!.tempMin
        ) + activity.getResources().getString(R.string.celsius)
        val windSpeed = "Wind Speed: " + java.lang.String.valueOf(dataList.wind!!.speed)
        val humidity = "Humidity: " + java.lang.String.valueOf(dataList.main!!.humidity)
        val lat: Double? = dataList!!.coord!!.lat
        val lon: Double? = dataList!!.coord!!.lon
        holder.placeNameText!!.setText(placeName)
        holder.weatherStatusText!!.setText(weatherDescription)
        holder.weatherTemperatureText!!.setText(currentTemperature)
        holder.singleRow.setOnClickListener {
            activity.startActivity(
                Intent(activity, WeatherDetailsActivity::class.java)
                    .putExtra(Util.LOCATION_NAME, placeName)
                    .putExtra(Util.WEATHER_DESCRIPTION, weatherDescription)
                    .putExtra(Util.CURRENT_TEMP, currentTemperature)
                    .putExtra(Util.MAX_TEMP, maxTemp)
                    .putExtra(Util.MIN_TEMP, minTemp)
                    .putExtra(Util.WIND_SPEED, windSpeed)
                    .putExtra(Util.HUMIDITY, humidity)
                    .putExtra(Util.LATITUDE, lat)
                    .putExtra(Util.LONGITUDE, lon)
            )
        }
    }

    override fun getItemCount(): Int {
        return weatherResultLists!!.size
    }

    fun setItems(results: List<DataList<Any?>>?) {
        weatherResultLists = results!!
    }

    inner class WeatherResultListViewHolder(var singleRow: View) : RecyclerView.ViewHolder(
        singleRow
    ) {
        @BindView(R.id.list_weather_result_list_placeName)
        var placeNameText: TextView? = null

        @BindView(R.id.list_weather_result_list_weather_status)
        var weatherStatusText: TextView? = null

        @BindView(R.id.list_weather_result_list_weather_temperature)
        var weatherTemperatureText: TextView? = null

        init {
            ButterKnife.bind(this, singleRow)
        }
    }

    init {
        this.activity = activity
        typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/roboto_regular.ttf")
    }
}
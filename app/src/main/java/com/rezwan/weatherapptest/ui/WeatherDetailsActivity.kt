package com.rezwan.weatherapptest.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rezwan.weatherapptest.R
import com.rezwan.weatherapptest.utility.Util

class WeatherDetailsActivity : AppCompatActivity(), OnMapReadyCallback {
    //------------xml instance------------------
    @BindView(R.id.weather_map_details_activity_locationName)
    var locationNameET: TextView? = null

    @BindView(R.id.weather_map_details_activity_weatherDescription)
    var weatherDescriptionET: TextView? = null

    @BindView(R.id.weather_map_details_activity_humidity)
    var humidityET: TextView? = null

    @BindView(R.id.weather_map_details_activity_windSpeed)
    var windSpeedET: TextView? = null

    @BindView(R.id.weather_map_details_activity_maxTemp)
    var maxTempET: TextView? = null

    @BindView(R.id.weather_map_details_activity_minTemp)
    var minTempET: TextView? = null

    @BindView(R.id.weather_map_details_activity_currentTemp)
    var currentTempET: TextView? = null

    //--------------class instance----------------------
    private var mGoogleMap: GoogleMap? = null
    private var locationName: String? = null
    private var latitude = 0.0
    private var longitude = 0.0
    override protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_map_details)
        ButterKnife.bind(this)

        //---------action bar---------------------
        setUpSupportActionBar()

        //--------------set up google map-------------------
        initializeGoogleMap()
    }

    private fun setUpSupportActionBar() {
        val toolbar: Toolbar = findViewById(R.id.weather_map_details_activity_toolBar)
        setSupportActionBar(toolbar)
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initializeGoogleMap() {
        val mapFragment: SupportMapFragment =
            getSupportFragmentManager().findFragmentById(R.id.weather_map_details_activity_google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        getExtraDataPassFromMainActivity()
    }

    private fun getExtraDataPassFromMainActivity() {
        val extras: Bundle? = intent!!.extras
        if (extras != null) {
            val weatherDescription: String? = extras.getString(Util.WEATHER_DESCRIPTION)
            val maxTemp: String? = extras.getString(Util.MAX_TEMP)
            val minTemp: String? = extras.getString(Util.MIN_TEMP)
            val humidity: String? = extras.getString(Util.HUMIDITY)
            val windSpeed: String? = extras.getString(Util.WIND_SPEED)
            val currentTemp: String? = extras.getString(Util.CURRENT_TEMP)
            locationName = extras.getString(Util.LOCATION_NAME)
            latitude = extras.getDouble(Util.LATITUDE)
            longitude = extras.getDouble(Util.LONGITUDE)
            locationNameET!!.setText(locationName)
            weatherDescriptionET!!.setText(weatherDescription)
            currentTempET!!.setText(currentTemp)
            maxTempET!!.setText(maxTemp)
            minTempET!!.setText(minTemp)
            humidityET!!.setText(humidity)
            windSpeedET!!.setText(windSpeed)
            setMarkerOnGoogleMapPosition()
        }
    }

    private fun setMarkerOnGoogleMapPosition() {
        val locationLatLng = LatLng(latitude, longitude)
        mGoogleMap!!.addMarker(MarkerOptions().position(locationLatLng).title(locationName))
        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 10f))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            super.onBackPressed()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
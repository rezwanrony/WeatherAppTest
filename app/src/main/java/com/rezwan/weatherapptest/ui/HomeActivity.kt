package com.rezwan.weatherapptest.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.rezwan.weatherapptest.R
import com.rezwan.weatherapptest.adapter.WeatherResultListAdepter
import com.rezwan.weatherapptest.application.WeatherApplication
import com.rezwan.weatherapptest.components.MainActivityComponent
import com.rezwan.weatherapptest.utility.Util
import com.rezwan.weatherapptest.interfaces.WeatherApi
import com.rezwan.weatherapptest.model.CurrentWeather
import com.rezwan.weatherapptest.model.WeatherResultList
import com.rezwan.weatherapptest.module.MainActivityModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {
    //-------------xml instance-------------------
    @BindView(R.id.main_activity_recyclerView)
    var weatherResultListRecyclerView: RecyclerView? = null

    //--------------class instance---------------
    private var googleApiClient: GoogleApiClient? = null
    var result: PendingResult<LocationSettingsResult>? = null
    private var mLocationRequest: LocationRequest? = null

    @Inject
    var weatherApi: WeatherApi? = null

    @Inject
    var adepter: WeatherResultListAdepter? = null
    override protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)


        //---------check runtime permission------------------
        checkRuntimePermission()
        val mainActivityComponent: MainActivityComponent = DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(this))
            .weatherApiComponent(WeatherApplication.get(this).getWeatherApiComponentApplication())
            .build()
        mainActivityComponent.injectMainActivity(this)


        //---------action bar---------------------
        setUpSupportActionBar()
        configureRecyclerVIew()
        //--------------fire up weather data--------------
        populateWeathers()
    }

    private fun setUpSupportActionBar() {
        val toolbar: Toolbar = findViewById(R.id.main_activity_toolbar)
        setSupportActionBar(toolbar)
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        }
    }

    private fun populateWeathers() {
        val call: Call<WeatherResultList?>? = weatherApi!!.getWeatherData(
            23.68,
            90.35,
            50,
            "e384f9ac095b2109c751d95296f8ea76",
            "metric"
        )
        call!!.enqueue(object : Callback<WeatherResultList?> {
            override fun onResponse(
                @NonNull call: Call<WeatherResultList?>,
                @NonNull response: Response<WeatherResultList?>
            ) {
                if (response.isSuccessful()) {
                    adepter!!.setItems(response.body()!!.dataList)
                    weatherResultListRecyclerView!!.setAdapter(adepter)
                    adepter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(@NonNull call: Call<WeatherResultList?>, @NonNull t: Throwable) {}
        })
    }

    private fun configureRecyclerVIew() {
        weatherResultListRecyclerView!!.setLayoutManager(LinearLayoutManager(this))
    }

    private fun checkRuntimePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Util.LOCATION_REQUEST_CODE
            )
        } else {
            buildGoogleApiClient()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String?>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
        when (requestCode) {
            Util.LOCATION_REQUEST_CODE -> {

                //----------phone state permission result---------------
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient()
                } else {
                    checkRuntimePermission()
                }
            }
        }
    }

    private fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        googleApiClient!!.connect()
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(@Nullable bundle: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.setInterval(1000)
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        val builder: LocationSettingsRequest.Builder =
            LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)
        builder.setAlwaysShow(true)
        checkLocationSetting(builder)
        LocationServices.FusedLocationApi.requestLocationUpdates(
            googleApiClient,
            mLocationRequest,
            this
        )
    }

    override fun onConnectionSuspended(i: Int) {}
    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {}
    override fun onLocationChanged(location: Location) {
        if (location != null) {
            populateCurrentWeatherData(location.latitude, location.longitude)
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
            googleApiClient!!.disconnect()
        }
    }

    fun checkLocationSetting(builder: LocationSettingsRequest.Builder) {
        result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result!!.setResultCallback { result ->
            val status: Status = result.getStatus()
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                    status.startResolutionForResult(
                        this@HomeActivity,
                        Util.LOCATION_SETTING_REQUEST_CODE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }
        }
    }

    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Util.LOCATION_SETTING_REQUEST_CODE -> when (resultCode) {
                Activity.RESULT_OK -> {
                }
                Activity.RESULT_CANCELED -> Toast.makeText(
                    getApplicationContext(),
                    "Please enable permission for getting notification",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
            }
        }
    }

    private fun populateCurrentWeatherData(lat: Double, lng: Double) {
        val call: Call<CurrentWeather?>? = weatherApi!!.getCurrentWeatherData(
            lat,
            lng,
            50,
            "e384f9ac095b2109c751d95296f8ea76",
            "metric"
        )
        call!!.enqueue(object : Callback<CurrentWeather?> {
            override fun onResponse(
                @NonNull call: Call<CurrentWeather?>,
                @NonNull response: Response<CurrentWeather?>
            ) {
                if (response.isSuccessful()) {
                    val temp = "Current Temperature: " + response.body()!!.main!!.temp + getResources().getString(R.string.celsius)
                    sendCustomNotifications(temp)
                }
            }

            override fun onFailure(@NonNull call: Call<CurrentWeather?>, @NonNull t: Throwable) {}
        })
    }

    private fun sendCustomNotifications(temp: String) {
        val remoteViews = RemoteViews(getPackageName(), R.layout.custom_notification)
        remoteViews.setImageViewResource(R.id.left_image, R.mipmap.ic_launcher_round)
        remoteViews.setTextViewText(R.id.title, getResources().getString(R.string.app_name))
        remoteViews.setTextViewText(R.id.temperature, temp)
        val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this@HomeActivity, "tempNotification")
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setCustomContentView(remoteViews)
            .setSound(soundUri)
            .setChannelId("tempNotification")
        assert(notificationManager != null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance: Int = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel("tempNotification", "Weather App", importance)
            mChannel.setDescription("notification")
            mChannel.enableLights(true)
            mChannel.setLightColor(
                ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.colorPrimary
                )
            )
            notificationManager!!.createNotificationChannel(mChannel)
        }
        builder.getNotification().flags =
            builder.getNotification().flags or Notification.FLAG_AUTO_CANCEL
        notificationManager!!.notify(0, builder.build())
    }
}
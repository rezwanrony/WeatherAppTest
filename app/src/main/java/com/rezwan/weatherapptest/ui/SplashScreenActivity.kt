package com.rezwan.weatherapptest.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.rezwan.weatherapptest.R

class SplashScreenActivity : AppCompatActivity() {
    @BindView(R.id.splash_activity_message_text)
    var messageText: TextView? = null
    override protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        ButterKnife.bind(this)

        //----------show splash screen message with Roboto font------------------
        setMessageTextWIthCustomFont()

        //------------launch main activity after delay time-------------------
        LaunchMainActivityAfterDelayTime()
    }

    private fun setMessageTextWIthCustomFont() {
        val typeface: Typeface = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf")
        messageText!!.setTypeface(typeface)
        messageText!!.setText(getResources().getString(R.string.app_name))
    }

    private fun LaunchMainActivityAfterDelayTime() {
        val DELAY_TIME = 2000
        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
            finish()
        }, DELAY_TIME.toLong())
    }
}
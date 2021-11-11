package com.rezwan.weatherapptest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Country {
    @SerializedName("country")
    @Expose
    var country: String? = null
}
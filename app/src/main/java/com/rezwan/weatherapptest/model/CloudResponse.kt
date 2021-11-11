package com.rezwan.weatherapptest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CloudResponse {
    @SerializedName("all")
    @Expose
    var all: Int? = null
}
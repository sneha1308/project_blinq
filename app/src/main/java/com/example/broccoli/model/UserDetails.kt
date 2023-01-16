package com.example.broccoli.model

import com.google.gson.annotations.SerializedName

data class UserDetails (
    @SerializedName("name") val userName: String,
    @SerializedName("email") val email: String

)
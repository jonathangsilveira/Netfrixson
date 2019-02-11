package br.edu.jgsilveira.portfolio.retrofitgithubsample.dto

import com.google.gson.annotations.SerializedName

class GuestSession(
        @SerializedName(value = "guest_session_id")
        val guestSessionId: String,
        @SerializedName(value = "expires_at")
        val expiresAt: String
)
package br.edu.jgsilveira.portfolio.retrofitgithubsample.dto

import br.edu.jgsilveira.portfolio.netfrixson.api.dto.BaseResponse
import com.google.gson.annotations.SerializedName

class GuestSession(
        @SerializedName(value = "guest_session_id")
        val guestSessionId: String,
        @SerializedName(value = "expires_at")
        val expiresAt: String, success: Boolean?, statusCode: Int?, statusMessage: String?
) : BaseResponse(success, statusCode, statusMessage)
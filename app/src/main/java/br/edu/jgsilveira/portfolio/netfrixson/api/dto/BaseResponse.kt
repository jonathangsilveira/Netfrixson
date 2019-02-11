package br.edu.jgsilveira.portfolio.netfrixson.api.dto

import com.google.gson.annotations.SerializedName

open class BaseResponse(val success: Boolean,
                        @SerializedName(value = "status_code") val statusCode: Int,
                        @SerializedName(value = "status_message") val statusMessage: String)
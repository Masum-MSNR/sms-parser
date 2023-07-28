package com.msnr.smsparser.network.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponsePojo(
    @SerializedName("meta")
    @Expose
    val meta: Meta,
    @SerializedName("results")
    @Expose
    val results: Results,
    @SerializedName("errortext")
    @Expose
    val errortext: String
)

data class Meta(
    @SerializedName("timestamp")
    @Expose
    val timestamp: String,

    @SerializedName("serverid")
    @Expose
    val serverid: String,

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("requestid")
    @Expose
    val requestid: String
)

data class Results(
    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("in_database")
    @Expose
    val inDatabase: Boolean,

    @SerializedName("phish_id")
    @Expose
    val phishId: String,

    @SerializedName("phish_detail_page")
    @Expose
    val phishDetailPage: String,

    @SerializedName("verified")
    @Expose
    val verified: Boolean,

    @SerializedName("verified_at")
    @Expose
    val verifiedAt: String,

    @SerializedName("valid")
    @Expose
    val valid: Boolean
)
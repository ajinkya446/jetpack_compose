package com.jetpack.ocac.services

import com.google.gson.JsonObject
import com.jetpack.ocac.Model.AKAI.AKAIModel
import com.jetpack.ocac.Model.AadhaarValidate
import com.jetpack.ocac.Model.LanguageModel
import com.jetpack.ocac.Model.Profile.UserProfileModel
import com.jetpack.ocac.Model.RefreshTokenModel
import com.jetpack.ocac.Model.ValidateOTP
import com.jetpack.ocac.Model.WalkthroughModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

var baseUrl: String = "https://fup.famrut.com/documentation/"
var akkaiBaseUrl: String = "https://www.amakrushi.ai/"
var access_token = ""

interface APIService {

    @Headers(
        "app-type': android",
        "api-key': j35k9g7s2h0d4l6a",
        "Content-Type: application/json; charset=UTF-8",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @GET("intro_screen_list/?limit=3")
    fun getIntroList(): Call<WalkthroughModel?>?

    @Headers(
        "app-type': android",
        "api-key': j35k9g7s2h0d4l6a",
        "Content-Type: application/json; charset=UTF-8",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @GET("ko_auto_login")
    fun getKrushakToken(): Call<JsonObject>

    @Headers(
        "app-type': android",
        "api-key': j35k9g7s2h0d4l6a",
        "Content-Type: application/json; charset=UTF-8",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @POST("user/login-with-unique-id")
    fun getAkaiToken(@Body jsonBody: RequestBody): Call<AKAIModel?>

    @Headers(
        "app-type': android",
        "api-key': j35k9g7s2h0d4l6a",
        "Content-Type: application/json; charset=UTF-8",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @GET("safal_auto_login")
    fun getSAFALToken(): Call<JsonObject>

    @Headers(
        "app-type': android",
        "api-key': j35k9g7s2h0d4l6a",
        "Content-Type: application/json; charset=UTF-8",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @GET("languages/")
    fun getLanguageList(): Call<LanguageModel?>?

    @Headers(
        "app-type: android",
        "Content-Type: application/json; charset=UTF-8",
        "accept: application/json",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @POST("validate_aadhar/")
    fun sendOTP(@Body jsonBody: RequestBody): Call<AadhaarValidate?>

    @Headers(
        "app-type: android",
        "Content-Type: application/json; charset=UTF-8",
        "accept: application/json",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @POST("validate_otp/")
    fun validateOTP(@Body jsonBody: RequestBody): Call<ValidateOTP?>

    @Headers(
        "app-type: android",
        "Content-Type: application/json; charset=UTF-8",
        "accept: application/json",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @POST("refresh-token")
    fun refreshToken(): Call<RefreshTokenModel?>

    @Headers(
        "app-type: android",
        "Content-Type: application/json; charset=UTF-8",
        "accept: application/json",
        "client-id: 4b2j35-k9g76s-2h0d4l-6ab5yt",
        "client-secret: nuC+hgapd93HTqw++C45ghjyeYF78sdPO="
    )
    @POST("farmer_profile_details_ko/")
    fun getFarmerDetails(): Call<UserProfileModel?>
}
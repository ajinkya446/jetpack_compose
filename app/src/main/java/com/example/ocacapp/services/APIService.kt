package com.example.ocacapp.services

import com.example.ocacapp.Model.LanguageModel
import com.example.ocacapp.Model.WalkthroughModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

var baseUrl: String = "https://fup.famrut.com/documentation/";

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
    @GET("languages/")
    fun getLanguageList(): Call<LanguageModel?>?
}
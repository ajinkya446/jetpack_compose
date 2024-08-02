package com.example.ocacapp.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ocacapp.Model.LanguageModel
import com.example.ocacapp.services.APIService
import com.example.ocacapp.services.baseUrl
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LanguageViewModel : ViewModel() {
    private val _data = mutableStateOf<LanguageModel?>(null)
    val data: State<LanguageModel?> get() = _data

    init {
        viewModelScope.launch {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(APIService::class.java)

            val call: Call<LanguageModel?>? = api.getLanguageList();

            call!!.enqueue(object : Callback<LanguageModel?> {
                override fun onResponse(
                    call: Call<LanguageModel?>,
                    response: Response<LanguageModel?>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Main", "success!" + response.body().toString())
                        _data.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<LanguageModel?>, t: Throwable) {
                    Log.e("Main", "Failed mate " + t.message.toString())
                }
            })
        }
    }
}
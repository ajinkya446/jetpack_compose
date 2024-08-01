package com.example.ocacapp.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ocacapp.Model.WalkthroughModel
import com.example.ocacapp.Services.APIService
import com.example.ocacapp.Services.baseUrl
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WalkthroughViewModel : ViewModel() {
    //    private val repository = DataRepository()
    private val _data = mutableStateOf<WalkthroughModel?>(null)
    val data: State<WalkthroughModel?> get() = _data

    init {
        viewModelScope.launch {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(APIService::class.java)

            val call: Call<WalkthroughModel?>? = api.getIntroList();

            call!!.enqueue(object : Callback<WalkthroughModel?> {
                override fun onResponse(
                    call: Call<WalkthroughModel?>,
                    response: Response<WalkthroughModel?>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Main", "success!" + response.body().toString())
                        _data.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<WalkthroughModel?>, t: Throwable) {
                    Log.e("Main", "Failed mate " + t.message.toString())
                }
            })
        }
    }
}


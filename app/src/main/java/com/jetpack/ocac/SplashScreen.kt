package com.jetpack.ocac

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.auth0.android.jwt.JWT
import com.google.firebase.messaging.FirebaseMessaging
import com.jetpack.ocac.Model.RefreshTokenModel
import com.jetpack.ocac.services.APIService
import com.jetpack.ocac.services.access_token
import com.jetpack.ocac.services.baseUrl
import com.jetpack.ocac.ui.theme.OCACAppTheme
import com.jetpack.ocacapp.R
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

var token = "";

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Request the FCM token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Get the token
                token = task.result
                Log.d("FirebaseToken", "Token: $token")
                // Do something with the token (e.g., send it to your server)
            } else {
                Log.e("FirebaseToken", "Fetching FCM token failed", task.exception)
            }
        }

        setContent {
            OCACAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                    val isBoarded = sharedPref.getBoolean("walkthrough", false)
                    val isLoggedIn = sharedPref.getBoolean("login", false)
                    val accessToken = sharedPref.getString("access-token", "")
                    SplashScreenUI(isBoarded, isLoggedIn, accessToken ?: "")
                }
            }
        }
    }
}

fun getExpirationTime(token: String): Long {
    return try {
        val jwt = JWT(token)
        println("Details Expires:${jwt.expiresAt}")
        val expirationTime = jwt.expiresAt ?: return -1
        val currentTime = Date()

        // Calculate the difference in milliseconds
        val diffInMillis = (expirationTime.time) - currentTime.time
        println("Difference: ${TimeUnit.MILLISECONDS.toMinutes(diffInMillis)}")
        println("Difference printed")
        // Convert milliseconds to minutes or hours
        TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    } catch (e: Exception) {
        -1
    }
}

@Composable
fun SplashScreenUI(isBoarded: Boolean, isLoggedIn: Boolean, accessToken: String) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(3000) // 3 se
        if (accessToken != null && accessToken != "") {
            access_token = accessToken
            val timeUntilExpiration = getExpirationTime(accessToken)
            if (timeUntilExpiration > 0) {
                context.startActivity(
                    Intent(
                        context,
                        if (!isBoarded) MainActivity::class.java else if (isLoggedIn) DashboardScreen::class.java else LoginScreenActivity::class.java
                    )
                )
            } else {
                val retrofit = Retrofit
                    .Builder()
                    .baseUrl(baseUrl).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val api = retrofit.create(APIService::class.java)
                val call: Call<RefreshTokenModel?> = api.refreshToken()
                call.enqueue(object : Callback<RefreshTokenModel?> {
                    override fun onResponse(
                        call: Call<RefreshTokenModel?>,
                        response: Response<RefreshTokenModel?>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()!!
                            val sharedPref =
                                context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString("access-token", data.access_token)
                            editor.apply()
                            context.startActivity(
                                Intent(
                                    context,
                                    if (!isBoarded) MainActivity::class.java else if (isLoggedIn) DashboardScreen::class.java else LoginScreenActivity::class.java
                                )
                            )
                        } else if (response.code() == 401) {
                            context.startActivity(Intent(context, LoginScreenActivity::class.java))
                        }
                    }

                    override fun onFailure(
                        call: Call<RefreshTokenModel?>, t: Throwable
                    ) {
                        context.startActivity(Intent(context, LoginScreenActivity::class.java))
                    }
                })
            }

        } else {
            context.startActivity(
                Intent(
                    context,
                    if (!isBoarded) MainActivity::class.java else LoginScreenActivity::class.java
                )
            )
        }

    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .height(160.dp)
                .width(160.dp),
            painter = painterResource(id = R.drawable.ocac_logo),
//                tint = Color.Unspecified,
            contentDescription = ""
        )
    }
}

// OkHttpClient with the interceptor
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder: Request.Builder = originalRequest.newBuilder()
        if (!access_token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $access_token")
        }
        val request = requestBuilder.build()
        chain.proceed(request)
    }
    .build()


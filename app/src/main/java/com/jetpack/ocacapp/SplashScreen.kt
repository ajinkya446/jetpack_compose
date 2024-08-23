package com.jetpack.ocac

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
import com.google.firebase.messaging.FirebaseMessaging
import com.jetpack.ocac.ui.theme.OCACAppTheme
import com.jetpack.ocacapp.LoginScreenActivity
import kotlinx.coroutines.delay

var token = "";

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
                    SplashScreenUI(isBoarded, isLoggedIn)
                }
            }
        }
    }
}

@Composable
fun SplashScreenUI(isBoarded: Boolean, isLoggedIn: Boolean) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        context.startActivity(
            Intent(
                context,
                if (!isBoarded) MainActivity::class.java else if (isLoggedIn) DashboardScreen::class.java else LoginScreenActivity::class.java
            )
        )
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
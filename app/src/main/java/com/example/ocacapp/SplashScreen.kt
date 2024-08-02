package com.example.ocacapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.example.ocacapp.ui.theme.OCACAppTheme
import kotlinx.coroutines.delay

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OCACAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {val context = LocalContext.current
                    val sharedPref = context.getSharedPreferences("my_prefs",Context.MODE_PRIVATE)
                    val isBoarded = sharedPref.getBoolean("walkthrough", false)
                    SplashScreenUI(isBoarded)
                }
            }
        }
    }
}

@Composable
fun SplashScreenUI(isBoarded: Boolean) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        if (!isBoarded) {
            context.startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                )
            )
        } else {
            context.startActivity(
                Intent(
                    context,
                    LoginScreenActivity::class.java
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
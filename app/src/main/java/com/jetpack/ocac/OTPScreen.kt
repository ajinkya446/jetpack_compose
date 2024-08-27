package com.jetpack.ocac

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.ocac.Model.ValidateOTP
import com.jetpack.ocac.services.APIService
import com.jetpack.ocac.services.access_token
import com.jetpack.ocac.services.baseUrl
import com.jetpack.ocac.ui.theme.OCACAppTheme
import com.jetpack.ocacapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OTPScreen : ComponentActivity() {
    private val timerViewModel: TimerViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var otp by remember { mutableStateOf("") }
            val snackbarHostState = remember { SnackbarHostState() }
            val mobileNumber = intent.getStringExtra("mobile_number")
            val transactionId = intent.getStringExtra("transaction_id")
            print("Mobile No:$mobileNumber")
            print("transactionId No:$transactionId")
            OCACAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    var loading by remember { mutableStateOf(false) }
                    val context = LocalContext.current
                    val data = remember { mutableStateOf<ValidateOTP?>(null) }
//                    var timeLeft by remember { mutableStateOf(120) } // Set initial countdown time in seconds
                    val timeLeft by timerViewModel.timeLeft.collectAsState()
                    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .paint(
                                    alignment = Alignment.TopStart,
                                    painter = painterResource(R.drawable.svg),
                                    contentScale = ContentScale.FillWidth
                                )
                                .padding(paddingValues = innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            if (loading) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(
                                        color = Color(0xff11842E), strokeWidth = 3.2.dp
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text("Loading")
                                }
                            } else {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ocac_logo),
                                        contentDescription = "OTP Screens",
                                        modifier = Modifier
                                            .width(151.dp)
                                            .height(173.dp),
                                        tint = Color.Unspecified
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Enter authentication code", style = TextStyle(
                                            fontSize = 20.sp,
                                            color = Color.Black,
                                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        modifier = Modifier.width(297.dp),
                                        text = "Enter the 6-digit OTP we just sent to your Krushak Odisha registered mobile number ending with $mobileNumber",
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            color = Color(0xff91949A),
                                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                                            fontWeight = FontWeight.Light
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        text = "OTP",
                                        textAlign = TextAlign.Start,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = Color(0xff11842E),
                                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = Color(0xff11842E),
                                            unfocusedBorderColor = Color(0xff11842E)
                                        ),
                                        value = otp,
//                                    modifier = inputModifier,
                                        textStyle = TextStyle(
                                            fontSize = 14.sp,
                                            color = Color.Black,
                                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                                            fontWeight = FontWeight.Normal
                                        ),
                                        onValueChange = {
                                            if (it.length <= 6) {
                                                otp = it
                                            }
                                        },
                                        placeholder = {
                                            Text(
                                                text = "Enter OTP", style = TextStyle(
                                                    fontSize = 16.sp,
                                                    color = Color.Gray,
                                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                                    fontWeight = FontWeight.Normal
                                                )
                                            )
                                        },
                                        singleLine = true
                                    )
                                    Spacer(modifier = Modifier.height(17.dp))
                                    Box(modifier = Modifier
                                        .clickable {
                                            loading = true
                                            val json = JSONObject()
                                            json.put("otp", otp)
                                            json.put("transaction_id", transactionId)
                                            json.put("device_id", token)
                                            val retrofit = Retrofit
                                                .Builder()
                                                .baseUrl(baseUrl)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build()
                                            // Create a request body with the JSON object
                                            val body = RequestBody.create(
                                                "application/json".toMediaTypeOrNull(),
                                                json.toString()
                                            )
                                            val api = retrofit.create(APIService::class.java)
                                            val call: Call<ValidateOTP?> = api.validateOTP(body)
                                            call.enqueue(object : Callback<ValidateOTP?> {
                                                override fun onResponse(
                                                    call: Call<ValidateOTP?>,
                                                    response: Response<ValidateOTP?>
                                                ) {
                                                    if (response.isSuccessful) {
                                                        data.value = response.body()!!
                                                        loading = false
                                                        access_token =
                                                            data.value?.access_token ?: ""
                                                        val sharedPref =
                                                            context.getSharedPreferences(
                                                                "my_prefs",
                                                                Context.MODE_PRIVATE
                                                            )
                                                        val editor = sharedPref.edit()
                                                        editor.putBoolean("login", true)
                                                        editor.putString(
                                                            "access-token",
                                                            data.value?.access_token ?: ""
                                                        )
                                                        editor.apply()
                                                        context.startActivity(
                                                            Intent(
                                                                context,
                                                                DashboardScreen::class.java
                                                            )
                                                        )
                                                        data.value = null
                                                    } else if (response.code() == 410) {
                                                        loading = false
                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                "Expired OTP. Please retry"
                                                            )
                                                        }
                                                    } else if (response.code() == 400) {
                                                        loading = false
                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                "OTP entered is Incorrect"
                                                            )
                                                        }
                                                    } else {
                                                        loading = false
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<ValidateOTP?>, t: Throwable
                                                ) {
                                                    coroutineScope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            "Something Went wrong..."
                                                        )
                                                        loading = false
                                                    }
                                                }
                                            })
                                        }
                                        .fillMaxWidth()
                                        .height(58.dp)
                                        .padding(horizontal = 16.dp)
                                        .background(
                                            color = Color(0xff11842E),
                                            shape = RoundedCornerShape(20)
                                        )
                                        .size(15.dp), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "Verify and Login", style = TextStyle(
                                                fontSize = 16.sp,
                                                color = Color.White,
                                                fontFamily = FontFamily(Font(R.font.lato_regular)),
                                                fontWeight = FontWeight.Normal
                                            )
                                        )
                                    }
                                    if (timeLeft == 0) Spacer(modifier = Modifier.height(12.dp))
                                    if (timeLeft == 0) Box(modifier = Modifier
                                        .clickable {
                                            timerViewModel.resetTimer()
                                        }
                                        .fillMaxWidth()
                                        .height(58.dp)
                                        .padding(horizontal = 16.dp)
                                        .background(
                                            color = Color(0xff11842E),
                                            shape = RoundedCornerShape(20)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xffBFC1C5),
                                            shape = RoundedCornerShape(20)
                                        ), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "Resend OTP", style = TextStyle(
                                                fontSize = 16.sp,
                                                color = Color.White,
                                                fontFamily = FontFamily(Font(R.font.lato_regular)),
                                                fontWeight = FontWeight.Normal
                                            )
                                        )
                                    }
                                    if (timeLeft != 0) Spacer(modifier = Modifier.height(12.dp))
                                    if (timeLeft != 0) CountdownTimerApp(timerViewModel)
                                    Spacer(modifier = Modifier.height(48.dp))
                                    Row {
                                        Text(
                                            modifier = Modifier.padding(top = 2.dp),
                                            text = "Has your mobile number changed?  ",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                fontFamily = FontFamily(Font(R.font.lato_regular)),
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            modifier = Modifier.clickable {
                                                val urlIntent = Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse("https://krushak.odisha.gov.in/citizen-portal/mobile-number-update")
                                                )
                                                context.startActivity(urlIntent)
                                            },
                                            text = "Click here",
                                            textDecoration = TextDecoration.Underline,
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                color = Color(0xffFF951A),
                                                fontFamily = FontFamily(Font(R.font.lato_regular)),
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

class TimerViewModel : ViewModel() {
    private val _timeLeft = MutableStateFlow(120) // 10 minutes in seconds
    val timeLeft: StateFlow<Int> = _timeLeft

    ///Starting the timer
    fun startTimer() {
        viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000L) // Delay for 1 second
                _timeLeft.value -= 1
            }
        }
    }

    /// Resetting the timer
    fun resetTimer(newTime: Int = 120) {
        _timeLeft.value = newTime
        startTimer()
    }
}

@Composable
fun CountdownTimerApp(timerViewModel: TimerViewModel) {
    val timeLeft by timerViewModel.timeLeft.collectAsState()

    LaunchedEffect(Unit) {
        timerViewModel.startTimer()
    }

    if (timeLeft != 0) TimerScreen(timeLeft)
}


@Composable
fun TimerScreen(timeLeft: Int) {
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeString = String.format("%02d:%02d", minutes, seconds)
    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(id = R.drawable.outline_timer_24),
            contentDescription = "Timer",
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = timeString, style = TextStyle(
                fontSize = 12.sp,
                color = Color.LightGray,
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                fontWeight = FontWeight.Normal
            )
        )
    }
}

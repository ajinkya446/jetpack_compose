package com.jetpack.ocacapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import com.jetpack.ocac.OTPScreen
import com.jetpack.ocac.R
import com.jetpack.ocac.services.APIService
import com.jetpack.ocac.services.baseUrl
import com.jetpack.ocac.ui.theme.OCACAppTheme
import com.jetpack.ocacapp.Model.AadhaarValidate
import com.jetpack.ocacapp.Model.ValidateAadhaar
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            OCACAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var textState by remember { mutableStateOf("") }
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

                            // Define what happens when the text changes
                            val onTextChanged: (String) -> Unit = { newText ->
                                textState = newText
                                // Handle the new text
                            }

                            LoginDesignUI(
                                snackbarHostState,
                                label = textState,
                                onTextChanged = onTextChanged,
                            )


                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginDesignUI(
        snackbarHostState: SnackbarHostState,
        label: String,
        onTextChanged: (String) -> Unit
    ) {
        var loading by remember { mutableStateOf(false) }

        val context = LocalContext.current
        val data = remember { mutableStateOf<ValidateAadhaar?>(null) }
        val otpDetails = remember { mutableStateOf<AadhaarValidate?>(null) }
        val coroutineScope = rememberCoroutineScope()
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
                    contentDescription = "",
                    modifier = Modifier
                        .width(151.dp)
                        .height(173.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Welcome to Krushak Odisha", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    modifier = Modifier.width(297.dp),
                    text = "Please Log in with your Krushak Odisha registered Aadhaar Number",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xff91949A),
                        fontFamily = FontFamily(Font(R.font.lato_thin)),
                        fontWeight = FontWeight.Light
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "Enter Aadhaar Number",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xff11842E),
                        unfocusedBorderColor = Color(0xff11842E)
                    ),
                    value = label,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                        fontWeight = FontWeight.Normal
                    ),
                    onValueChange = {
                        if (it.length <= 12) {
                            onTextChanged(it)
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Enter 12 digit Aadhaar Number", style = TextStyle(
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
                Box(
                    modifier = Modifier
                        .clickable {
                            val isStatus: Boolean = validateAadhaarNumber(label)
                            if (isStatus) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Invalid Aadhaar number. It must be exactly 12 digits.")
                                }
                            } else {
                                val json = JSONObject()
                                json.put("aadhaar_no", label)
                                loading = true
                                val retrofit = Retrofit
                                    .Builder()
                                    .baseUrl(baseUrl)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                val body = RequestBody.create(
                                    "application/json".toMediaTypeOrNull(),
                                    json.toString()
                                )
                                val api = retrofit.create(APIService::class.java)
                                val sendCall: Call<AadhaarValidate?> =
                                    api.sendOTP(body)
                                sendCall.enqueue(object : Callback<AadhaarValidate?> {
                                    override fun onResponse(
                                        callOTP: Call<AadhaarValidate?>,
                                        responseOTP: Response<AadhaarValidate?>
                                    ) {
                                        if (responseOTP.isSuccessful) {
                                            otpDetails.value = responseOTP.body()!!
                                            loading = false
                                            val intent = Intent(
                                                context, OTPScreen::class.java
                                            )
                                            intent.putExtra(
                                                "transaction_id",
                                                otpDetails.value!!.transaction_id
                                            )
                                            intent.putExtra(
                                                "mobile_number",
                                                otpDetails.value!!.mobile_number
                                            )
                                            context.startActivity(intent)
                                            otpDetails.value = null
                                        } else if (responseOTP.code() == 400) {
                                            loading = false
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(
                                                    "Could not able to send otp to aadhaar number."
                                                )
                                            }
                                        } else {
                                            loading = false
                                        }
                                    }

                                    override fun onFailure(
                                        callOTP: Call<AadhaarValidate?>,
                                        t: Throwable
                                    ) {
                                        loading = false
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Could not able to send otp to aadhaar number."
                                            )
                                        }
                                    }
                                })
                                data.value = null
                            }
                        }
                        .fillMaxWidth()
                        .height(58.dp)
                        .padding(horizontal = 16.dp)
                        .background(
                            color = Color(0xff11842E), shape = RoundedCornerShape(20)
                        )
                        .size(15.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Validate Aadhaar", style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
                Row {
                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = "Not registered with Krushak Odisha? ",
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
                                Uri.parse("https://krushak.odisha.gov.in/citizen-portal/login")
                            )
                            context.startActivity(urlIntent)
                        },
                        text = "Register",
                        textDecoration = TextDecoration.Underline,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color(0xffFF951A),
                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
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

    /// Checking aadhaar number
    private fun validateAadhaarNumber(aadhaar: String): Boolean {
        val aadhaarRegex = Regex("^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}\$")
        return !aadhaarRegex.matches(aadhaar)
    }
}



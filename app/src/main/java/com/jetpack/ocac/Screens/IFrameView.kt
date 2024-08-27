package com.jetpack.ocac.Screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.jetpack.ocac.services.access_token
import com.jetpack.ocac.ui.theme.OCACAppTheme
import okhttp3.OkHttpClient
import okhttp3.Request

class IFrameView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OCACAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val url: String? = intent.getStringExtra("url")
                    WebViewScreen(url ?: "")
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String) {
    if (url == "") {
        Text(text = "No Url Found")
    } else {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            // Show a loading indicator if necessary
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            // Hide the loading indicator if necessary
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?, error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            // Handle the error here
                            // You can show an error message or a custom error page
                        }
                    }
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.domStorageEnabled = true;
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            }
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

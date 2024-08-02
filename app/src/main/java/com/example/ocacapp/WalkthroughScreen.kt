@file:OptIn(ExperimentalFoundationApi::class)

package com.example.ocacapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ocacapp.ViewModels.WalkthroughViewModel
import com.example.ocacapp.ui.theme.OCACAppTheme

class WalkthroughScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MultiDex.install(this)
        setContent {
            OCACAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnBoardingScreen()

                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(walkthroughViewModel: WalkthroughViewModel = viewModel()) {
    val data by walkthroughViewModel.data
    println("Data = ${(data?.data?.size ?: "No Size")}")

    if (data != null) {
        val pagerState = rememberPagerState(0, 0F, pageCount = ({ data?.data?.size ?: 2 }))
        var url = remember {
            ""
        }
        var text = remember {
            ""
        }
        var description = remember {
            ""
        }
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {

            HorizontalPager(state = pagerState) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    text = (data?.data?.get(pagerState.currentPage)?.title ?: "")
                    description =
                        (data?.data?.get(pagerState.currentPage)?.description ?: "")
                    url = (data?.data?.get(pagerState.currentPage)?.image ?: "")
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.plant),
                        modifier = Modifier
                            .size(360.dp)
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = description,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xff3F3F3F),
                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(data?.data?.size ?: 2) {
                    CustomIndicator(isSelected = pagerState.currentPage == it)
                }
                if (pagerState.currentPage == (data?.data?.size ?: 2)) {
                    Spacer(modifier = Modifier.weight(1f))
                }
                if (pagerState.currentPage == (data?.data?.size ?: 2)) {
                    val context = LocalContext.current
                    Box(
                        modifier = Modifier
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        context,
                                        LoginScreenActivity::class.java
                                    )
                                )
                            }
                            .width(120.dp)
                            .height(40.dp)
                            .padding(2.dp)
                            .background(
                                color = Color(0xff11842E),
                                shape = RoundedCornerShape(60)
                            )
                            .size(15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Continue",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.lato_regular)),
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text("Loading")
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomIndicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .width(if (isSelected) 60.dp else 20.dp)
//            .border(width = 1.dp, color = Color(0xff11842E), shape = RoundedCornerShape(12))
            .padding(2.dp)
            .background(
                color = if (isSelected) Color(0xff11842E) else Color(0xffD3D5D7),
                shape = if (isSelected) RoundedCornerShape(60) else CircleShape
            )
            .size(15.dp)
    )
}






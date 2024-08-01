package com.example.ocacapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.ocacapp.ui.theme.OCACAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            OCACAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    Scaffold(
                        bottomBar = {
                            Box(contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(58.dp)
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                                    .background(
                                        color = Color(0xff11842E),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                WalkthroughScreen::class.java
                                            )
                                        )
                                    }) {
                                Text(
                                    text = "Continue",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        },

                        ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .paint(
                                    painter = painterResource(R.drawable.background),
                                    contentScale = ContentScale.FillWidth
                                )
                                .padding(paddingValues = innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            ChangeLanguageUI(Modifier)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChangeLanguageUI(modifier: Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Choose Language",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Please select a language that best suits your need",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                fontWeight = FontWeight.Medium,
                color = Color(0xff3F3F3F)
            )
        )
        Spacer(modifier = Modifier.height(48.dp))
        KindRadioGroupUsage()
    }
}

@Composable
fun KindRadioGroupUsage() {
    val kinds = listOf("English", "Hindi", "Orissa")
    val (selected, setSelected) = remember { mutableStateOf(kinds[0]) }

    KindRadioGroup(
        mItems = kinds,
        selected, setSelected
    )


}

@Composable
fun KindRadioGroup(
    mItems: List<String>,
    selected: String,
    setSelected: (selected: String) -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            mItems.forEach { item ->
                Box(
                    modifier = Modifier
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (item == selected),
                            // below method is called on
                            // clicking of radio button.
                            onClick = { setSelected(item) }
                        )
                        .padding(bottom = 12.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xff11842E),
                            shape = RoundedCornerShape(8.dp)
                        )

                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == item,
                            onClick = {
                                setSelected(item)
                            },
                            enabled = true,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xff11842E)
                            )
                        )

                        Text(
                            text = item,
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.lato_regular))),
                        )

                    }
                }
            }
        }
    }
}


/*

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OCACAppTheme {
        ChangeLanguageUI(Modifier)
    }
}*/

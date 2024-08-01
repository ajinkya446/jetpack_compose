package com.example.ocacapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.ocacapp.ui.theme.OCACAppTheme
import kotlinx.coroutines.launch

class DashboardScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OCACAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DashboardScreenUI()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenUI() {

    val items = listOf(
        GridMenu(
            modifier = Modifier,
            icon = painterResource(id = R.drawable.weather_sun),
            title = "Weather"
        ),
        GridMenu(
            modifier = Modifier,
            icon = painterResource(id = R.drawable.schemes),
            title = "Schemes"
        ),
        GridMenu(
            modifier = Modifier,
            icon = painterResource(id = R.drawable.plant),
            title = "Plant Protection"
        ),
        GridMenu(
            modifier = Modifier,
            icon = painterResource(id = R.drawable.other_info),
            title = "Other Information"
        )
    )
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var expanded by remember { mutableStateOf(false) }
    var pageIndex by remember { mutableStateOf(0) }
    val langItems = listOf("English", "Hindi", "Orissa")
    var selectedItem by remember { mutableStateOf("English") }
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContentUI()
        },
    ) {

        Scaffold(topBar = {
            TopAppBar(
                modifier = Modifier.clip(shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)),
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(
                            Icons.Rounded.Menu,
                            contentDescription = "sidebar",
                            tint = Color.White,
//                            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                        )
                    }
                },
                title = {
                    Text(
                        text = "Hello, Ajinkya ",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                            fontWeight = FontWeight.Normal
                        )
                    )
                },
                actions = {
                    Box(modifier = Modifier
                        .width(76.dp)
                        .clickable { expanded = !expanded }) {
                        Row {
                            Icon(
                                painterResource(id = R.drawable.baseline_language_24),
                                contentDescription = "global",
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            if (expanded) DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                properties = PopupProperties(focusable = true)
                            ) {
                                langItems.forEach { langItem ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedItem = langItem
                                            expanded = false
                                        },
                                        text = {
                                            Text(
                                                text = "$langItem", style = TextStyle(
                                                    fontSize = 16.sp,
                                                    color = Color.Black,
                                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                                    fontWeight = FontWeight.Normal
                                                )
                                            )
                                        },
                                    )
                                }
                            }
                            Text(
                                text = selectedItem, style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                    }


                },
                colors = topAppBarColors(
                    containerColor = Color(0xff11842E),
//                titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }, bottomBar = {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xffE1E2E4),
                        shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.clickable { pageIndex = 0 },
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Home",
                            tint = Color(if (pageIndex == 0) 0xff11842E else 0xff9FA3A9)
                        )

                        Text(
                            text = "Home", style = TextStyle(
                                fontSize = 10.sp,
                                color = Color(if (pageIndex == 0) 0xff11842E else 0xff9FA3A9),
                                fontFamily = FontFamily(Font(R.font.lato_regular)),
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(103.dp)
                            .background(
                                shape = RoundedCornerShape(43),
                                color = Color(0xff11842E)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                tint = Color(0xffFFFFFF),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Tap to ask", style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color(0xffFFFFFF),
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                                .clickable { pageIndex = 1 },
                            painter = painterResource(id = R.drawable.notifications),
                            contentDescription = "alert",
                            tint = Color(if (pageIndex == 1) 0xff11842E else 0xff9FA3A9)
                        )

                        Text(
                            text = "Alert", style = TextStyle(
                                fontSize = 10.sp,
                                color = Color(if (pageIndex == 1) 0xff11842E else 0xff9FA3A9),
                                fontFamily = FontFamily(Font(R.font.lato_regular)),
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        },
            floatingActionButtonPosition = FabPosition.End, floatingActionButton = {

                Icon(
                    contentDescription = "helpline",
                    modifier = Modifier
                        .height(58.dp)
                        .width(58.dp).clickable {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:155233")
                            }
                            context.startActivity(intent)
                        },
                    painter = painterResource(id = R.drawable.helpline_mobile),
                    tint = Color.Unspecified
                )

            }) { it ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Weather today", style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .paint(
                            alignment = Alignment.TopStart,
                            painter = painterResource(R.drawable.weather_bg),
                            contentScale = ContentScale.FillWidth
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 9.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "19°C", style = TextStyle(
                                    fontSize = 32.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Nashik, Maharashtra", style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xff3F3F3F),
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                modifier = Modifier
                                    .width(58.dp)
                                    .height(45.dp),
                                painter = painterResource(R.drawable.weather_sun),
                                contentDescription = null,
                            )
                            //                Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Partly Cloudy", style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xff111111),
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Benefits Passbook", style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "View more >", style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xff11842E),
                            fontFamily = FontFamily(Font(R.font.lato_regular)),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color(0xffF5F6F6),
                            shape = RoundedCornerShape(20)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "₹ 12,500", style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Icon(
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(18.dp),
                                    painter = painterResource(id = R.drawable.cash_icon),
                                    contentDescription = null, tint = Color(0xff11842E),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Cash", style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color(0xff717478),
                                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "7 hour", style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Icon(
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(18.dp),
                                    painter = painterResource(id = R.drawable.time_icon),
                                    contentDescription = null, tint = Color(0xff11842E),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Training", style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color(0xff717478),
                                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "20 kg", style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Icon(
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(18.dp),
                                    painter = painterResource(id = R.drawable.kind_icon),
                                    contentDescription = null, tint = Color(0xff11842E),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "In Kind", style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color(0xff717478),
                                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Ask me anything", style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.height(172.dp)) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        userScrollEnabled = false,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            items(items.size) { index ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp, bottom = 8.dp)
                                        .background(
                                            color = Color(0xffF5F6F6),
                                            shape = RoundedCornerShape(10)
                                        )
                                        .padding(all = 10.dp)
                                ) {
                                    Column(horizontalAlignment = Alignment.Start) {
                                        Image(
                                            modifier = Modifier
                                                .height(40.dp)
                                                .width(40.dp),
                                            painter = items[index].icon,
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = items[index].title, style = TextStyle(
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                fontFamily = FontFamily(Font(R.font.lato_regular)),
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "My Portals", style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .height(220.dp)
//                            .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(66.dp)
                            .background(
                                color = Color(0xffF5F6F6),
                                shape = RoundedCornerShape(10)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp),
                                painter = painterResource(id = R.drawable.ko),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Krushak Odisha",
                                textAlign = TextAlign.Start,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                tint = Color.Gray,
                                contentDescription = null
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(66.dp)
                            .background(
                                color = Color(0xffF5F6F6),
                                shape = RoundedCornerShape(10)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp),
                                painter = painterResource(id = R.drawable.sugam),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Go Sugam",
                                textAlign = TextAlign.Start,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                tint = Color.Gray,
                                contentDescription = null
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(66.dp)
                            .background(
                                color = Color(0xffF5F6F6),
                                shape = RoundedCornerShape(10)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp),
                                painter = painterResource(id = R.drawable.safal),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Safal",
                                textAlign = TextAlign.Start,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                tint = Color.Gray,
                                contentDescription = null
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContentUI() {
    ModalDrawerSheet(
        modifier = Modifier.width(260.dp),
        drawerContainerColor = Color.White
    ) {
        val context = LocalContext.current
        DrawerHeader()
        DrawerBody(itemTextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(R.font.lato_regular)
            )
        ),
            items = listOf(
                MenuItem(
                    id = "search",
                    title = "Search",
                    icon = painterResource(R.drawable.search)
                ),
                MenuItem(
                    id = "Profile",
                    title = "My Profile",
                    icon = painterResource(R.drawable.user)
                ),
                MenuItem(
                    id = "portals",
                    title = "My Portals",
                    icon = painterResource(R.drawable.portals)
                ),
                MenuItem(
                    id = "My Services",
                    title = "My Services",
                    icon = painterResource(R.drawable.services_icon)
                ),
                MenuItem(
                    id = "Notifications",
                    title = "Notifications",
                    icon = painterResource(R.drawable.bell_icon)
                ),
                MenuItem(
                    id = "Benefits",
                    title = "Benefits Passbook",
                    icon = painterResource(R.drawable.search)
                ),
                MenuItem(
                    id = "shc",
                    title = "Soil Health Card",
                    icon = painterResource(R.drawable.pharmacy_icon)
                ),
                MenuItem(
                    id = "wheat",
                    title = "Digital Crop Survey",
                    icon = painterResource(R.drawable.wheat)
                ),
                MenuItem(
                    id = "service_response",
                    title = "My Services & Responses",
                    icon = painterResource(R.drawable.cursor)
                ),
                MenuItem(
                    id = "Settings",
                    title = "Settings",
                    icon = painterResource(R.drawable.settings)
                ),
                MenuItem(
                    id = "FAQ",
                    title = "FAQ",
                    icon = painterResource(R.drawable.faq)
                ),
                MenuItem(
                    id = "about",
                    title = "About Us",
                    icon = painterResource(R.drawable.settings)
                ),
                MenuItem(
                    id = "logout",
                    title = "Log Out",
                    icon = painterResource(R.drawable.log_out)
                )
            ), onItemClick = {
                println("Clicked on ${it.title}")
                Log.d("Called Menu", "Clicked on ${it.title}")
                if (it.id == "logout") {
                    context.startActivity(
                        Intent(
                            context,
                            LoginScreenActivity::class.java
                        )
                    )
                }
            }
        )
    }
}




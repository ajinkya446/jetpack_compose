package com.jetpack.ocac

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jetpack.ocac.Model.Profile.UserProfileModel
import com.jetpack.ocac.Screens.jsonObject
import com.jetpack.ocacapp.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DrawerHeader(userProfileModel: UserProfileModel?) {
    var joinedOn: String? = null
    val date=jsonObject?.get("roll_on")
    if ((jsonObject?.get("roll_on") ?: "").toString() != "") {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
        val formattedDate = LocalDateTime.parse(date.toString(), dateFormatter)
//        val localDateTime = LocalDateTime.parse((jsonObject?.get("roll_on") ?: "").toString(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        joinedOn = formattedDate.format(formatter)
    }
    Box(
        modifier = Modifier
            .width(400.dp)
            .height(74.dp)
            .background(color = Color(0xff11842E)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = userProfileModel?.data?.demoInfo?.vchPhotograph
                    ?: "",//painterResource(id = R.drawable.user_image),
                contentDescription = "user",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(47.dp)
                    .clip(CircleShape)
                    .height(47.dp)
                    .width(49.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = userProfileModel?.data?.demoInfo?.vchFarmerName ?: "", style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Joined $joinedOn", style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.lato_regular)),
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 14.sp),
    onItemClick: (MenuItem) -> Unit
) {
    LazyColumn(
        modifier
    ) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .width(400.dp)
                    .fillMaxHeight()
                    .padding(
                        top = 10.dp
                    )
                    .clickable { onItemClick(item) }
                    .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
//                item.icon
                Icon(
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp),
                    painter = item.icon,
                    contentDescription = null,
                    tint = Color(0xff11842E)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    textAlign = TextAlign.Start, modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                if (item.id != "logout") Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }
}
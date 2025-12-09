package com.example.todomobileapp.Screen


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todomobileapp.R

@Composable
fun HomeScreen(navController: NavController) {

    var studyChecked by remember { mutableStateOf(false) }
    var runChecked by remember { mutableStateOf(false) }
    var partyChecked by remember { mutableStateOf(false) }

    var meetupChecked by remember { mutableStateOf(true) }
    var trashChecked by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF7))
    ) {

        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        )

        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrowback),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "October 20, 2022",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "My Todo List",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )


        Spacer(modifier = Modifier.height(20.dp))

            TaskCard(
                icon = R.drawable.book,
                title = "Study lesson",
                time = "",
                checked = studyChecked,
                onCheckedChange = { studyChecked = it }
            )

            TaskCard(
                icon = R.drawable.trophy,
                title = "Run 5k",
                time = "4:00pm",
                checked = runChecked,
                onCheckedChange = { runChecked = it }
            )

            TaskCard(
                icon = R.drawable.calendar,
                title = "Go to party",
                time = "10:00pm",
                checked = partyChecked,
                onCheckedChange = { partyChecked = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Completed",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 20.dp),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            CompletedTaskCard(
                icon = R.drawable.calendar,
                title = "Game meetup",
                time = "1:00pm",
                checked = meetupChecked,
                onCheckedChange = { meetupChecked = it }
            )

            CompletedTaskCard(
                icon = R.drawable.book,
                title = "Take out trash",
                time = "",
                checked = trashChecked,
                onCheckedChange = { trashChecked = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {navController.navigate(Screen.DetailScreen.route) },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A2E83)
                ),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Add New Task", fontSize = 17.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun TaskCard(
    icon: Int,
    title: String,
    time: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xFFF1F0F6), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                if (time.isNotEmpty()) {
                    Text(
                        text = time,
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4A2E83)
                )
            )
        }
    }
}

@Composable
fun CompletedTaskCard(
    icon: Int,
    title: String,
    time: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xFFF1F0F6), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )

                if (time.isNotEmpty()) {
                    Text(
                        text = time,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }

            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4A2E83)
                )
            )
        }
    }
}

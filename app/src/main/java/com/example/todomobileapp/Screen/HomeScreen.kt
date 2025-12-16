package com.example.todomobileapp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todomobileapp.R
import com.example.todomobileapp.roomdatabase.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val taskDatabase = remember { TaskDatabase.getDataBase(context) }
    val repository = remember { Repository(taskDatabase) }
    val viewModel = remember { MainViewModel(repository) }

    val allTasks by viewModel.allNotes.observeAsState(initial = emptyList())

    val activeTasks = allTasks.filter { !it.isCompleted }
    val doneTasks = allTasks.filter { it.isCompleted }

    val headerHeight = 96.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF7))
    ) {

        Image(
            painter = painterResource(id = R.drawable.header2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 20.dp, end = 20.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF3D2A7C)
                )
            }

            Text(
                text = "Todo List",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = headerHeight),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "All Tasks",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(activeTasks) { task ->
                TaskCard(task, viewModel, navController)
            }

            if (doneTasks.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Completed Tasks",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(doneTasks) { task ->
                    TaskCard(task, viewModel, navController)
                }
            }
        }

        Button(
            onClick = { navController.navigate(Screen.DetailScreen.route) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A2E83)),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text("Add New Task", fontSize = 17.sp, color = Color.White)
        }
    }
}

@Composable
fun TaskCard(task: Task, viewModel: MainViewModel, navController: NavController) {

    Box(
        modifier = Modifier
            .clickable {
                navController.navigate(
                    "${Screen.TaskDetail.route}/${task.title}/${task.notes ?: ""}/${task.category}/${task.dateMillis}/${task.timeMillis}/${task.isCompleted}"
                )
            }
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xFFF1F0F6), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = getCategoryIcon(task.category)),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    task.timeMillis?.let {
                        Text(
                            text = millisToTime(it),
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))


                    task.dateMillis?.let {
                        Text(
                            text = millisToDate(it),
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { viewModel.updateTaskCompletion(task, it) },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4A2E83))
            )
        }
    }
}

fun millisToTime(millis: Long): String {
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun millisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun getCategoryIcon(categoryId: Int): Int =
    when (categoryId) {
        1 -> R.drawable.book
        2 -> R.drawable.calendar
        3 -> R.drawable.trophy
        else -> R.drawable.ic_launcher_foreground
    }

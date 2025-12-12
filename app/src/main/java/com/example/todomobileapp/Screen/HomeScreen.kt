package com.example.todomobileapp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import com.example.todomobileapp.roomdatabase.MainViewModel
import com.example.todomobileapp.roomdatabase.Repository
import com.example.todomobileapp.roomdatabase.Task
import com.example.todomobileapp.roomdatabase.TaskDatabase

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val taskDatabase = remember { TaskDatabase.getDataBase(context) }
    val repository = remember { Repository(taskDatabase) }
    val viewModel = remember { MainViewModel(repository) }

    val allTasks by viewModel.allNotes.observeAsState(initial = emptyList())


    val activeTasks = allTasks.filter { !it.isCompleted }
    val doneTasks = allTasks.filter { it.isCompleted }

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
                        .background(Color.White)
                        .clickable { },
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

            // 🔵 UNCOMPLETED TASKS
            // Active Tasks
            activeTasks.forEach { task ->
                TaskCard(task = task, viewModel = viewModel)
            }

            // Completed Tasks
            if (doneTasks.isNotEmpty()) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Completed",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                doneTasks.forEach { task ->
                    TaskCard(task = task, viewModel = viewModel)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate(Screen.DetailScreen.route) },
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


// ----------------------------------------------------------------------
// 🔥 CHECKBOX FIXED – TASK UPDATE WORKING
// ----------------------------------------------------------------------

@Composable
fun TaskCard(
    task: Task,
    viewModel: MainViewModel
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
                    painter = painterResource(id = getCategoryIcon(task.category)),
                    contentDescription = "",
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                task.timeMillis?.let { timeMillis ->
                    Text(
                        text = millisToTime(timeMillis),
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }

            // ✔ Task Completion Checkbox
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { checked ->
                    viewModel.updateTaskCompletion(task, checked)
                },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4A2E83))
            )
        }
    }
}


// TIME FORMAT
fun millisToTime(millis: Long): String {
    val calendar = java.util.Calendar.getInstance().apply { timeInMillis = millis }
    val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
    val minute = calendar.get(java.util.Calendar.MINUTE)
    return String.format("%02d:%02d", hour, minute)
}

// ICON MAPPER
fun getCategoryIcon(categoryId: Int): Int {
    return when (categoryId) {
        1 -> R.drawable.book
        2 -> R.drawable.calendar
        3 -> R.drawable.trophy
        else -> R.drawable.ic_launcher_foreground
    }
}

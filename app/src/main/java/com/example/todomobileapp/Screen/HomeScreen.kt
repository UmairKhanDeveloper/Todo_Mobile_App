package com.example.todomobileapp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val taskDatabase = remember { TaskDatabase.getDataBase(context) }
    val repository = remember { Repository(taskDatabase) }
    val viewModel = remember { MainViewModel(repository) }

    val allTasks by viewModel.allNotes.observeAsState(initial = emptyList())
    val activeTasks = allTasks.filter { !it.isCompleted }
    val doneTasks = allTasks.filter { it.isCompleted }
    val scope = rememberCoroutineScope()

    val headerHeight = 89.dp

    var selectedTasks by remember { mutableStateOf(setOf<Task>()) }

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

            if (selectedTasks.isEmpty()) {
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
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                selectedTasks = emptySet()

                            }
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFF3D2A7C)
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "${selectedTasks.size} selected",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            selectedTasks.forEach { scope.launch { viewModel.Delete(task = it) } }
                            selectedTasks = emptySet()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        if (allTasks.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = headerHeight),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rafiki),
                    contentDescription = "No tasks yet",
                    modifier = Modifier.size(250.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "No tasks added yet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = headerHeight),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                if (activeTasks.isNotEmpty()) {
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
                        TaskCard(
                            task = task,
                            viewModel = viewModel,
                            navController = navController,
                            selectedTasks = selectedTasks,
                            onSelectChange = { isSelected ->
                                selectedTasks =
                                    if (isSelected) selectedTasks + task else selectedTasks - task
                            }
                        )
                    }
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
                        TaskCard(
                            task = task,
                            viewModel = viewModel,
                            navController = navController,
                            selectedTasks = selectedTasks,
                            onSelectChange = { isSelected ->
                                selectedTasks =
                                    if (isSelected) selectedTasks + task else selectedTasks - task
                            }
                        )
                    }
                }
            }
        }

        if (selectedTasks.isEmpty()) {
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
}

@Composable
fun TaskCard(
    task: Task,
    viewModel: MainViewModel,
    navController: NavController,
    selectedTasks: Set<Task>,
    onSelectChange: (Boolean) -> Unit
) {

    val isSelected = selectedTasks.contains(task)

    Box(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    if (selectedTasks.isNotEmpty()) {
                        onSelectChange(!isSelected)
                    } else {
                        navController.navigate(
                            "${Screen.TaskDetail.route}/${task.id}/${task.title}/${task.notes ?: ""}/${task.category}/${task.dateMillis}/${task.timeMillis}/${task.isCompleted}"
                        )
                    }
                },
                onLongClick = {
                    onSelectChange(!isSelected)
                }
            )
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .fillMaxWidth()
            .background(
                if (isSelected) Color(0xFFD3D3F3) else Color.White,
                RoundedCornerShape(16.dp)
            )
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

            if (selectedTasks.isEmpty()) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { viewModel.updateTaskCompletion(task, it) },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4A2E83))
                )
            }
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

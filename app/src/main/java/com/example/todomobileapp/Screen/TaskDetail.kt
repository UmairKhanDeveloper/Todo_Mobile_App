package com.example.todomobileapp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun TaskDetail(
    navController: NavController,
    id: Int,
    title: String,
    notes: String,
    category: Int,
    dateMillis: Long,
    timeMillis: Long,
    isCompleted: Boolean,
) {
    val context = LocalContext.current
    val taskDatabase = remember { TaskDatabase.getDataBase(context) }
    val repository = remember { Repository(taskDatabase) }
    val viewModel = remember { MainViewModel(repository) }
    val scope = rememberCoroutineScope()
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    var showDeleteDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F5FA))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.header2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrowback),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Task Details",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(1f))


                IconButton(onClick = {
                    showDeleteDialog = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        }

        if (showDeleteDialog) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Task") },
                text = { Text("Are you sure you want to delete this task?") },
                confirmButton = {
                    androidx.compose.material3.TextButton(
                        onClick = {
                            val taskToDelete = Task(
                                id = id,
                                title = title,
                                notes = notes,
                                category = category,
                                dateMillis = dateMillis,
                                timeMillis = timeMillis,
                                isCompleted = isCompleted
                            )
                            scope.launch {
                                viewModel.Delete(taskToDelete)
                            }
                            showDeleteDialog = false
                            navController.popBackStack()
                        }
                    ) {
                        Text("Yes", color = Color.Red)
                    }
                },
                dismissButton = {
                    androidx.compose.material3.TextButton(
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text("No")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Label("Task Title")
            ReadOnlyBox(title)

            Spacer(Modifier.height(20.dp))

            Label("Category")
            SelectedCategory(category)

            Spacer(Modifier.height(20.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Label("Date")
                    ReadOnlyBox(dateFormat.format(Date(dateMillis)))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Label("Time")
                    ReadOnlyBox(timeFormat.format(Date(timeMillis)))
                }
            }

            Spacer(Modifier.height(20.dp))

            Label("Notes")
            NotesBox(notes)

            if (isCompleted) {
                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF6B4FA0))
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓ Task Completed",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
fun Label(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun ReadOnlyBox(value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = value,
            fontSize = 15.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun NotesBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = if (text.isEmpty()) "No notes" else text,
            fontSize = 15.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun SelectedCategory(selected: Int) {
    val icon = getCategoryIcon(selected)
    icon?.let {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8E0FF))
                .border(
                    width = 2.dp,
                    color = Color(0xFF5E3BBA),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

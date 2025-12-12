package com.example.todomobileapp.Screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import java.util.Calendar


@Composable
fun DetailScreen(navController: NavController) {

    var taskTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var selectedCategory by remember { mutableStateOf(1) }

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedTimeMillis by remember { mutableStateOf<Long?>(null) }

    val context = LocalContext.current
    val taskDatabase = remember { TaskDatabase.getDataBase(context) }
    val repository = remember { Repository(taskDatabase) }
    val viewModel = remember { MainViewModel(repository) }
    val scope = rememberCoroutineScope()

    val calendar = Calendar.getInstance()


    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)

            selectedDateMillis = calendar.timeInMillis
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePicker = TimePickerDialog(
        context,
        { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            selectedTimeMillis = calendar.timeInMillis
            selectedTime = String.format("%02d:%02d", hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF7))
    ) {

        Image(
            painter = painterResource(id = R.drawable.header2),
            contentDescription = "",
            modifier = Modifier
                .height(96.dp)
                .width(390.dp)
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
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = Color(0xFF3D2A7C),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Add New Task",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                // TASK TITLE
                Text("Task Title", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Task Title") },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4D3B9E),
                        unfocusedBorderColor = Color(0xFFC9C9C9),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("Category", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                    CategoryItem(
                        id = 1,
                        selectedCategory = selectedCategory,
                        onSelect = { selectedCategory = it },
                        icon = R.drawable.book,
                        bgColor = Color(0xFFE5F0FF)
                    )

                    CategoryItem(
                        id = 2,
                        selectedCategory = selectedCategory,
                        onSelect = { selectedCategory = it },
                        icon = R.drawable.calendar,
                        bgColor = Color(0xFFF2E8FF)
                    )

                    CategoryItem(
                        id = 3,
                        selectedCategory = selectedCategory,
                        onSelect = { selectedCategory = it },
                        icon = R.drawable.trophy,
                        bgColor = Color(0xFFFFF5D9)
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                Row(modifier = Modifier.fillMaxWidth()) {

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Date", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = selectedDate,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { datePicker.show() },
                            placeholder = { Text("Date", fontSize = 14.sp) },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.date),
                                    contentDescription = "",
                                    tint = Color(0xFF3D2A7C),
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable { datePicker.show() }
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = Color(0xFF4D3B9E),
                                unfocusedBorderColor = Color(0xFFC9C9C9)
                            )
                        )

                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Time", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = selectedTime,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { timePicker.show() },

                            placeholder = { Text(" Time") },

                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.time),
                                    contentDescription = "",
                                    tint = Color(0xFF3D2A7C),
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable { timePicker.show() }
                                )
                            },

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = Color(0xFF4D3B9E),
                                unfocusedBorderColor = Color(0xFFC9C9C9)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text("Notes", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Notes") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF4D3B9E),
                        unfocusedBorderColor = Color(0xFFC9C9C9)
                    )
                )

                Spacer(modifier = Modifier.height(35.dp))

                Button(
                    onClick = {

                            scope.launch {
                                viewModel.Insert(task = Task(
                                    id = null,
                                    title = taskTitle,
                                    notes = notes,
                                    category = selectedCategory,
                                    dateMillis = selectedDateMillis,
                                    timeMillis = selectedTimeMillis
                                ))
                            }
                            navController.popBackStack()

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF47348F))
                ) {
                    Text("Save", color = Color.White, fontSize = 16.sp)
                }

            }
        }
    }
}


@Composable
fun CategoryItem(
    id: Int,
    selectedCategory: Int,
    onSelect: (Int) -> Unit,
    icon: Int,
    bgColor: Color
) {

    val isSelected = id == selectedCategory

    Box(
        modifier = Modifier
            .size(55.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.White else bgColor)
            .border(
                width = if (isSelected) 3.dp else 2.dp,
                color = if (isSelected) Color(0xFF4D3B9E) else Color.White,
                shape = CircleShape
            )
            .clickable { onSelect(id) },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.size(28.dp)
        )
    }
}






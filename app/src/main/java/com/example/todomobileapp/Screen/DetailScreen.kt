package com.example.todomobileapp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todomobileapp.R

@Composable
fun DetailScreen(navController: NavController) {

    var taskTitle by remember { mutableStateOf("") }
    var notesText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(-1) }

    var dateText by remember { mutableStateOf("") }
    var timeText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF7))
    ) {

        // Purple Header
        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Column(modifier = Modifier.fillMaxSize()) {

            // -------- TOP CLOSE + TITLE --------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Close button circle
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
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Add New Task",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // ------- WHITE CONTAINER --------
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.White,
                        RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)
                    )
            ) {

                Column(modifier = Modifier.padding(22.dp)) {

                    // -------- TITLE --------
                    Text("Task Title", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        placeholder = { Text("Task Title") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFD0D0D0),
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                        )
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // -------- CATEGORY --------
                    Text("Category", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {

                        CategoryIcon(
                            icon = R.drawable.book,
                            selected = selectedCategory == 0,
                            tint = Color(0xFFC3D4FF)        // light blue
                        ) { selectedCategory = 0 }

                        CategoryIcon(
                            icon = R.drawable.calendar,
                            selected = selectedCategory == 1,
                            tint = Color(0xFFD7C5FF)        // purple
                        ) { selectedCategory = 1 }

                        CategoryIcon(
                            icon = R.drawable.trophy,
                            selected = selectedCategory == 2,
                            tint = Color(0xFFFFE9B3)        // yellow
                        ) { selectedCategory = 2 }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // -------- DATE + TIME --------
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // Date
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Date", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(
                                value = dateText,
                                onValueChange = { dateText = it },
                                placeholder = { Text("Date") },
                                trailingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.calendar),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Time
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Time", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(
                                value = timeText,
                                onValueChange = { timeText = it },
                                placeholder = { Text("Time") },
                                trailingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.time),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    // -------- NOTES --------
                    Text("Notes", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        placeholder = { Text("Notes") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        maxLines = 6,
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // -------- SAVE BUTTON --------
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A2E83)
                        )
                    ) {
                        Text("Save", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}


@Composable
fun CategoryIcon(icon: Int, selected: Boolean, tint: Color, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .size(58.dp)
            .clip(CircleShape)
            .background(if (selected) tint else Color(0xFFF1F0F6))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.size(26.dp)
        )
    }
}

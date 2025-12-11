package com.example.todomobileapp.Screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todomobileapp.R

@Composable
fun DetailScreen(navController: NavController) {

    var taskTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(1) }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Date", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.White,
                                    RoundedCornerShape(12.dp)
                                ),
                            shape = RoundedCornerShape(12.dp),

                            placeholder = { Text("Date") },

                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.date),
                                    contentDescription = "",
                                    tint = Color(0xFF3D2A7C),
                                    modifier = Modifier.size(20.dp)
                                )
                            },

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,

                                focusedBorderColor = Color(0xFF4D3B9E),
                                unfocusedBorderColor = Color(0xFFC9C9C9),
                                disabledBorderColor = Color(0xFFC9C9C9),
                            )
                        )

                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Time", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            placeholder = { Text("Time") },
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.time),
                                    contentDescription = "",
                                    tint = Color(0xFF3D2A7C),
                                    modifier = Modifier.size(20.dp)
                                )
                            }, colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,

                                focusedBorderColor = Color(0xFF4D3B9E),
                                unfocusedBorderColor = Color(0xFFC9C9C9),
                                disabledBorderColor = Color(0xFFC9C9C9),
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
                    placeholder = { Text("Notes") }, colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,

                        focusedBorderColor = Color(0xFF4D3B9E),
                        unfocusedBorderColor = Color(0xFFC9C9C9),
                        disabledBorderColor = Color(0xFFC9C9C9),
                    )
                )

                Spacer(modifier = Modifier.height(35.dp))
                Button(
                    onClick = {},
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

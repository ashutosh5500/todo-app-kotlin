package com.ashu.test.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashu.test.R
import com.ashu.test.viewModels.TodoViewModel


@OptIn(ExperimentalMaterial3Api::class)

class TodoAppBars {

    @Composable
    fun DefaultAppBar(todoViewModel: TodoViewModel) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        ) // Apply rounded corners
                        .background(Color(0xff111111))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "TaskApp",
                            color = colorResource(R.color.customOrange),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        CustomDropdown(todoViewModel = todoViewModel)

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = {
                            // Toggle search functionality
                            todoViewModel.isSearchActive.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search todo",
                                tint = Color.White
                            )
                        }

                        if (todoViewModel.isMultiSelectFeatureActive.value) {
                            Text(
                                text = "${todoViewModel.selectedTodos.size} selected",
                                color = Color.White,
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xff111111),
                titleContentColor = Color.White
            )
        )
    }

    @Composable
    fun DeleteAppBar(todoViewModel: TodoViewModel) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            todoViewModel.isMultiSelectFeatureActive.value = false
                            todoViewModel.selectedTodos.clear()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Delete Items",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = {
                        todoViewModel.selectedTodos.forEach { todo -> todoViewModel.deleteTodo(todo) }
                        todoViewModel.isMultiSelectFeatureActive.value = false
                        todoViewModel.selectedTodos.clear()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete todo",
                            tint = Color.White
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black,
                titleContentColor = Color.White
            )
        )
    }

    @Composable
    fun SearchAppBar(todoViewModel: TodoViewModel) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Close search",
                        tint = Color.White,
                        modifier = Modifier.clickable(onClick = {
                            todoViewModel.isSearchActive.value = false
                        })
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "dummy search",
                        tint = Color.White
                    )

                    StyledTextField(
                        value = todoViewModel.searchValue.value,
                        onValueChange = {
                            todoViewModel.searchValue.value = it
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black,
                titleContentColor = Color.White
            )
        )
    }

    @Composable
    private fun CustomDropdown(todoViewModel: TodoViewModel) {
        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    todoViewModel.isExpanded.value = !todoViewModel.isExpanded.value
                }
            ) {
                Text(
                    text = todoViewModel.usernames[todoViewModel.itemPosition.intValue],
                    style = TextStyle(color = Color.White)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "DropDown Icon",
                    tint = Color.White
                )
                DropdownMenu(
                    expanded = todoViewModel.isExpanded.value,
                    onDismissRequest = {
                        todoViewModel.isExpanded.value = false
                    },
                ) {
                    todoViewModel.usernames.forEachIndexed { index, userName ->
                        DropdownMenuItem(
                            text = { Text(userName) },
                            onClick = {
                                todoViewModel.isExpanded.value = false
                                todoViewModel.itemPosition.value = index
                            }
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StyledTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(R.color.customOrange),
                unfocusedBorderColor = Color(0xFFCCCCCC),
                focusedLabelColor = Color(0xFF6200EE),
                cursorColor = colorResource(R.color.white),
            ),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
            ),

            placeholder = {
                Text(
                    text = "Search",
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .padding(8.dp)
                .shadow(1.dp, RoundedCornerShape(8.dp))
        )
    }
}

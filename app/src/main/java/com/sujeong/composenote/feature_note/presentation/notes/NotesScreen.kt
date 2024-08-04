package com.sujeong.composenote.feature_note.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sujeong.composenote.R
import com.sujeong.composenote.core.presentation.component.noteFontFamily
import com.sujeong.composenote.feature_note.presentation.notes.components.NoteItem
import com.sujeong.composenote.feature_note.presentation.notes.components.OrderSection
import com.sujeong.composenote.feature_note.presentation.util.Screen
import com.sujeong.composenote.ui.theme.DarkGray
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        route = Screen.AddEditNoteScreen.route
                    )
                },
                containerColor = DarkGray,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "노트 쓰기",
                    tint = Color.White
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Note",
                    fontFamily = noteFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = with(LocalDensity.current) {
                        32.dp.toSp()
                    },
                    color = DarkGray
                )

                IconButton(
                    modifier = Modifier
                        .height(40.dp)
                        .width(24.dp),
                    onClick = {
                        viewModel.onEvent(
                            NotesEvent.ToggleOrderSection
                        )
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sort_24),
                        contentDescription = "정렬",
                        tint = DarkGray
                    )
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = { noteOrder ->
                        viewModel.onEvent(
                            NotesEvent.Order(noteOrder)
                        )
                    }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    route = Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(
                                NotesEvent.DeleteNote(note)
                            )

                            scope.launch {
                                val result = snackBarHostState.showSnackbar(
                                    message = "삭제되었습니다.",
                                    actionLabel = "취소",
                                    duration = SnackbarDuration.Short
                                )

                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
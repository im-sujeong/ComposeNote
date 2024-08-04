package com.sujeong.composenote.feature_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sujeong.composenote.R
import com.sujeong.composenote.core.presentation.component.NoteTextStyle
import com.sujeong.composenote.feature_note.domain.model.Note
import com.sujeong.composenote.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import com.sujeong.composenote.ui.theme.DarkGray
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    noteColor: Int = Note.noteColors.random().toArgb()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scope = rememberCoroutineScope()

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(
                if(noteColor != -1) noteColor
                else viewModel.noteColor.value
            )
        )
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                AddEditNoteUiEvent.SaveNote -> {
                    navController.navigateUp()
                }

                is AddEditNoteUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                containerColor = DarkGray,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save_24),
                    contentDescription = "저장",
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
                .background(noteBackgroundAnimatable.value)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(16.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 2.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    DarkGray
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }

                                viewModel.onEvent(
                                    AddEditNoteEvent.ChangeColor(
                                        color = colorInt
                                    )
                                )
                            }
                    )
                }
            }

            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint.asString(),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                noteTextStyle = NoteTextStyle.HEADLINE,
                onValueChange = {
                    viewModel.onEvent(
                        AddEditNoteEvent.EnteredTitle(it)
                    )
                },
                onFocusChange = {
                    viewModel.onEvent(
                        AddEditNoteEvent.ChangeTitleFocus(it)
                    )
                },
                isHintVisible = titleState.isHintVisible
            )

            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint.asString(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                noteTextStyle = NoteTextStyle.BODY_LARGE,
                onValueChange = {
                    viewModel.onEvent(
                        AddEditNoteEvent.EnteredContent(it)
                    )
                },
                onFocusChange = {
                    viewModel.onEvent(
                        AddEditNoteEvent.ChangeContentFocus(it)
                    )
                },
                isHintVisible = contentState.isHintVisible
            )
        }
    }
}
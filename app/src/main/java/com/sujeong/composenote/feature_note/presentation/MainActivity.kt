package com.sujeong.composenote.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sujeong.composenote.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.sujeong.composenote.feature_note.presentation.notes.NotesScreen
import com.sujeong.composenote.feature_note.presentation.util.Screen
import com.sujeong.composenote.ui.theme.ComposeNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNoteTheme(
                dynamicColor = false
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController ,
                        startDestination = Screen.NoteScreen.route
                    ) {
                        composable(
                            route = Screen.NoteScreen.route
                        ) {
                            NotesScreen(navController = navController)
                        }

                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val noteColor = it.arguments?.getInt("noteColor") ?: -1

                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = noteColor
                            )
                        }
                    }
                }
            }
        }
    }
}
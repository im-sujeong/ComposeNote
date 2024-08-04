package com.sujeong.composenote.feature_note.presentation.notes.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujeong.composenote.feature_note.domain.util.NoteOrder
import com.sujeong.composenote.feature_note.domain.util.OrderType
import com.sujeong.composenote.ui.theme.DarkGray

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultFilterChip(
                text = "제목순",
                selected = noteOrder is NoteOrder.Title,
                onSelect = {
                    onOrderChange(NoteOrder.Title(noteOrder.orderType))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultFilterChip(
                text = "날짜순",
                selected = noteOrder is NoteOrder.Date,
                onSelect = {
                    onOrderChange(NoteOrder.Date(noteOrder.orderType))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultFilterChip(
                text = "배경색상순",
                selected = noteOrder is NoteOrder.Color,
                onSelect = {
                    onOrderChange(NoteOrder.Color(noteOrder.orderType))
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultFilterChip(
                text = "오름차순",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(
                        noteOrder.copy(OrderType.Ascending)
                    )
                },
                leadingIconVector = Icons.Default.KeyboardArrowUp,
                leadingIconColor = DarkGray,
                selectedLeadingIconColor = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultFilterChip(
                text = "내림차순",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(
                        noteOrder.copy(OrderType.Descending)
                    )
                },
                leadingIconVector = Icons.Default.KeyboardArrowDown,
                leadingIconColor = DarkGray,
                selectedLeadingIconColor = Color.White
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderSectionPreview() {
    OrderSection(onOrderChange = {})
}
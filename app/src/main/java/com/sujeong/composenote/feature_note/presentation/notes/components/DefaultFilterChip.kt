package com.sujeong.composenote.feature_note.presentation.notes.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujeong.composenote.core.presentation.component.NoteText
import com.sujeong.composenote.core.presentation.component.NoteTextStyle
import com.sujeong.composenote.ui.theme.ComposeNoteTheme
import com.sujeong.composenote.ui.theme.DarkGray
import com.sujeong.composenote.ui.theme.Gray
import com.sujeong.composenote.ui.theme.LightGray

@Composable
fun DefaultFilterChip(
    text: String,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit = {},
    selected: Boolean = false,
    @DrawableRes leadingIconResId: Int? = null,
    leadingIconVector: ImageVector? = null,
    leadingIconColor: Color = MaterialTheme.colorScheme.primary,
    selectedLeadingIconColor: Color = MaterialTheme.colorScheme.primary
) {
    FilterChip(
        modifier = Modifier
            .height(24.dp)
            .then(modifier),
        selected = selected,
        onClick = { onSelect() },
        label = {
            NoteText(
                text = text,
                noteTextStyle = if(selected) {
                    NoteTextStyle.LABEL_BOLD
                } else NoteTextStyle.LABEL
            )
        },
        leadingIcon = {
            if(leadingIconVector == null && leadingIconResId == null) {
                return@FilterChip
            }

            val color = if(selected) {
                selectedLeadingIconColor
            }else {
                leadingIconColor
            }

            if (leadingIconVector != null) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = leadingIconVector,
                    contentDescription = "",
                    tint = color
                )
            }else {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = leadingIconResId!!),
                    contentDescription = "",
                    tint = color
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = DarkGray,
            labelColor = Gray,
            selectedLabelColor = Color.White
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            selectedBorderWidth = 0.dp,
            borderColor = LightGray,
            borderWidth = 1.dp
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultRadioButtonPreview() {
    ComposeNoteTheme {
        DefaultFilterChip("제목순")
    }
}
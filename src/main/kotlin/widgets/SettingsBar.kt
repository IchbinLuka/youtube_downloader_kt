package widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import downloader.FileType
import downloader.fileTypes

@Composable
fun SettingsBar(
    onButtonClick: (String, FileType) -> Unit
) {
    var link by remember { mutableStateOf("") }
    var fileType by remember { mutableStateOf(fileTypes[0]) }
    Surface(
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .padding(5.dp)
            ) {
                BasicTextField(
                    value = link,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterStart),
                    onValueChange = {link = it}
                )
                if (link == "") {
                    Text("Enter link")
                }
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(
                elevation = ButtonDefaults.elevation(defaultElevation = 1.dp, pressedElevation = 0.dp),
                onClick = {
                    onButtonClick(link, fileType)
                    link = ""
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Gray
                ),
                border = BorderStroke(width = 1.dp, color = Color.LightGray),
                contentPadding = PaddingValues(horizontal = 4.dp),
                modifier = Modifier.align(Alignment.CenterVertically).height(30.dp)
                    .padding(horizontal = 5.dp)
            ) {
                Text("Download")
            }
            OutputFormatSelector {
                fileType = it
            }
        }
    }
}

@Composable
fun OutputFormatSelector(
    onSelect: (FileType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableStateOf(0) }
    Box {
        Text(
            fileTypes[currentIndex].fileEnding,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    expanded = true
                }
                .padding(7.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            fileTypes.forEachIndexed { i, s ->
                DropdownMenuItem(
                    onClick = {
                        currentIndex = i
                        expanded = false
                        onSelect(s)
                    }
                ) {
                    Text(s.fileEnding)
                }
            }
        }
    }
}
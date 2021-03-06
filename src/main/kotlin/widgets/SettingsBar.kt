package widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
    val borderModifier = Modifier
        .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(5.dp))

    Surface(
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = borderModifier.padding(5.dp).align(Alignment.CenterVertically)
            ) {
                BasicTextField(
                    value = link,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterStart),
                    onValueChange = {link = it}
                )
                if (link == "") {
                    Text(
                        "Enter link",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
            Spacer(modifier = Modifier.width(3.dp))
            Surface(
                modifier = borderModifier
            ) {
                OutputFormatSelector {
                    fileType = it
                }
            }
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
                    .fillMaxWidth()
            ) {
                Text("Download")
            }
        }
    }
}

@Composable
fun OutputFormatSelector(
    modifier: Modifier = Modifier,
    onSelect: (FileType) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableStateOf(0) }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                expanded = true
            }
            .padding(7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Text(
                fileTypes[currentIndex].fileEnding,
                style = MaterialTheme.typography.subtitle2
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
                        Text(s.fileEnding, style = MaterialTheme.typography.subtitle1)
                    }
                }
            }
        }
        Icon(
            Icons.Filled.ArrowDropDown, null,
            modifier = Modifier.size(15.dp)
        )
    }
}
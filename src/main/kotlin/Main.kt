// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.window.singleWindowApplication
import downloader.prepareYtDlExe
import theme.YTDownloaderTheme
import widgets.History
import java.io.File
import java.io.FileOutputStream

fun main() = singleWindowApplication {
    var text by remember { mutableStateOf("Hello, World!") }
    prepareYtDlExe()

    MainScreen()
}

@Preview
@Composable
fun MainScreen() {
    YTDownloaderTheme {
        Column {
            SettingsBar(
                onButtonClick = { _, _ -> }
            )
        }
        History()
    }
}

@Composable
fun SettingsBar(
    onButtonClick: (String, Boolean) -> Unit
) {
    var link by remember { mutableStateOf("")}
    var audioOnly by remember { mutableStateOf(false) }
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
                onClick = { onButtonClick(link, audioOnly) },
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
            LabeledCheckbox(
                checked = audioOnly,
                onCheckedChange = { audioOnly = it },
                label = "Audio only"
            )
        }
    }
}

@Composable
fun LabeledCheckbox(
    modifier: Modifier = Modifier,
    label: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(text = label)
    }
}
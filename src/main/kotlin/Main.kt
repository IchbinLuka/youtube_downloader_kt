// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.YTDownloaderTheme

fun main() = Window {
    ProcessBuilder ()
    var text by remember { mutableStateOf("Hello, World!") }

    var link by remember { mutableStateOf("")}

    YTDownloaderTheme {
        Column {
            SettingsBar(
                link = link,
                onlyAudio = false,
                onValueChange = {text = it},
                onButtonClick = {}
            )
        }
    }
}

@Composable
fun SettingsBar(
    link: String,
    onlyAudio: Boolean,
    onValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Row {
        TextField(
            value = link,
            modifier = Modifier.padding(8.dp)
                .fillMaxWidth(0.7f),
            onValueChange = onValueChange
        )
        Button(
            onClick = onButtonClick
        ) {
            Text("Download")
        }
    }
}
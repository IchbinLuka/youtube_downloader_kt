import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import downloader.YtDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import theme.YTDownloaderTheme
import widgets.History
import widgets.HistoryItemData
import widgets.LabeledCheckbox

fun main() = singleWindowApplication {
    var text by remember { mutableStateOf("Hello, World!") }


    MainScreen()
}

suspend fun downloadVideo(url: String, ytDl: YtDownloader, history: MutableList<HistoryItemData>, path: String) {
    val info = ytDl.getVideoInfo(url)
    if (info != null) {
        val data = HistoryItemData(
            info = info,
            progress = mutableStateOf(0.0)
        )
        history.add(data)
        ytDl.downloadVideo(url = url, destination = path) {
            data.progress.value = it
        }
    }
}

@Preview
@Composable
fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    var history: MutableList<HistoryItemData> = mutableStateListOf<HistoryItemData>()

    val path = YtDownloader.prepareYtDlExe()
    val ytDl = YtDownloader(path)

    val currentPath = System.getProperty("user.dir")

    val onClick: (String, Boolean) -> Unit = { url, _ ->
        coroutineScope.launch {
            launch(Dispatchers.Default) {
                println("Fetching info")
                val info = ytDl.getVideoInfo(url)
                println("Fetched info")
                if (info != null) {
                    val data = HistoryItemData(
                        info = info,
                        progress = mutableStateOf(0.0)
                    )
                    history.add(data)
                    ytDl.downloadVideo(url = url, destination = currentPath) {
                        data.progress.value = it
                    }
                }
            }
        }
    }

    YTDownloaderTheme {
        Column {
            SettingsBar(
                onButtonClick = onClick
            )
            History(history)
        }
    }
}


@Composable
fun SettingsBar(
    onButtonClick: (String, Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
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
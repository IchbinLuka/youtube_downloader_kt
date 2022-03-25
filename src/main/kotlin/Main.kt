import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.singleWindowApplication
import downloader.FileType
import downloader.YtDownloader
import theme.YTDownloaderTheme
import widgets.SettingsBar
import widgets.history.History
import widgets.history.HistoryItemData

fun main() = singleWindowApplication(
    title = "Download Video from Youtube"
) {
    MainScreen()
}

@Preview
@Composable
fun MainScreen() {
    val history: MutableList<HistoryItemData> = mutableStateListOf<HistoryItemData>()

    val path = YtDownloader.prepareYtDlExe()
    val ytDl = YtDownloader(path)

    val currentPath = System.getProperty("user.dir")

    val onClick: (String, FileType) -> Unit = { url, type ->
        Thread {
            val data = HistoryItemData(
                info = mutableStateOf(null),
                progress = mutableStateOf(0.0)
            )
            history.add(0, data)
            ytDl.downloadVideo(url = url, destination = currentPath,
                onProgress = {
                    data.progress.value = it
                },
                outputType = type,
                onVideoInfo = {
                    data.info.value = it
                }
            )
        }.start()
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


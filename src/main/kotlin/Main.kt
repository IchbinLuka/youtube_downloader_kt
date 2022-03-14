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
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import data.VideoInfo
import downloader.YtDownloader
import theme.YTDownloaderTheme
import widgets.History
import widgets.LabeledCheckbox
import java.io.File

fun main() = singleWindowApplication {
    var text by remember { mutableStateOf("Hello, World!") }
    val path = YtDownloader.prepareYtDlExe()
    val dir = File(path).parentFile
    val processBuilder = ProcessBuilder(path, "https://www.youtube.com/watch?v=3BfZETS_kgY", "-P ${dir.absolutePath}", "--write-info-json", "--no-download")
    val process = processBuilder.start()

    process.waitFor()
    val mapper = jacksonObjectMapper()

    val files = dir.listFiles()
    println(path)
    if (files != null) {
        try {
            val infoFile = files.first { it.name.endsWith(".info.json") }
            val info = mapper.readValue<VideoInfo>(String(infoFile.readBytes()), VideoInfo::class.java)
        } catch (e: NoSuchElementException) {
            println("No info json found")
        }
    } else {
        println("No children found")
    }
    val downloader = YtDownloader(path)
    println("Downloading Video")
    downloader.downloadVideo("https://www.youtube.com/watch?v=3BfZETS_kgY", dir.absolutePath) {
        println(it)
    }

    MainScreen()
}

@Preview
@Composable
fun MainScreen() {
    var text by remember { mutableStateOf(listOf<VideoInfo>()) }

    YTDownloaderTheme {
        Column {
            SettingsBar(
                onButtonClick = { _, _ -> }
            )
        }
        History(listOf())
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
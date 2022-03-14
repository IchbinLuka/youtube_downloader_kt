package widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import data.VideoInfo
import util.loadNetworkPicture


data class HistoryItemData(
    val info: VideoInfo,
    var progress: MutableState<Double>
)

@Composable
fun History(
    videos: List<HistoryItemData>
) {
    LazyColumn {
        items(videos) {
            HistoryRow(it)
        }
    }
}

@Composable
fun HistoryRow(
    data: HistoryItemData
) {
    //val progress: Double by remember { data.progress }
    val thumbnails = data.info.thumbnails
    Row {
        Box(
            modifier = Modifier.height(30.dp)
        ) {
            Image(
                bitmap = loadNetworkPicture(thumbnails[thumbnails.size - 2].url), "",
                contentScale = ContentScale.FillHeight
            )
        }
        Column {
            Text(
                data.info.title
            )
            Text(
                data.info.channel
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "${data.progress.value}%"
        )
    }
}
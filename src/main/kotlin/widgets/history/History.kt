package widgets.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import data.VideoInfo
import util.loadNetworkPicture
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


data class HistoryItemData(
    val info: VideoInfo,
    var onProgress: (Double) -> Unit
)

@Composable
fun History(
    downloads: List<HistoryItemData>
) {
    LazyColumn {
        items(downloads) {
            HistoryRow(it)
        }
    }
}

@Composable
fun HistoryRow(
    data: HistoryItemData
) {
    var progress by remember { mutableStateOf(0.0) }
    data.onProgress = {
        println("Called onProgress")
        progress = it
    }

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
            "${progress}%"
        )
    }
}
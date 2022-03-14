package widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import data.VideoInfo
import util.loadNetworkPicture


@Composable
fun History(
    videos: List<VideoInfo>
) {
    LazyColumn {
        items(videos) {
            HistoryRow(it)
        }
    }
}

@Composable
fun HistoryRow(
    info: VideoInfo
) {
    Row {
        Image(
            bitmap = loadNetworkPicture(info.thumbnails.last().url), ""
        )
        Column {
            Text(
                info.title
            )
            Text(
                info.channel
            )
        }
    }
}
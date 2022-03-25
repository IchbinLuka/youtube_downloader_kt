package widgets.history

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.VideoInfo
import util.loadNetworkPicture


data class HistoryItemData(
    val info: MutableState<VideoInfo?>,
    var progress: MutableState<Double>
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

@Preview()
@Composable
fun ExampleRow() {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        Row{
            Surface(
                color = Color.Green,
                modifier = Modifier
                    .fillMaxWidth(50f / 100f)
                    .fillMaxHeight()
            ) {}
            //Spacer(modifier = Modifier.weight(1 - data.progress.value.toFloat()))
        }
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Box(
                modifier = Modifier.height(30.dp)
            ) {
            }
            Column {
                Text(
                    "Title"
                )
                Text(
                    "Description"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "${50}%"
            )
        }
    }
}

@Composable
fun HistoryRow(
    data: HistoryItemData
) {

    val thumbnails = data.info.value?.thumbnails

    val progress by animateFloatAsState(data.progress.value.toFloat())

    Surface(
        modifier = Modifier
            .padding(5.dp)
            .height(75.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ){
            Surface(
                color = Color(0xff22b566),
                modifier = Modifier
                    .fillMaxWidth(progress / 100f)
                    .fillMaxHeight()
            ) {}
            //Spacer(modifier = Modifier.weight(1 - data.progress.value.toFloat()))
        }
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                //modifier = Modifier.height(30.dp)
            ) {
                if (thumbnails != null) {
                    Image(
                        bitmap = loadNetworkPicture(thumbnails[thumbnails.size - 2].url), "",
                        contentScale = ContentScale.FillHeight
                    )
                } else {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(16f / 9f),
                            color = Color.LightGray
                        ) {}
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.width(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                val titleStyle = MaterialTheme.typography.body1
                val channelStyle = MaterialTheme.typography.subtitle1
                if (data.info.value != null) {
                    Text(
                        text = data.info.value!!.title,
                        fontWeight = FontWeight.Bold,
                        style = titleStyle
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        data.info.value!!.channel,
                        style = channelStyle
                    )
                } else {
                    Surface(
                        color = Color.LightGray,
                        modifier = Modifier
                        .size(
                            width = 100.dp,
                            height = titleStyle.fontSize.value.dp)) {}
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = Color.LightGray,
                        modifier = Modifier
                            .size(
                                width = 75.dp,
                                height = channelStyle.fontSize.value.dp)) {}
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color(0x7fffffff)
            ) {
                Text(
                    "${data.progress.value}%"
                )
            }
        }
    }
}

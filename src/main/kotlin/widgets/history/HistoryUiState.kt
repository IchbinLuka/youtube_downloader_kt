package widgets.history

import androidx.compose.runtime.mutableStateListOf

class HistoryUiState(
    private val initialDownloads: List<HistoryItemData>
) {
    private val _downloads: MutableList<HistoryItemData> = mutableStateListOf(
        *initialDownloads.toTypedArray()
    )

    val downloads: List<HistoryItemData> = _downloads

    fun addDownload(download: HistoryItemData) {
        _downloads.add(download)
    }
}
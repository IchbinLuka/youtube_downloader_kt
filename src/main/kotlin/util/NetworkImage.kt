package util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.jetbrains.skia.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

fun loadNetworkPicture(link: String): ImageBitmap {
    val url = URL(link)
    val connection = url.openConnection() as HttpURLConnection
    val input = connection.inputStream
    val buffImg = ImageIO.read(input)

    val stream = ByteArrayOutputStream()
    ImageIO.write(buffImg, "png", stream)
    val bytes = stream.toByteArray()

    return Image.makeFromEncoded(bytes).asImageBitmap()
}
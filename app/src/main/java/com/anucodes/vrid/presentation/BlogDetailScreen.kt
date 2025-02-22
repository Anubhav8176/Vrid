package com.anucodes.vrid.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil3.compose.AsyncImage
import com.anucodes.vrid.presentation.viewModel.AppViewModel
import com.anucodes.vrid.ui.theme.poppinsFamily
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SuppressLint("SetJavaScriptEnabled")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BlogDetailScreen(
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {

    val blog by appViewModel.blog.collectAsState()

    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        blog?.title?.rendered?.let {
            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = it,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontFamily = poppinsFamily,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier.height(10.dp))

        blog?.date?.let {
            Text(
                modifier = modifier
                    .padding(bottom = 10.dp),
                text = "Published: ${format(it)}",
                fontSize = 18.sp,
                fontFamily = poppinsFamily
            )
        }

        AndroidView(
            factory = {context->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webViewClient = WebViewClient()
                    blog?.content?.rendered?.let {
                        loadDataWithBaseURL(null, it, "text/html", "UTF-8", null)
                    }
                }
            },
            update = {webView->
                blog?.content?.rendered?.let {
                    webView.loadDataWithBaseURL(null, it, "text/html", "UTF-8", null)
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun format(dateTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' hh:mm a")

    val localDateTime = LocalDateTime.parse(dateTime, inputFormatter)
    return localDateTime.format(outputFormatter)
}
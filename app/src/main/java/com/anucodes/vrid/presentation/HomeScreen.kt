package com.anucodes.vrid.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anucodes.vrid.networking.model.Response
import com.anucodes.vrid.presentation.viewModel.AppViewModel
import com.anucodes.vrid.ui.theme.poppinsFamily
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val allBlogs by appViewModel.allBlogs.collectAsState()

    Column {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            text = "All Blogs",
            fontFamily = poppinsFamily,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(allBlogs){index, blog->
                BlogTile(
                    response = blog,
                    appViewModel = appViewModel,
                    navController = navController
                )

                if(index == allBlogs.size - 3){
                    appViewModel.getAllBlogs()
                }
            }
        }
    }

}

@Composable
fun BlogTile(
    response: Response,
    appViewModel: AppViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(15.dp),
                color = Color.Black
            ),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        onClick = {
            appViewModel.getBlog(response.id)
            navController.navigate("details")
        }
    ){
        Column(
            modifier = modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {

            AsyncImage(
                modifier = modifier
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(15.dp),
                        color = Color.Black
                    ),
                model = response.jetpack_featured_media_url,
                contentDescription = ""
            )
            Spacer(modifier.height(10.dp))
            Text(
                text = response.title.rendered,
                fontFamily = poppinsFamily,
                fontSize = 18.sp,
                maxLines = 2
            )
            Spacer(modifier.height(8.dp))
            Text(
                text = response.status,
                fontSize = 17.sp,
                fontFamily = poppinsFamily
            )
        }
    }
}

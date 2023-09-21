package com.example.compose_myalbum

import android.Manifest
import android.app.Application
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import kotlin.math.absoluteValue


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MainViewModel>()
            var isGranted by remember { mutableStateOf(false) }


            val permission = if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }


            val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()){granted ->
                    isGranted = granted
                }

            if(ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED) {
                    isGranted = true
            }

            if (isGranted){
                viewModel.fetchPhotos()
                HomeScreen(photoUris = viewModel.photoUris.value)
            }else{
                PermissionRequestScreen {
                    launcher.launch(permission)
                }
            }

        }
    }
}

class MainViewModel(application: Application) : AndroidViewModel(application){
    private val _photoUris = mutableStateOf(emptyList<Uri>())
    val photoUris : State<List<Uri>> = _photoUris

    fun fetchPhotos(){
        val uris = mutableListOf<Uri>()
        getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"
        )?.use {cursor ->
           val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()){
                val id = cursor.getLong(idIndex)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                 )
                uris.add(contentUri)
            }
        }
        _photoUris.value = uris
    }
}

@Composable
fun PermissionRequestScreen(onClick : () -> Unit){
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text("권한이 허용되지않았습니다.")
        Button(onClick = {onClick() }) {
            Text(text = "권한 요청")

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(photoUris: List<Uri>){
    val pagerState = rememberPagerState(pageCount = {photoUris.size})

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .fillMaxSize(),
        ) {pageIndex ->
            Card (
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - pageIndex) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                         lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also {scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                    }
            ){
                AsyncImage(
                    model = photoUris[pageIndex],
                    contentDescription = "Images",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Row(
            modifier = Modifier
                .height(50.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ){
            repeat(photoUris.size) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(20.dp)

                )
            }

        }
    }
}




package com.example.etheleduapp.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.etheleduapp.helper.rememberAssetImage


@Composable

fun ImageDisplayScreen(context: Context, folder: String, imageName: String) {
    // Construct the path relative to the assets folder
    // Example: "1/my_image.png"
    val assetPath = "$folder/$imageName"
    val imageBitmap = rememberAssetImage(context, assetPath)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Viewing Folder: $folder")

        Spacer(modifier = Modifier.height(20.dp))

        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Image from assets",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Text(text = "Image not found at: assets/$assetPath")
        }
    }
}


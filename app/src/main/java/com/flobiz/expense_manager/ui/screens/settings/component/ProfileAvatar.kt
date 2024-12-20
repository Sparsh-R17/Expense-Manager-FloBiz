package com.flobiz.expense_manager.ui.screens.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.flobiz.expense_manager.ui.theme.ColorOnPrimary
import com.flobiz.expense_manager.ui.theme.ColorPrimary
import com.flobiz.expense_manager.ui.theme.ColorSecondary

@Composable
fun ProfileAvatar(
    imageUrl: String?,
    modifier: Modifier=Modifier
){
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(ColorSecondary)
            .border(1.dp, ColorPrimary.copy(alpha = 0.2f), CircleShape),
        contentAlignment = Alignment.Center
    ){
        if(imageUrl!=null){
            AsyncImage(
                model = imageUrl,
                contentDescription = "ProfilePic",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }else{
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Icon",
                tint = ColorOnPrimary,
                modifier = Modifier.size(42.dp)
            )
        }
    }

}
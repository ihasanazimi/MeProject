package com.example.compose.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOutlinedInputTextField(
    hintText: String,
    inputType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    defaultTextValue: String = ""
) {
    var text by remember { mutableStateOf(defaultTextValue) }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        value = text,
        keyboardOptions = inputType,
        onValueChange = { text = it },
        label = { Text(hintText) }
    )
}


@Composable
fun MyCaptionTextFiled(captionText: String = "", modifier: Modifier = Modifier) {
    Text(
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
        fontWeight = FontWeight.Thin,
        text = captionText,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}


@Composable
fun MyPrimaryButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit
) {
    ElevatedButton(
        modifier = modifier,
        onClick = { onClickListener.invoke() }) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            text = buttonText
        )
    }
}


@Composable
fun MyTitleTextFiled(titleText: String = "", modifier: Modifier = Modifier) {
    Text(
        fontStyle = FontStyle.Normal,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        text = titleText,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}


@Composable
fun MyHeader(titleText : String ,modifier: Modifier) {
    Box(modifier) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable {}
        ) {
            Icon(
                imageVector = Icons.Rounded.Settings,
                contentDescription = "Logo",
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }

        Text(
            fontStyle = FontStyle.Normal,
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            text = titleText,
            modifier = Modifier.align(
                Alignment.Center
            )
        )

    }
}


@Composable
fun CircularImage(source : Painter, modifier: Modifier = Modifier){
    Image(
        painter = source,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}


package com.example.groovyspotify.common.composable

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groovyspotify.R
import font.helveticaFamily

@Composable
fun CommonButton(text: String, modifier: Modifier,color: ButtonColors,enabled: Boolean, action: () -> Unit){
    Button(
        onClick =action
        ,
        colors = color,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        CommonText(text = text,modifier = Modifier)
    }
}


@Composable
fun UniversalButton(modifier: Modifier, data: Any,onClick: () -> Unit ) {

    var colorChange by rememberSaveable { mutableStateOf(false) }


    Button(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        onClick = {
            colorChange = !colorChange

                onClick.invoke()
        },
        colors = ButtonDefaults
            .buttonColors(

                backgroundColor = if (colorChange) Color.White else Color.DarkGray,
                contentColor = if (colorChange) Color.DarkGray else Color.White


            )
    ) {

        Text(
            text = data.toString(),
            fontSize = 16.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium
        )

    }
}

@Composable
fun BackButtonComposable() {

    Icon(
        modifier = Modifier
//            .align(Alignment.TopStart)
            .padding(18.dp)
            .size(24.dp)
            .clickable {
//                navController.popBackStack()
            },
        painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
        contentDescription = "Back button",
        tint = Color(0xFFFF5722)
    )
}
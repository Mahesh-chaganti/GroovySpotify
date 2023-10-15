package com.example.groovyspotify.ui.genderanddob

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.common.composable.BasicFieldComposable
import com.example.groovyspotify.common.composable.BasicKeyboardFieldComposable
import com.example.groovyspotify.common.composable.ColoredText
import com.example.groovyspotify.common.composable.CommonProgressSpinner
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.common.composable.ExtraBoldText

@Composable
fun GenderAndDobScreen(genderAndDobViewModel: GenderAndDobViewModel,
                       openAndPopUp:(String,String) -> Unit) {

    val uiState by genderAndDobViewModel.uiState
    val loading by genderAndDobViewModel.inProgress

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            Modifier
                .padding(20.dp)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExtraBoldText(
                text = "A little more details",
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            PhotoScreen(genderAndDobViewModel = genderAndDobViewModel)
            Row(
                Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Age", Modifier.weight(0.7f))
                BasicKeyboardFieldComposable(
                    text = R.string.age,
                    value = uiState.age,
                    onNewValue = { genderAndDobViewModel.onAgeChange(age = it)},
                    modifier = Modifier
                        .weight(0.3f)
                        .wrapContentHeight(),
                    keyboardType = KeyboardType.Number

                    )
            }
            Row(
                Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(text = "Gender", Modifier.weight(0.7f))
                BasicKeyboardFieldComposable(
                    text = R.string.gender,
                    value = uiState.gender,
                    onNewValue =  genderAndDobViewModel::onGenderChange ,
                    modifier = Modifier
                        .weight(0.3f)
                        .wrapContentHeight(),
                    keyboardType = KeyboardType.Text
                    )
            }



        }
        ColoredText(
            text = "Next >",
            color = Color(0xFFFF5722),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            //send language and artist data
            // navigate to next screen
            genderAndDobViewModel.onNextClick(openAndPopUp)


        }
        if(loading)
            CommonProgressSpinner()
    }

}

@Composable
fun PhotoScreen(genderAndDobViewModel: GenderAndDobViewModel) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let {
            genderAndDobViewModel.onPhotoUploadClick(uri = uri)
        }
    }
    Card(
        modifier = Modifier
            .size(500.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.White)
    )
    {
        if(!genderAndDobViewModel.uiState.value.profilePhotoUrl.isNullOrEmpty())
        AsyncImage(
            model =  genderAndDobViewModel.uiState.value.profilePhotoUrl ,
            contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()

        )
        else
        {
            Image(imageVector =  Icons.Filled.AccountBox,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        launcher.launch("image/*")
                    })
        }
    }
}

//@Preview
//@Composable
//fun GenderAndDobScreenPreview() {
//    GenderAndDobScreen()
//}
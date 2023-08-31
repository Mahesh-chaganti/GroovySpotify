package com.example.groovyspotify.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groovyspotify.R
import font.helveticaFamily
import com.example.groovyspotify.R.string as AppText

@Composable
fun BasicFieldComposable(
    @StringRes text: Int,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onNewValue,
        label = {
            CommonText(text = stringResource(id = text), modifier = Modifier)
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = commonTextFieldColors()
    )

}


@Composable
fun PasswordFieldComposable(value: String, onNewValue: (String) -> Unit, modifier: Modifier) {
    PasswordField(value, AppText.password, onNewValue, modifier)
}

@Composable
fun RepeatPasswordFieldComposable(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier
) {
    PasswordField(value, AppText.repeat_password, onNewValue, modifier)
}

@Composable
private fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(R.drawable.round_visibility_24)
        else painterResource(R.drawable.round_visibility_off_24)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        value = value,
        onValueChange = onNewValue,
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    painter = icon,
                    contentDescription = if (isVisible) "Hide Password" else "Show Password"
                )
            }
        },
        label = {
            CommonText(text = stringResource(id = placeholder), modifier = Modifier)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = commonTextFieldColors()
    )
}

@Composable
fun CommonText(text: String,modifier: Modifier){
    Text(
        text = text,
        fontSize = 18.sp,
        fontFamily = helveticaFamily,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Medium,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
fun ExtraBoldText(text: String,modifier: Modifier){
    Text(
        text = text,
        fontSize = 36.sp,
        fontFamily = helveticaFamily,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
fun ColoredText(text: String,  color: Color,modifier:Modifier,onClick:()->Unit) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontFamily = helveticaFamily,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Medium,
        color = color,
        modifier = modifier
            .clickable
            {
               onClick.invoke()
            }

    )
}

@Composable
fun commonTextFieldColors(): TextFieldColors{

     return TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = Color.White,
        textColor = Color.White,
        backgroundColor = Color.DarkGray,
        focusedBorderColor = Color(0xFF0890CD),
        unfocusedBorderColor = Color.White,
        disabledTextColor = Color.White,
        focusedLabelColor = Color(0xFF0890CD),
        placeholderColor = Color.White

    )
}


@Composable
fun EmailFieldComposable(value: String, onNewValue: (String) -> Unit, modifier: Modifier ) {
    OutlinedTextField(
        value = value,
        onValueChange = onNewValue,
        label = {
            CommonText(text = "Email", modifier = Modifier)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = commonTextFieldColors()

    )
}



package font

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.groovyspotify.R

val helveticaFamily = FontFamily(
    Font(R.font.helvetica_ce_medium, FontWeight.Medium),
    Font(R.font.helvetica_ce_bold, FontWeight.Bold),
    Font(R.font.helvetica_romanitalic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.helvetica_roman, FontWeight.Medium),

)
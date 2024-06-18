package ir.ha.meproject.ui.compose.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.util.CircularImage
import com.example.compose.util.MyCaptionTextFiled
import com.example.compose.util.MyHeader
import com.example.compose.util.MyOutlinedInputTextField
import com.example.compose.util.MyPrimaryButton
import com.example.compose.util.MyTitleTextFiled
import ir.ha.meproject.R


@Preview
@Composable
fun Profile() {

    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state)
            .background(MaterialTheme.colorScheme.background)
    ) {

        MyHeader(
            titleText = "Profile",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .height(56.dp)
                .align(Alignment.Start),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            CircularImage(
                source = painterResource(id = R.drawable.baseline_account_circle_24),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(116.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
            )
        }

        MyTitleTextFiled("Hasan Azimi", modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
        MyCaptionTextFiled("@ihasanazimi", modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.padding(0.dp, 16.dp))

        MyOutlinedInputTextField("YourEmail", KeyboardOptions(keyboardType = KeyboardType.Email))
        MyOutlinedInputTextField("Phone Number", KeyboardOptions(keyboardType = KeyboardType.Phone))
        MyOutlinedInputTextField("Postal Code", KeyboardOptions(keyboardType = KeyboardType.Number))

        MyOutlinedInputTextField(
            hintText = "Password",
            inputType = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )

        Spacer(modifier = Modifier.padding(0.dp, 8.dp))

        MyPrimaryButton(
            buttonText = "Submit",
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .height(56.dp)
                .padding(16.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
        ) {

        }
    }
}
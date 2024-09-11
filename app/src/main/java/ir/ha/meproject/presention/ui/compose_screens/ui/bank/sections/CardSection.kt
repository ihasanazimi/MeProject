package com.example.compose.ui.bank.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.ha.meproject.R
import ir.ha.meproject.ui.compose.theme.BlueEnd
import ir.ha.meproject.ui.compose.theme.BlueStart
import ir.ha.meproject.ui.compose.theme.GreenEnd
import ir.ha.meproject.ui.compose.theme.GreenStart
import ir.ha.meproject.ui.compose.theme.OrangeEnd
import ir.ha.meproject.ui.compose.theme.OrangeStart
import ir.ha.meproject.ui.compose.theme.PurpleEdn
import ir.ha.meproject.ui.compose.theme.PurpleStart

val cardItems = listOf(
    Card(
        cardType = "VISA",
        cardNumber = "5894 6315 6004 3432",
        cardName = "Business",
        balance = 46.987,
        color = getGradient(PurpleStart, PurpleEdn)
    ),
    Card(
        cardType = "MASTER CARD",
        cardNumber = "9374 6315 6004 3432",
        cardName = "Saving",
        balance = 23.987,
        color = getGradient(BlueStart, BlueEnd)
    ),
    Card(
        cardType = "VISA",
        cardNumber = "5022 6315 6004 3432",
        cardName = "School",
        balance = 14.987,
        color = getGradient(GreenStart, GreenEnd)
    ),
    Card(
        cardType = "VISA",
        cardNumber = "8264 6315 6004 3432",
        cardName = "Trips",
        balance = 97.987,
        color = getGradient(OrangeStart, OrangeEnd)
    ),
)


fun getGradient(startColor: Color, endColor: Color): Brush {
    return Brush.horizontalGradient(colors = listOf(startColor, endColor))
}


data class Card(
    val cardType: String,
    val cardNumber: String,
    val cardName: String,
    val balance: Double,
    val color: Brush
)


@Preview
@Composable
fun CardsSection() {
    LazyRow {
        items(cardItems.size) { index ->
            CardItem(index)
        }
    }
}


@Composable
fun CardItem(index: Int) {
    val card = cardItems[index]
    var lastItemPadding = 0.dp
    if (index == cardItems.size - 1) {
        lastItemPadding = 16.dp
    }

    var image = painterResource(id = R.drawable.visa)
    if (card.cardType == "MASTER CARD") {
        image = painterResource(id = R.drawable.credit_card)
    }

    Box(modifier = Modifier.padding(start = 16.dp, end = lastItemPadding)) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(card.color)
                .width(250.dp)
                .height(160.dp)
                .clickable { }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = image,
                contentDescription = card.cardName,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = card.cardName,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White
            )

            Text(
                text = card.balance.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White
            )

            Text(
                text = card.cardNumber,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White
            )


        }
    }

}



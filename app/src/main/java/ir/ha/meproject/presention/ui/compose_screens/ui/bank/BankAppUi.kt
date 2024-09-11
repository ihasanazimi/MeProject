package ir.ha.meproject.presention.ui.compose_screens.ui.bank

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.bank.sections.CardsSection
import ir.ha.meproject.presention.ui.compose_screens.ui.bank.sections.CurrenciesSection
import com.example.compose.ui.bank.sections.FinanceSection
import com.example.compose.ui.bank.sections.WalletSection


@Preview
@Composable

fun BankUi() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        HomeScreen()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        WalletSection()
        CardsSection()
        Spacer(modifier = Modifier.height(16.dp))
        FinanceSection()
        CurrenciesSection()
    }
}

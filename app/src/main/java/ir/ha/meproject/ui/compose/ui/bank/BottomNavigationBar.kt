package ir.ha.meproject.ui.compose.ui.bank

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ir.ha.meproject.ui.compose.graph.ACCOUNT
import ir.ha.meproject.ui.compose.graph.HOME
import ir.ha.meproject.ui.compose.graph.NOTIFICATION
import ir.ha.meproject.ui.compose.graph.WALLET

data class BottomNavItem(val id: String, val title: String, val icon: ImageVector)

val bottomNavItems = listOf(
    BottomNavItem(
        id = HOME,
        title = "Home",
        icon = Icons.Rounded.Home
    ),
    BottomNavItem(
        id = WALLET,
        title = "Wallet",
        icon = Icons.Rounded.Wallet
    ),
    BottomNavItem(
        id = NOTIFICATION,
        title = "Notification",
        icon = Icons.Rounded.Notifications
    ),
    BottomNavItem(
        id = ACCOUNT,
        title = "Account",
        icon = Icons.Rounded.AccountCircle
    )
)


@Preview(showSystemUi = true)
@Composable
fun BottomNavigation(currentItem : String? , onItemSelectCallBack : (String?) -> Unit) {
    var activeItem by remember { mutableStateOf(currentItem) }
    NavigationBar(modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
        Row() {
            bottomNavItems.forEachIndexed { index, bottomNavItem ->
                NavigationBarItem(
                    selected = activeItem == bottomNavItem.id,
                    onClick = {
                        activeItem = bottomNavItem.id
                        onItemSelectCallBack.invoke(activeItem)

                    }, icon = {
                        Icon(
                            imageVector = bottomNavItem.icon,
                            contentDescription = bottomNavItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }, label = {
                        Text(
                            text = bottomNavItem.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}
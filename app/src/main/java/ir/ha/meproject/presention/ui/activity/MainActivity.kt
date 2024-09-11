package ir.ha.meproject.presention.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.ha.meproject.presention.ui.compose_screens.graph.ACCOUNT
import ir.ha.meproject.presention.ui.compose_screens.graph.HOME
import ir.ha.meproject.presention.ui.compose_screens.theme.ComposeTheme
import ir.ha.meproject.presention.ui.compose_screens.ui.bank.BankUi
import ir.ha.meproject.presention.ui.compose_screens.ui.bank.BottomNavigation
import ir.ha.meproject.presention.ui.compose_screens.ui.profile.Profile

class MainActivity : ComponentActivity() {


    private val TAG = MainActivity::class.java.simpleName

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface {
                ComposeTheme {

                    val navController = rememberNavController()
                    val lastItemOfBottomBar = remember { mutableStateOf(HOME) }
                    val currentRoute =navController.currentBackStackEntryAsState().value?.destination?.route

                    Scaffold(bottomBar = {
                        BottomNavigation(currentRoute) { route ->
                            Log.i(TAG, "onCreate : route -> $route")
                            runCatching {
                                if (route != lastItemOfBottomBar.value) {
                                    navController.navigate(route!!)
                                }
                                lastItemOfBottomBar.value = route
                            }
                        }
                    }) { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            SetStatusColor(color = MaterialTheme.colorScheme.background)
                            NavHost(navController = navController, startDestination = HOME) {
                                composable(route = HOME) { BankUi() }
                                composable(route = ACCOUNT) { Profile() }
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun SetStatusColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(color = color)
        }
    }

}
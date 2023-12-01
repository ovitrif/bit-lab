package tech.masivo.bitlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.masivo.bitlab.ui.BlockScreen
import tech.masivo.bitlab.ui.HomeScreen
import tech.masivo.bitlab.ui.HomeViewModel
import tech.masivo.bitlab.ui.theme.BitlabTheme

object Routes {
    const val Home = "home"
    const val Block = "block"
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BitlabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.Home) {
                        composable(Routes.Home) {
                            HomeScreen(
                                uiState = uiState.value,
                                onNavigateToBlock = {
                                    navController.navigate("${Routes.Block}/id=$it")
                                }
                            )
                        }
                        composable("${Routes.Block}/id={id}") { backStackEntry ->
                            backStackEntry.arguments?.getString("id")?.let {
                                BlockScreen(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.mad.e.dec2022twcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import com.mad.e.dec2022twcapp.MainActivityViewModel.UiState
import com.mad.e.dec2022twcapp.MainActivityViewModel.UiState.Loading
import com.mad.e.dec2022twcapp.MainActivityViewModel.UiState.Success
import com.mad.e.dec2022twcapp.navigation.BaseViewModelFactoryProvider
import com.mad.e.dec2022twcapp.navigation.TemplateNaveHost
import com.mad.e.dec2022twcapp.theme.TemplateTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider : BaseViewModelFactoryProvider

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: UiState = Loading

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                Loading -> true
                Success -> false
            }
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }.collect()
            }
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = isSystemInDarkTheme()

            // Update the dark content of the system bars to match the theme
            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.setSystemBarsColor(Color.Transparent)
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }

            TemplateTheme { TemplateNaveHost() }
        }
    }

    @Composable
    fun SplashScreen(navController: NavController) = Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.tws_icon),
            contentDescription = "",
            alignment = Alignment.Center, modifier = Modifier
                .fillMaxSize().padding(40.dp)
        )

        Text(
            text = "Version - ${BuildConfig.VERSION_NAME}",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        )
    }
}

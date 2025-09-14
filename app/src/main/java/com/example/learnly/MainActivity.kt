package com.example.learnly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learnly.ui.theme.LearnlyTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnlyTheme {

                val navController = rememberNavController()
                val startDestination = remember {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null && user.isEmailVerified) {
                        "BottomNav"
                    } else {
                        "onboarding"
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = { fadeIn(animationSpec = tween(100)) },
                        exitTransition = { fadeOut(animationSpec = tween(100)) },
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(
                                onLoginClick = { navController.navigate("login") { popUpTo("onboarding") { inclusive = true } } },
                                signupClick = { navController.navigate("signup") { popUpTo("onboarding") { inclusive = true } } }
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                onBoardingClick = { navController.navigate("onboarding") { popUpTo("login") { inclusive = true } } },
                                signupClick = { navController.navigate("signup") { popUpTo("login") { inclusive = true } } },
                                homeClick = { navController.navigate("BottomNav") { popUpTo("login") { inclusive = true } }
                                }
                            )
                        }
                        composable("signup") {
                            SignupScreen(
                                loginClick = { navController.navigate("login") { popUpTo("signup") { inclusive = true } } },
                                loginClick1 = { navController.navigate("login") { popUpTo("signup") { inclusive = true } } }
                            )
                        }
                        composable("BottomNav") { BottomNavScreen(rootNavController = navController) }
                    }
                }
            }
        }
    }
}


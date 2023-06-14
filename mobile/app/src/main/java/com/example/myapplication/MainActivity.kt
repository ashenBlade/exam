package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.infrastructure.AppState
import com.example.myapplication.infrastructure.AppStateMachine
import com.example.myapplication.infrastructure.Routes
import com.example.myapplication.screens.all_items.AllItemsPage
import com.example.myapplication.screens.create.CreateItemPage
import com.example.myapplication.screens.edit.EditItemPage
import com.example.myapplication.screens.remove.RemoveItemPage
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                val machine = AppStateMachine()
                val controller = rememberNavController()
                Scaffold(bottomBar = {
                    Row {
                        Button(onClick = { machine.update(AppState.AllItemsPage) }) {
                            Text(text = "all")
                        }
                        Button(onClick = { machine.update(AppState.CreateItemPage) }) {
                            Text("create")
                        }
                        Button(onClick = { machine.update(AppState.RemoveItemPage) }) {
                            Text("remove")
                        }
                        Button(onClick = { machine.update(AppState.EditItemPage) }) {
                            Text("edit")
                        }
                    }
                }) {
                    val bottom = it.calculateBottomPadding()
                    val top = it.calculateTopPadding()
                    val startPage = Routes.allItems
                    NavHost(navController = controller, startDestination = startPage,
//                        modifier = Modifier.padding(top = top, bottom = bottom)
                    ) {
                        composable(Routes.allItems) {
                            AllItemsPage(viewModel = hiltViewModel())
                        }
                        composable(Routes.edit) {
                            EditItemPage(viewModel = hiltViewModel())
                        }
                        composable(Routes.create) {
                            CreateItemPage(viewModel = hiltViewModel())
                        }
                        composable(Routes.remove) {
                            RemoveItemPage(viewModel = hiltViewModel())
                        }
                    }
                    machine.currentState.observe(this) {
                        when (it) {
                            AppState.AllItemsPage -> controller.navigate(Routes.allItems)
                            AppState.RemoveItemPage -> controller.navigate(Routes.remove)
                            AppState.CreateItemPage -> controller.navigate(Routes.create)
                            AppState.EditItemPage -> controller.navigate(Routes.edit)
                        }
                    }
                }
            }
        }
    }
}

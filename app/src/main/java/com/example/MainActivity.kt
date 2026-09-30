package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.data.db.AppDatabase
import com.example.data.repository.ChecklistRepository
import com.example.ui.screens.MainScreen
import com.example.ui.theme.ChecklistOpsTheme
import com.example.ui.viewmodel.ChecklistViewModel
import com.example.ui.viewmodel.ChecklistViewModelFactory

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val database = AppDatabase.getDatabase(applicationContext)
    val repository = ChecklistRepository(database.checklistDao())
    val factory = ChecklistViewModelFactory(repository)
    val viewModel by viewModels<ChecklistViewModel> { factory }

    setContent {
      ChecklistOpsTheme {
        MainScreen(viewModel = viewModel)
      }
    }
  }
}

package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.example.ui.components.AppBottomBar
import com.example.ui.components.CalendarDatePickerDialog
import com.example.ui.components.ChannelTabsBar
import com.example.ui.components.ChecklistHeader
import com.example.ui.components.ItemDetailSheet
import com.example.ui.viewmodel.AppBottomTab
import com.example.ui.viewmodel.ChecklistViewModel

@Composable
fun MainScreen(viewModel: ChecklistViewModel) {
  val context = LocalContext.current
  val currentChannel by viewModel.currentChannel.collectAsState()
  val selectedDate by viewModel.selectedDate.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val activeFilter by viewModel.activeFilter.collectAsState()
  val currentTab by viewModel.currentTab.collectAsState()
  val editingItem by viewModel.editingItem.collectAsState()
  val showCalendarDialog by viewModel.showCalendarDialog.collectAsState()

  val items by viewModel.filteredItems.collectAsState()
  val counts by viewModel.filterCounts.collectAsState()
  val channelSummaries by viewModel.channelSummaries.collectAsState()
  val activityLogs by viewModel.activityLogs.collectAsState()

  // Handle system back navigation nicely
  BackHandler(enabled = editingItem != null || showCalendarDialog || searchQuery.isNotEmpty() || currentTab != AppBottomTab.CHECKLIST) {
    when {
      editingItem != null -> viewModel.closeItemDetail()
      showCalendarDialog -> viewModel.setCalendarDialogVisible(false)
      searchQuery.isNotEmpty() -> viewModel.setSearchQuery("")
      currentTab != AppBottomTab.CHECKLIST -> viewModel.setTab(AppBottomTab.CHECKLIST)
    }
  }

  Scaffold(
    bottomBar = {
      AppBottomBar(
        currentTab = currentTab,
        onTabSelected = { viewModel.setTab(it) }
      )
    },
    modifier = Modifier
      .fillMaxSize()
      .testTag("app_main_scaffold")
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF4F6F9))
    ) {
      // Header matching image & HTML
      ChecklistHeader(
        onBackClick = {
          if (currentTab != AppBottomTab.CHECKLIST) {
            viewModel.setTab(AppBottomTab.CHECKLIST)
          } else {
            Toast.makeText(context, "Channel checklist is at root level", Toast.LENGTH_SHORT).show()
          }
        },
        onProfileClick = {
          Toast.makeText(context, "Operator: SY · Shift: Day Broadcast Ops", Toast.LENGTH_SHORT).show()
        }
      )

      // Channel navigation tabs
      ChannelTabsBar(
        selectedChannel = currentChannel,
        onChannelSelected = { channel ->
          viewModel.selectChannel(channel)
          if (currentTab != AppBottomTab.CHECKLIST) {
            viewModel.setTab(AppBottomTab.CHECKLIST)
          }
        }
      )

      // Main Content Screen depending on active Bottom Tab
      Box(modifier = Modifier.weight(1f)) {
        when (currentTab) {
          AppBottomTab.CHECKLIST -> {
            ChecklistScreen(
              channel = currentChannel,
              dateText = viewModel.getDisplayDate(),
              items = items,
              counts = counts,
              searchQuery = searchQuery,
              onSearchQueryChanged = { viewModel.setSearchQuery(it) },
              activeFilter = activeFilter,
              onFilterSelected = { viewModel.setFilter(it) },
              onOpenCalendar = { viewModel.setCalendarDialogVisible(true) },
              onItemClicked = { viewModel.openItemDetail(it) },
              onCycleStatus = { viewModel.cycleItemStatus(it) }
            )
          }
          AppBottomTab.ACTIVITY -> {
            ActivityScreen(logs = activityLogs)
          }
          AppBottomTab.CHANNELS -> {
            ChannelsScreen(
              channelSummaries = channelSummaries,
              onSwitchChannel = { channel ->
                viewModel.selectChannel(channel)
                viewModel.setTab(AppBottomTab.CHECKLIST)
              },
              onMarkAllChecked = { channel ->
                viewModel.selectChannel(channel)
                viewModel.markCurrentChannelComplete()
                Toast.makeText(context, "$channel checklist marked as verified", Toast.LENGTH_SHORT).show()
              }
            )
          }
          AppBottomTab.HISTORY -> {
            HistoryScreen(
              selectedDate = viewModel.getDisplayDate(),
              channelSummaries = channelSummaries,
              onDateSelected = { viewModel.selectDate(it) }
            )
          }
        }
      }
    }

    // Interactive Date Picker Dialog Modal
    if (showCalendarDialog) {
      CalendarDatePickerDialog(
        initialDate = selectedDate,
        onDismiss = { viewModel.setCalendarDialogVisible(false) },
        onDateSelected = { newDate ->
          viewModel.selectDate(newDate)
        }
      )
    }

    // Detail Bottom Sheet Drawer
    editingItem?.let { item ->
      ItemDetailSheet(
        item = item,
        onDismiss = { viewModel.closeItemDetail() },
        onSave = { newStatus, notes ->
          viewModel.updateItemStatus(item, newStatus, notes)
          Toast.makeText(context, "Item updated to ${newStatus.displayTitle}", Toast.LENGTH_SHORT).show()
        }
      )
    }
  }
}

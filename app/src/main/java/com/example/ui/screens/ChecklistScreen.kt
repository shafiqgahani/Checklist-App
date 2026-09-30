package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChecklistItemEntity
import com.example.ui.components.ChecklistFilterBar
import com.example.ui.components.ChecklistItemCard
import com.example.ui.components.ChecklistTableHeader
import com.example.ui.components.ScheduleDateCard
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.viewmodel.FilterCounts

@Composable
fun ChecklistScreen(
  channel: String,
  dateText: String,
  items: List<ChecklistItemEntity>,
  counts: FilterCounts,
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  activeFilter: String,
  onFilterSelected: (String) -> Unit,
  onOpenCalendar: () -> Unit,
  onItemClicked: (ChecklistItemEntity) -> Unit,
  onCycleStatus: (ChecklistItemEntity) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF4F6F9))
      .testTag("checklist_main_screen")
  ) {
    // Quick Action & Date Selector Card
    ScheduleDateCard(
      channel = channel,
      dateText = dateText,
      onOpenCalendar = onOpenCalendar
    )

    // Filter & Search Bar
    ChecklistFilterBar(
      searchQuery = searchQuery,
      onSearchQueryChanged = onSearchQueryChanged,
      activeFilter = activeFilter,
      onFilterSelected = onFilterSelected,
      counts = counts
    )

    // Column Headers (ITEM, ASSIGNMENT, STATUS)
    ChecklistTableHeader()

    // Checklist Scrollable Feed
    if (items.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(top = 40.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.Assignment,
            contentDescription = null,
            tint = Slate400,
            modifier = Modifier.size(40.dp)
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "No items match your criteria",
            color = Slate500,
            fontSize = 12.sp
          )
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(bottom = 8.dp)
          .testTag("checklist_feed_container"),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        items(items, key = { it.id }) { checklistItem ->
          ChecklistItemCard(
            item = checklistItem,
            onCardClick = { onItemClicked(checklistItem) },
            onCycleStatus = { onCycleStatus(checklistItem) }
          )
        }
      }
    }
  }
}

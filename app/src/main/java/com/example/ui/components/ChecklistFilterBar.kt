package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.viewmodel.FilterCounts

@Composable
fun ChecklistFilterBar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  activeFilter: String,
  onFilterSelected: (String) -> Unit,
  counts: FilterCounts
) {
  val focusManager = LocalFocusManager.current

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    // Search Box
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(38.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(Color.White)
        .border(1.dp, Slate200, RoundedCornerShape(8.dp))
        .padding(horizontal = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        tint = Slate400,
        modifier = Modifier.size(16.dp)
      )

      Box(
        modifier = Modifier
          .weight(1f)
          .padding(horizontal = 8.dp)
      ) {
        if (searchQuery.isEmpty()) {
          Text(
            text = "Filter item name or assignment...",
            color = Slate400,
            fontSize = 12.sp
          )
        }
        BasicTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChanged,
          textStyle = TextStyle(
            color = Slate900,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          ),
          cursorBrush = SolidColor(BrandTeal),
          singleLine = true,
          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
          keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("item_search_input")
        )
      }

      if (searchQuery.isNotEmpty()) {
        Box(
          modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .clickable { onSearchQueryChanged("") }
            .testTag("clear_search_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Clear search",
            tint = Slate400,
            modifier = Modifier.size(14.dp)
          )
        }
      }
    }

    // Status Filter Pills
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      FilterPill(
        title = "All",
        count = counts.all,
        filterKey = "ALL",
        isSelected = activeFilter == "ALL",
        onSelect = { onFilterSelected("ALL") }
      )
      FilterPill(
        title = "Completed",
        count = counts.completed,
        filterKey = "Completed",
        isSelected = activeFilter == "Completed",
        onSelect = { onFilterSelected("Completed") }
      )
      FilterPill(
        title = "Review",
        count = counts.inReview,
        filterKey = "In review",
        isSelected = activeFilter == "In review",
        onSelect = { onFilterSelected("In review") }
      )
      FilterPill(
        title = "Attention",
        count = counts.attention,
        filterKey = "Needs attention",
        isSelected = activeFilter == "Needs attention",
        onSelect = { onFilterSelected("Needs attention") }
      )
      FilterPill(
        title = "Pending",
        count = counts.pending,
        filterKey = "Not started",
        isSelected = activeFilter == "Not started",
        onSelect = { onFilterSelected("Not started") }
      )
    }
  }
}

@Composable
private fun FilterPill(
  title: String,
  count: Int,
  filterKey: String,
  isSelected: Boolean,
  onSelect: () -> Unit
) {
  val bgColor = if (isSelected) BrandTeal else Color.White
  val textColor = if (isSelected) Color.White else Slate700
  val borderColor = if (isSelected) BrandTeal else Slate200

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(bgColor)
      .border(1.dp, borderColor, RoundedCornerShape(16.dp))
      .clickable { onSelect() }
      .padding(horizontal = 10.dp, vertical = 4.dp)
      .testTag("filter_pill_$filterKey"),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "$title ($count)",
      color = textColor,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold
    )
  }
}

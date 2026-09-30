package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandBorder
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.BrandTealSubtle
import com.example.ui.theme.Slate400
import com.example.ui.viewmodel.AppBottomTab

@Composable
fun AppBottomBar(
  currentTab: AppBottomTab,
  onTabSelected: (AppBottomTab) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White)
  ) {
    // Top Border
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(BrandBorder)
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 24.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      BottomNavItem(
        label = "Checklist",
        icon = Icons.Default.Add,
        isSelected = currentTab == AppBottomTab.CHECKLIST,
        hasPill = true,
        onClick = { onTabSelected(AppBottomTab.CHECKLIST) },
        testTag = "nav_tab_checklist"
      )

      BottomNavItem(
        label = "Activity",
        icon = Icons.Default.CheckCircle,
        isSelected = currentTab == AppBottomTab.ACTIVITY,
        onClick = { onTabSelected(AppBottomTab.ACTIVITY) },
        testTag = "nav_tab_activity"
      )

      BottomNavItem(
        label = "Channels",
        icon = Icons.Default.Layers,
        isSelected = currentTab == AppBottomTab.CHANNELS,
        onClick = { onTabSelected(AppBottomTab.CHANNELS) },
        testTag = "nav_tab_channels"
      )

      BottomNavItem(
        label = "History",
        icon = Icons.Default.History,
        isSelected = currentTab == AppBottomTab.HISTORY,
        onClick = { onTabSelected(AppBottomTab.HISTORY) },
        testTag = "nav_tab_history"
      )
    }
  }
}

@Composable
private fun BottomNavItem(
  label: String,
  icon: ImageVector,
  isSelected: Boolean,
  hasPill: Boolean = false,
  onClick: () -> Unit,
  testTag: String
) {
  val iconColor = if (isSelected) BrandTeal else Slate400
  val textColor = if (isSelected) BrandTeal else Slate400
  val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium

  Column(
    modifier = Modifier
      .clickable { onClick() }
      .padding(horizontal = 8.dp, vertical = 2.dp)
      .testTag(testTag),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(3.dp)
  ) {
    if (hasPill && isSelected) {
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(BrandTealSubtle),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = label,
          tint = iconColor,
          modifier = Modifier.size(15.dp)
        )
      }
    } else {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = iconColor,
        modifier = Modifier.size(20.dp)
      )
    }

    Text(
      text = label,
      color = textColor,
      fontSize = 10.sp,
      fontWeight = fontWeight
    )
  }
}

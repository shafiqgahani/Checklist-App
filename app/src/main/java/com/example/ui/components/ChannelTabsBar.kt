package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.DefaultChecklistData
import com.example.ui.theme.BrandBorder
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Slate700

@Composable
fun ChannelTabsBar(
  selectedChannel: String,
  onChannelSelected: (String) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White)
      .padding(horizontal = 16.dp, vertical = 10.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    DefaultChecklistData.CHANNELS.forEach { channel ->
      val isSelected = channel.equals(selectedChannel, ignoreCase = true)
      val bgColor = if (isSelected) BrandNavy else Color(0xFFF1F4F8)
      val textColor = if (isSelected) Color.White else Slate700

      Box(
        modifier = Modifier
          .weight(1f)
          .height(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(bgColor)
          .clickable { onChannelSelected(channel) }
          .testTag("channel_tab_$channel"),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = channel,
          color = textColor,
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp
        )
      }
    }
  }

  // Subtle bottom border line
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(1.dp)
      .background(BrandBorder)
  )
}

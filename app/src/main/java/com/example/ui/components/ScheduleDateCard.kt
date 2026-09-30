package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandBorder
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Sky100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900

@Composable
fun ScheduleDateCard(
  channel: String,
  dateText: String,
  onOpenCalendar: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 10.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(Color.White)
      .border(1.dp, BrandBorder, RoundedCornerShape(12.dp))
      .padding(horizontal = 14.dp, vertical = 12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Left info: Channel Badge & Description
      Row(
        modifier = Modifier.weight(1f, fill = false),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Channel Tag
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Sky100)
            .padding(horizontal = 8.dp, vertical = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = channel,
            color = BrandNavy,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp
          )
        }

        Column(
          modifier = Modifier.padding(end = 8.dp)
        ) {
          Text(
            text = "Daily playlist checklist",
            color = Slate900,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = "Tap channel tabs to update assignments...",
            color = Slate500,
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      // Right: Date Selector Trigger Button
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(Color(0xFFF8FAFC))
          .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
          .clickable { onOpenCalendar() }
          .padding(horizontal = 10.dp, vertical = 6.dp)
          .testTag("date_selector_button"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Column {
          Text(
            text = "SCHEDULE DATE",
            color = Slate400,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
          Text(
            text = dateText,
            color = Slate800,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Icon(
          imageVector = Icons.Default.KeyboardArrowDown,
          contentDescription = "Select Date",
          tint = Slate500,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}

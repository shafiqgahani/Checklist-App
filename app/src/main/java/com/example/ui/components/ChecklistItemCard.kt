package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChecklistItemEntity
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate900

@Composable
fun ChecklistTableHeader() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "ITEM",
      color = Slate400,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 0.8.sp,
      modifier = Modifier.weight(0.35f)
    )
    Text(
      text = "ASSIGNMENT",
      color = Slate400,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 0.8.sp,
      modifier = Modifier
        .weight(0.40f)
        .padding(horizontal = 4.dp)
    )
    Text(
      text = "STATUS",
      color = Slate400,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 0.8.sp,
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.25f)
    )
  }
}

@Composable
fun ChecklistItemCard(
  item: ChecklistItemEntity,
  onCardClick: () -> Unit,
  onCycleStatus: () -> Unit
) {
  val statusConfig = item.itemStatus

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(Color.White)
      .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
      .clickable { onCardClick() }
      .testTag("checklist_card_${item.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      // Left 4dp accent color bar
      Box(
        modifier = Modifier
          .width(4.dp)
          .height(72.dp)
          .background(statusConfig.accentColor)
      )

      // Card Content (3 columns: Item, Assignment, Status)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Col 1: Item Title (Weight ~0.35)
        Column(
          modifier = Modifier
            .weight(0.35f)
            .padding(end = 6.dp)
        ) {
          Text(
            text = item.item,
            color = Slate900,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 15.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
        }

        // Col 2: Assignment Description (Weight ~0.40)
        Column(
          modifier = Modifier
            .weight(0.40f)
            .padding(horizontal = 4.dp)
        ) {
          Text(
            text = item.assignment,
            color = Slate500,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
        }

        // Col 3: Status Button (Weight ~0.25, right aligned)
        Box(
          modifier = Modifier.weight(0.25f),
          contentAlignment = Alignment.CenterEnd
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(statusConfig.bgColor)
              .border(1.dp, statusConfig.borderColor, RoundedCornerShape(6.dp))
              .clickable { onCycleStatus() }
              .padding(horizontal = 8.dp, vertical = 5.dp)
              .testTag("status_toggle_${item.id}"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = statusConfig.badgeLabel,
              color = statusConfig.textColor,
              fontSize = 10.sp,
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 0.5.sp
            )
          }
        }
      }
    }
  }
}

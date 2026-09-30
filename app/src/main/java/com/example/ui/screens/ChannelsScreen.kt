package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
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
import com.example.ui.theme.BrandBorder
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.Sky100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate900
import com.example.ui.theme.StatusAttentionAccent
import com.example.ui.theme.StatusCompletedAccent
import com.example.ui.theme.StatusNotStartedAccent
import com.example.ui.theme.StatusReviewAccent
import com.example.ui.viewmodel.ChannelSummary

@Composable
fun ChannelsScreen(
  channelSummaries: List<ChannelSummary>,
  onSwitchChannel: (String) -> Unit,
  onMarkAllChecked: (String) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF4F6F9))
      .padding(horizontal = 16.dp, vertical = 12.dp)
      .testTag("channels_screen")
  ) {
    Text(
      text = "Broadcaster Channels Overview",
      color = Slate900,
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp
    )
    Text(
      text = "Monitor playlist verification progress across all 5 networks",
      color = Slate500,
      fontSize = 12.sp
    )

    Spacer(modifier = Modifier.height(12.dp))

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(channelSummaries, key = { it.channel }) { summary ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, BrandBorder, RoundedCornerShape(12.dp))
            .padding(14.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Header
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Sky100)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = summary.channel,
                    color = BrandNavy,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp
                  )
                }
                Text(
                  text = "${summary.totalItems} Checklist Items",
                  color = Slate500,
                  fontSize = 12.sp
                )
              }

              val percentage = (summary.completionRate * 100).toInt()
              Text(
                text = "$percentage% Complete",
                color = if (percentage == 100) StatusCompletedAccent else BrandNavy,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }

            // Progress bar
            LinearProgressIndicator(
              progress = { summary.completionRate },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = StatusCompletedAccent,
              trackColor = Color(0xFFE2E8F0)
            )

            // Status counts breakdown
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              StatusCounterBadge(
                label = "Checked",
                count = summary.completedItems,
                color = StatusCompletedAccent
              )
              StatusCounterBadge(
                label = "Review",
                count = summary.inReviewItems,
                color = StatusReviewAccent
              )
              StatusCounterBadge(
                label = "Alert",
                count = summary.attentionItems,
                color = StatusAttentionAccent
              )
              StatusCounterBadge(
                label = "Pending",
                count = summary.pendingItems,
                color = StatusNotStartedAccent
              )
            }

            // Action Buttons
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              OutlinedButton(
                onClick = { onMarkAllChecked(summary.channel) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f).height(36.dp)
              ) {
                Text("Mark Checked", fontSize = 11.sp, color = BrandNavy, fontWeight = FontWeight.Bold)
              }

              Button(
                onClick = { onSwitchChannel(summary.channel) },
                colors = ButtonDefaults.buttonColors(
                  containerColor = BrandTeal,
                  contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f).height(36.dp)
              ) {
                Text("Open Checklist", fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun StatusCounterBadge(
  label: String,
  count: Int,
  color: Color
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(2.dp))
        .background(color)
        .padding(horizontal = 4.dp, vertical = 1.dp)
    ) {
      Text(
        text = count.toString(),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp
      )
    }
    Text(
      text = label,
      color = Slate500,
      fontSize = 10.sp
    )
  }
}

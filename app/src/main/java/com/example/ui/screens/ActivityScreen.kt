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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
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
import com.example.data.model.ActivityLogEntity
import com.example.ui.theme.BrandBorder
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.Sky100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate900
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ActivityScreen(
  logs: List<ActivityLogEntity>
) {
  val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
  val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF4F6F9))
      .padding(horizontal = 16.dp, vertical = 12.dp)
      .testTag("activity_screen")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Operations Activity Log",
          color = Slate900,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
        Text(
          text = "Real-time audit trail of playlist updates",
          color = Slate500,
          fontSize = 12.sp
        )
      }
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(Sky100)
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = "${logs.size} Events",
          color = BrandNavy,
          fontWeight = FontWeight.Bold,
          fontSize = 11.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    if (logs.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 40.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            tint = Slate400,
            modifier = Modifier.size(48.dp)
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "No operational events logged yet",
            color = Slate500,
            fontSize = 13.sp
          )
        }
      }
    } else {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(logs, key = { it.id }) { log ->
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(Color.White)
              .border(1.dp, BrandBorder, RoundedCornerShape(10.dp))
              .padding(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
              ) {
                Box(
                  modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0F2FE)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = BrandTeal,
                    modifier = Modifier.size(16.dp)
                  )
                }

                Column {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Sky100)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                      Text(
                        text = log.channel,
                        color = BrandNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                      )
                    }

                    Text(
                      text = log.itemName,
                      color = Slate900,
                      fontWeight = FontWeight.Bold,
                      fontSize = 12.sp
                    )
                  }

                  Spacer(modifier = Modifier.height(4.dp))

                  Text(
                    text = "Status updated: ${log.oldStatus} → ${log.newStatus}",
                    color = Slate500,
                    fontSize = 11.sp
                  )
                }
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = timeFormat.format(Date(log.timestamp)),
                  color = Slate900,
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 11.sp
                )
                Text(
                  text = dateFormat.format(Date(log.timestamp)),
                  color = Slate400,
                  fontSize = 10.sp
                )
              }
            }
          }
        }
      }
    }
  }
}

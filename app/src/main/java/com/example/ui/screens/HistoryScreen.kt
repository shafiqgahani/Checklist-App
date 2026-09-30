package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandBorder
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.Sky100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.theme.StatusCompletedAccent
import com.example.ui.viewmodel.ChannelSummary
import java.time.LocalDate

@Composable
fun HistoryScreen(
  selectedDate: String,
  channelSummaries: List<ChannelSummary>,
  onDateSelected: (LocalDate) -> Unit
) {
  val clipboardManager = LocalClipboardManager.current
  val context = LocalContext.current

  val totalItems = channelSummaries.sumOf { it.totalItems }
  val totalCompleted = channelSummaries.sumOf { it.completedItems }
  val totalAttention = channelSummaries.sumOf { it.attentionItems }
  val overallPct = if (totalItems > 0) (totalCompleted * 100) / totalItems else 0

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF4F6F9))
      .padding(horizontal = 16.dp, vertical = 12.dp)
      .testTag("history_screen")
  ) {
    Text(
      text = "Broadcast Shift History & Audit",
      color = Slate900,
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp
    )
    Text(
      text = "Verification logs and compliance reporting for $selectedDate",
      color = Slate500,
      fontSize = 12.sp
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Compliance Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(BrandNavy)
        .padding(16.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "DAILY AUDIT COMPLIANCE",
              color = Color(0xFF94A3B8),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.5.sp
            )
            Text(
              text = selectedDate,
              color = Color.White,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(Sky100)
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Text(
              text = "$overallPct% PASSED",
              color = BrandNavy,
              fontWeight = FontWeight.ExtraBold,
              fontSize = 12.sp
            )
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text(text = "Total Checkpoints", color = Color(0xFFCBD5E1), fontSize = 11.sp)
            Text(text = "$totalItems", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }
          Column {
            Text(text = "Verified Items", color = Color(0xFFCBD5E1), fontSize = 11.sp)
            Text(text = "$totalCompleted", color = StatusCompletedAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }
          Column {
            Text(text = "Needs Attention", color = Color(0xFFCBD5E1), fontSize = 11.sp)
            Text(text = "$totalAttention", color = Color(0xFFF87171), fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }
        }

        Button(
          onClick = {
            val report = buildString {
              appendLine("CHECKLIST OPS - BROADCAST AUDIT REPORT")
              appendLine("Schedule Date: $selectedDate")
              appendLine("Overall Compliance: $overallPct% ($totalCompleted/$totalItems items)")
              appendLine("----------------------------------------")
              channelSummaries.forEach { summary ->
                appendLine("${summary.channel}: ${summary.completedItems}/${summary.totalItems} Checked (${(summary.completionRate * 100).toInt()}%)")
              }
              appendLine("Signed off by: SY (Shift Supervisor)")
            }
            clipboardManager.setText(AnnotatedString(report))
            Toast.makeText(context, "Audit report copied to clipboard", Toast.LENGTH_SHORT).show()
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = BrandTeal,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .testTag("copy_audit_report_button")
        ) {
          Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.size(6.dp))
          Text("Copy Shift Compliance Report", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "Network Audit Status Breakdown",
      color = Slate900,
      fontWeight = FontWeight.Bold,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(channelSummaries.size) { idx ->
        val summary = channelSummaries[idx]
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(1.dp, BrandBorder, RoundedCornerShape(8.dp))
            .padding(12.dp)
        ) {
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
                  .clip(RoundedCornerShape(4.dp))
                  .background(Sky100)
                  .padding(horizontal = 8.dp, vertical = 2.dp)
              ) {
                Text(
                  text = summary.channel,
                  color = BrandNavy,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp
                )
              }
              Text(
                text = "${summary.completedItems} / ${summary.totalItems} verified",
                color = Slate700,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
            }

            if (summary.completedItems == summary.totalItems && summary.totalItems > 0) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = StatusCompletedAccent,
                  modifier = Modifier.size(16.dp)
                )
                Text(
                  text = "COMPLIANT",
                  color = StatusCompletedAccent,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp
                )
              }
            } else {
              Text(
                text = "${summary.totalItems - summary.completedItems} PENDING",
                color = Slate400,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            }
          }
        }
      }
    }
  }
}

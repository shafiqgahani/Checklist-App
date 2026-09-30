package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.BrandBorder
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CalendarDatePickerDialog(
  initialDate: LocalDate,
  onDismiss: () -> Unit,
  onDateSelected: (LocalDate) -> Unit
) {
  var viewingMonth by remember { mutableStateOf(YearMonth.from(initialDate)) }
  var selectedDay by remember { mutableStateOf(initialDate) }

  val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
  val daysInMonth = viewingMonth.lengthOfMonth()
  val firstDayOfWeek = viewingMonth.atDay(1).dayOfWeek.value % 7 // Sunday = 0

  Dialog(onDismissRequest = onDismiss) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        // Modal Header
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(BrandNavy)
            .padding(horizontal = 16.dp, vertical = 14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Select Schedule Date",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
          )
          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(24.dp).testTag("close_calendar_button")
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = Color(0xFFCBD5E1),
              modifier = Modifier.size(18.dp)
            )
          }
        }

        // Month Navigation
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = { viewingMonth = viewingMonth.minusMonths(1) },
            modifier = Modifier.size(32.dp).testTag("prev_month_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
              contentDescription = "Previous Month",
              tint = Slate700
            )
          }

          Text(
            text = viewingMonth.format(monthFormatter),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Slate900
          )

          IconButton(
            onClick = { viewingMonth = viewingMonth.plusMonths(1) },
            modifier = Modifier.size(32.dp).testTag("next_month_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
              contentDescription = "Next Month",
              tint = Slate700
            )
          }
        }

        // Day of Week Headers
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa").forEach { dayLabel ->
            Box(
              modifier = Modifier.weight(1f),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = dayLabel,
                color = Slate400,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }

        // Calendar Days Grid
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          var dayCounter = 1
          val totalSlots = firstDayOfWeek + daysInMonth
          val totalRows = (totalSlots + 6) / 7

          for (row in 0 until totalRows) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              for (col in 0..6) {
                val slotIndex = row * 7 + col
                if (slotIndex < firstDayOfWeek || dayCounter > daysInMonth) {
                  Box(modifier = Modifier.weight(1f))
                } else {
                  val currentDayNum = dayCounter
                  val thisDate = viewingMonth.atDay(currentDayNum)
                  val isSelected = thisDate == selectedDay

                  Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                  ) {
                    Box(
                      modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) BrandNavy else Color.Transparent)
                        .clickable { selectedDay = thisDate }
                        .testTag("calendar_day_$currentDayNum"),
                      contentAlignment = Alignment.Center
                    ) {
                      Text(
                        text = currentDayNum.toString(),
                        color = if (isSelected) Color.White else Slate900,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                      )
                    }
                  }
                  dayCounter++
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Preset Quick Picks / Footer Actions
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(BrandBorder)
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC))
            .padding(horizontal = 14.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.End,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextButton(
            onClick = {
              val today = LocalDate.now()
              selectedDay = today
              viewingMonth = YearMonth.from(today)
            },
            modifier = Modifier.testTag("pick_today_button")
          ) {
            Text(
              text = "Today",
              color = Slate700,
              fontWeight = FontWeight.SemiBold,
              fontSize = 12.sp
            )
          }

          Button(
            onClick = { onDateSelected(selectedDay) },
            colors = ButtonDefaults.buttonColors(
              containerColor = BrandTeal,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .height(34.dp)
              .testTag("confirm_calendar_date_button")
          ) {
            Text(
              text = "Done",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )
          }
        }
      }
    }
  }
}

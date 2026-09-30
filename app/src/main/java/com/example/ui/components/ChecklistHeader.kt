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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.Sky100

@Composable
fun ChecklistHeader(
  onBackClick: () -> Unit = {},
  onProfileClick: () -> Unit = {}
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(BrandNavy)
      .statusBarsPadding()
      .padding(horizontal = 20.dp, vertical = 12.dp)
  ) {
    // Top Bar: Back & User Avatar
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .size(36.dp)
            .testTag("header_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color(0xFFE2E8F0),
            modifier = Modifier.size(20.dp)
          )
        }
        Text(
          text = "Checklist Ops",
          color = Color.White,
          fontSize = 18.dp.value.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = (-0.3).sp,
          modifier = Modifier.padding(start = 4.dp)
        )
      }

      // User Avatar Circle ("SY")
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(Sky100)
          .clickable { onProfileClick() }
          .testTag("user_avatar_button"),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "SY",
          color = BrandNavy,
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Header Title & Subtitle
    Text(
      text = "Channel checklist",
      color = Color.White,
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = (-0.5).sp
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = "Choose a schedule date, then review by channel",
      color = Color(0xFFCBD5E1),
      fontSize = 12.sp,
      fontWeight = FontWeight.Normal
    )

    Spacer(modifier = Modifier.height(6.dp))
  }
}

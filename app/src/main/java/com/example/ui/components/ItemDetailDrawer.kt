package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChecklistItemEntity
import com.example.data.model.ItemStatus
import com.example.ui.theme.BrandBorder
import com.example.ui.theme.BrandNavy
import com.example.ui.theme.BrandTeal
import com.example.ui.theme.Sky100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import java.util.Locale

data class ParameterRowItem(
  val rowNumber: Int,
  val isOperationalNote: Boolean,
  val condition: String,
  val setting: String,
  val rawText: String,
  val containsNoSetting: Boolean,
  val containsLogoOnly: Boolean
)

fun parseParameterSettings(assignmentText: String): List<ParameterRowItem> {
  return assignmentText
    .split(";")
    .map { it.trim() }
    .filter { it.isNotBlank() }
    .mapIndexed { index, segment ->
      val hasArrow = segment.contains("→") || segment.contains("->")
      val containsNoSetting = segment.contains("NO SETTING", ignoreCase = true)
      val containsLogoOnly = segment.contains("LOGO ONLY", ignoreCase = true)

      if (hasArrow) {
        val arrow = if (segment.contains("→")) "→" else "->"
        val arrowIndex = segment.indexOf(arrow)
        val condition = segment.substring(0, arrowIndex).trim()
        val setting = segment.substring(arrowIndex + arrow.length).trim()
        ParameterRowItem(
          rowNumber = index + 1,
          isOperationalNote = false,
          condition = condition,
          setting = setting,
          rawText = segment,
          containsNoSetting = containsNoSetting,
          containsLogoOnly = containsLogoOnly
        )
      } else {
        ParameterRowItem(
          rowNumber = index + 1,
          isOperationalNote = true,
          condition = "",
          setting = "",
          rawText = segment,
          containsNoSetting = containsNoSetting,
          containsLogoOnly = containsLogoOnly
        )
      }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailSheet(
  item: ChecklistItemEntity,
  onDismiss: () -> Unit,
  onSave: (ItemStatus, String) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var selectedStatus by remember(item.id) { mutableStateOf(item.itemStatus) }
  var notesText by remember(item.id) { mutableStateOf(item.notes) }

  val isParameterSetting = item.item.equals("Parameter Setting", ignoreCase = true)
  val parameterRows = remember(item.assignment) {
    if (isParameterSetting) parseParameterSettings(item.assignment) else emptyList()
  }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    dragHandle = null
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.92f)
        .navigationBarsPadding()
    ) {
      // 1. STICKY HEADER - fixed at the top of the modal
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(horizontal = 20.dp, vertical = 14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              // Channel Badge
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(Sky100)
                  .padding(horizontal = 8.dp, vertical = 2.dp)
              ) {
                Text(
                  text = item.channel,
                  color = BrandNavy,
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 11.sp
                )
              }

              // Status Badge
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(selectedStatus.bgColor)
                  .border(1.dp, selectedStatus.borderColor, RoundedCornerShape(12.dp))
                  .padding(horizontal = 8.dp, vertical = 2.dp)
              ) {
                Text(
                  text = selectedStatus.displayTitle,
                  color = selectedStatus.textColor,
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Deep navy heading
            Text(
              text = item.item,
              color = BrandNavy,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              lineHeight = 22.sp
            )
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier
              .size(32.dp)
              .testTag("close_drawer_button")
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = Slate400,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }

      // Thin grey-blue border below sticky header
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(1.dp)
          .background(BrandBorder)
      )

      // 2. SCROLLABLE BODY - vertically scrollable list
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 20.dp, vertical = 14.dp)
      ) {
        if (isParameterSetting) {
          // Structured spreadsheet-style list for Parameter Setting
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "STRUCTURED PARAMETER SPECIFICATIONS",
              color = BrandNavy,
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 0.8.sp
            )
            Text(
              text = "${parameterRows.size} Rows",
              color = Slate500,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // List of bordered rows
          Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.testTag("parameter_setting_rows_container")
          ) {
            parameterRows.forEach { rowItem ->
              ParameterRowCard(rowItem = rowItem)
            }
          }
        } else {
          // Normal checklist item container
          Text(
            text = "COMPLETE ASSIGNMENT & PARAMETERS",
            color = Slate400,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
          )

          Spacer(modifier = Modifier.height(6.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFFF8FAFC))
              .border(1.dp, BrandBorder, RoundedCornerShape(8.dp))
              .padding(12.dp)
          ) {
            Text(
              text = item.assignment,
              color = Slate700,
              fontFamily = FontFamily.Monospace,
              fontSize = 11.sp,
              lineHeight = 16.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Status Radio Choices
        Text(
          text = "UPDATE ITEM STATUS",
          color = BrandNavy,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.8.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          ItemStatus.entries.forEach { statusOption ->
            val isSelected = selectedStatus == statusOption
            val optBg = if (isSelected) Color(0xFFF0FDFA) else Color.White
            val optBorder = if (isSelected) BrandTeal else Color(0xFFE2E8F0)

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(optBg)
                .border(1.dp, optBorder, RoundedCornerShape(8.dp))
                .clickable { selectedStatus = statusOption }
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .testTag("status_option_${statusOption.dbValue}"),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(statusOption.accentColor)
                )
                Text(
                  text = statusOption.displayTitle,
                  color = if (isSelected) BrandNavy else Slate700,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  fontSize = 12.sp
                )
              }

              if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = "Selected",
                  tint = BrandTeal,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Shift Remarks / Notes
        Text(
          text = "OPERATOR SHIFT NOTES (OPTIONAL)",
          color = BrandNavy,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.8.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
          value = notesText,
          onValueChange = { notesText = it },
          placeholder = { Text("e.g. Verified by SY at 18:45, clock locked", fontSize = 12.sp, color = Slate400) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("notes_input_field"),
          colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF8FAFC),
            unfocusedContainerColor = Color(0xFFF8FAFC),
            focusedIndicatorColor = BrandTeal,
            unfocusedIndicatorColor = Color(0xFFE2E8F0)
          ),
          shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Confirm & Save Button
        Button(
          onClick = { onSave(selectedStatus, notesText) },
          colors = ButtonDefaults.buttonColors(
            containerColor = BrandTeal,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .testTag("drawer_save_button")
        ) {
          Text(
            text = "Confirm & Save",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )
        }
      }
    }
  }
}

@Composable
private fun ParameterRowCard(rowItem: ParameterRowItem) {
  // Determine background based on requirements:
  // - Light blue background for rows containing NO SETTING
  // - Light amber background for rows containing LOGO ONLY
  // - Light grey background for operational notes
  // - Light rose background for normal programme-setting rows
  val rowBg = when {
    rowItem.containsNoSetting -> Color(0xFFEFF6FF)       // Light blue
    rowItem.containsLogoOnly -> Color(0xFFFFFBEB)        // Light amber
    rowItem.isOperationalNote -> Color(0xFFF8FAFC)       // Light grey
    else -> Color(0xFFFFF1F2)                            // Light rose
  }

  val rowBorder = Color(0xFFE3E8EE) // Thin grey-blue row border

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(rowBg)
      .border(1.dp, rowBorder, RoundedCornerShape(8.dp))
      .padding(12.dp)
      .testTag("parameter_row_${rowItem.rowNumber}")
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      // Row Meta Header: Row Number Badge & Category Tags
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Small Row Number
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = String.format(Locale.US, "#%02d", rowItem.rowNumber),
            color = Slate700,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }

        // Status / Category badges
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (rowItem.isOperationalNote) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE2E8F0))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "OPERATIONAL NOTE",
                color = Slate700,
                fontSize = 9.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
              )
            }
          }

          if (rowItem.containsNoSetting) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFDBEAFE))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "NO SETTING",
                color = Color(0xFF1E40AF),
                fontSize = 9.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
              )
            }
          }

          if (rowItem.containsLogoOnly) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFFEF3C7))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "LOGO ONLY",
                color = Color(0xFF92400E),
                fontSize = 9.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
              )
            }
          }
        }
      }

      if (rowItem.isOperationalNote) {
        // Operational Note Content
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
          Text(
            text = "INSTRUCTION / NOTE",
            color = Slate500,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
          Text(
            text = rowItem.rawText,
            color = Slate900,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 20.sp
          )
        }
      } else {
        // Programme / Condition in bold
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
          Text(
            text = "PROGRAMME / CONDITION",
            color = Slate500,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
          Text(
            text = rowItem.condition,
            color = BrandNavy,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp
          )
        }

        // Required Setting underneath in readable text
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
          Text(
            text = "REQUIRED SETTING",
            color = Slate500,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
          Text(
            text = rowItem.setting,
            color = Slate900,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 20.sp
          )
        }
      }
    }
  }
}

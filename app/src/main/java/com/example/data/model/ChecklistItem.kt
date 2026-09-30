package com.example.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ui.theme.StatusAttentionAccent
import com.example.ui.theme.StatusAttentionBg
import com.example.ui.theme.StatusAttentionBorder
import com.example.ui.theme.StatusAttentionText
import com.example.ui.theme.StatusCompletedAccent
import com.example.ui.theme.StatusCompletedBg
import com.example.ui.theme.StatusCompletedBorder
import com.example.ui.theme.StatusCompletedText
import com.example.ui.theme.StatusNotStartedAccent
import com.example.ui.theme.StatusNotStartedBg
import com.example.ui.theme.StatusNotStartedBorder
import com.example.ui.theme.StatusNotStartedText
import com.example.ui.theme.StatusReviewAccent
import com.example.ui.theme.StatusReviewBg
import com.example.ui.theme.StatusReviewBorder
import com.example.ui.theme.StatusReviewText

enum class ItemStatus(
  val dbValue: String,
  val badgeLabel: String,
  val displayTitle: String,
  val bgColor: Color,
  val textColor: Color,
  val borderColor: Color,
  val accentColor: Color
) {
  COMPLETED(
    dbValue = "Completed",
    badgeLabel = "CHECKED",
    displayTitle = "Completed",
    bgColor = StatusCompletedBg,
    textColor = StatusCompletedText,
    borderColor = StatusCompletedBorder,
    accentColor = StatusCompletedAccent
  ),
  IN_REVIEW(
    dbValue = "In review",
    badgeLabel = "REVIEW",
    displayTitle = "In review",
    bgColor = StatusReviewBg,
    textColor = StatusReviewText,
    borderColor = StatusReviewBorder,
    accentColor = StatusReviewAccent
  ),
  NEEDS_ATTENTION(
    dbValue = "Needs attention",
    badgeLabel = "ALERT",
    displayTitle = "Needs attention",
    bgColor = StatusAttentionBg,
    textColor = StatusAttentionText,
    borderColor = StatusAttentionBorder,
    accentColor = StatusAttentionAccent
  ),
  NOT_STARTED(
    dbValue = "Not started",
    badgeLabel = "PENDING",
    displayTitle = "Not started",
    bgColor = StatusNotStartedBg,
    textColor = StatusNotStartedText,
    borderColor = StatusNotStartedBorder,
    accentColor = StatusNotStartedAccent
  );

  fun nextStatus(): ItemStatus {
    val values = entries
    val nextIdx = (ordinal + 1) % values.size
    return values[nextIdx]
  }

  companion object {
    fun fromDb(value: String): ItemStatus {
      return entries.find { it.dbValue.equals(value, ignoreCase = true) } ?: NOT_STARTED
    }
  }
}

@Entity(tableName = "checklist_items")
data class ChecklistItemEntity(
  @PrimaryKey
  val id: String,
  val channel: String,
  val item: String,
  val assignment: String,
  val status: String,
  val scheduleDate: String = "2026-02-24",
  val notes: String = "",
  val lastUpdated: Long = System.currentTimeMillis()
) {
  val itemStatus: ItemStatus
    get() = ItemStatus.fromDb(status)
}

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val channel: String,
  val itemName: String,
  val oldStatus: String,
  val newStatus: String,
  val timestamp: Long = System.currentTimeMillis()
)

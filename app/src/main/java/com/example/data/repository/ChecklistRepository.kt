package com.example.data.repository

import com.example.data.db.ChecklistDao
import com.example.data.model.ActivityLogEntity
import com.example.data.model.ChecklistItemEntity
import com.example.data.model.ItemStatus
import kotlinx.coroutines.flow.Flow

class ChecklistRepository(private val dao: ChecklistDao) {

  suspend fun ensureDataInitialized(date: String = "2026-02-24") {
    val count = dao.getItemCountForDate(date)
    if (count == 0) {
      val items = DefaultChecklistData.getInitialItems(date)
      dao.insertAll(items)
      dao.insertActivityLog(
        ActivityLogEntity(
          channel = "SYSTEM",
          itemName = "Playlist Initialized",
          oldStatus = "-",
          newStatus = "Ready",
          timestamp = System.currentTimeMillis()
        )
      )
    } else {
      synchronizeParameterSettings(date)
    }
  }

  suspend fun synchronizeParameterSettings(date: String = "2026-02-24") {
    for (spec in DefaultChecklistData.PARAMETER_SETTINGS) {
      val existingList = dao.getItemsByChannelAndName(spec.channel, "Parameter Setting", date)
      val existingByStableId = dao.getItemById(spec.id)

      if (existingByStableId != null) {
        val updated = existingByStableId.copy(
          channel = spec.channel,
          item = "Parameter Setting",
          assignment = spec.assignment,
          scheduleDate = date
        )
        dao.updateItem(updated)
        for (item in existingList) {
          if (item.id != spec.id) {
            dao.deleteItemById(item.id)
          }
        }
      } else if (existingList.isNotEmpty()) {
        val firstItem = existingList.first()
        for (item in existingList) {
          dao.deleteItemById(item.id)
        }
        val newItem = ChecklistItemEntity(
          id = spec.id,
          channel = spec.channel,
          item = "Parameter Setting",
          assignment = spec.assignment,
          status = firstItem.status,
          scheduleDate = date,
          notes = firstItem.notes,
          lastUpdated = System.currentTimeMillis()
        )
        dao.insertItem(newItem)
      } else {
        val newItem = ChecklistItemEntity(
          id = spec.id,
          channel = spec.channel,
          item = "Parameter Setting",
          assignment = spec.assignment,
          status = "Not started",
          scheduleDate = date,
          lastUpdated = System.currentTimeMillis()
        )
        dao.insertItem(newItem)
      }
    }
  }

  fun getItemsByChannelAndDate(channel: String, date: String): Flow<List<ChecklistItemEntity>> {
    return dao.getItemsByChannelAndDate(channel, date)
  }

  fun getAllItemsByDate(date: String): Flow<List<ChecklistItemEntity>> {
    return dao.getAllItemsByDate(date)
  }

  fun getActivityLogs(): Flow<List<ActivityLogEntity>> {
    return dao.getActivityLogs()
  }

  fun getRecordedDates(): Flow<List<String>> {
    return dao.getAllRecordedDates()
  }

  suspend fun cycleItemStatus(item: ChecklistItemEntity) {
    val oldStatus = item.status
    val nextStatus = item.itemStatus.nextStatus().dbValue
    val updated = item.copy(status = nextStatus, lastUpdated = System.currentTimeMillis())
    dao.updateItem(updated)
    dao.insertActivityLog(
      ActivityLogEntity(
        channel = item.channel,
        itemName = item.item,
        oldStatus = oldStatus,
        newStatus = nextStatus,
        timestamp = System.currentTimeMillis()
      )
    )
  }

  suspend fun updateItemStatus(item: ChecklistItemEntity, newStatus: ItemStatus, notes: String = item.notes) {
    val oldStatus = item.status
    val updated = item.copy(
      status = newStatus.dbValue,
      notes = notes,
      lastUpdated = System.currentTimeMillis()
    )
    dao.updateItem(updated)
    if (oldStatus != newStatus.dbValue) {
      dao.insertActivityLog(
        ActivityLogEntity(
          channel = item.channel,
          itemName = item.item,
          oldStatus = oldStatus,
          newStatus = newStatus.dbValue,
          timestamp = System.currentTimeMillis()
        )
      )
    }
  }

  suspend fun markAllChannelCompleted(channel: String, date: String) {
    dao.updateAllChannelItemsStatus(channel, date, ItemStatus.COMPLETED.dbValue, System.currentTimeMillis())
    dao.insertActivityLog(
      ActivityLogEntity(
        channel = channel,
        itemName = "Batch Audit Completed",
        oldStatus = "Various",
        newStatus = "Completed",
        timestamp = System.currentTimeMillis()
      )
    )
  }
}

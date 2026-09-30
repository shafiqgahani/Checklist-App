package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ActivityLogEntity
import com.example.data.model.ChecklistItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {

  @Query("SELECT * FROM checklist_items WHERE channel = :channel AND scheduleDate = :scheduleDate ORDER BY id ASC")
  fun getItemsByChannelAndDate(channel: String, scheduleDate: String): Flow<List<ChecklistItemEntity>>

  @Query("SELECT * FROM checklist_items WHERE scheduleDate = :scheduleDate ORDER BY channel ASC, id ASC")
  fun getAllItemsByDate(scheduleDate: String): Flow<List<ChecklistItemEntity>>

  @Query("SELECT * FROM checklist_items WHERE id = :id LIMIT 1")
  suspend fun getItemById(id: String): ChecklistItemEntity?

  @Query("SELECT * FROM checklist_items WHERE channel = :channel AND item = :item AND scheduleDate = :scheduleDate")
  suspend fun getItemsByChannelAndName(channel: String, item: String, scheduleDate: String): List<ChecklistItemEntity>

  @Query("SELECT COUNT(*) FROM checklist_items WHERE scheduleDate = :scheduleDate")
  suspend fun getItemCountForDate(scheduleDate: String): Int

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertItem(item: ChecklistItemEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(items: List<ChecklistItemEntity>)

  @Update
  suspend fun updateItem(item: ChecklistItemEntity)

  @Query("DELETE FROM checklist_items WHERE id = :id")
  suspend fun deleteItemById(id: String)

  @Query("UPDATE checklist_items SET status = :status, lastUpdated = :timestamp WHERE channel = :channel AND scheduleDate = :scheduleDate")
  suspend fun updateAllChannelItemsStatus(channel: String, scheduleDate: String, status: String, timestamp: Long)

  @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT 100")
  fun getActivityLogs(): Flow<List<ActivityLogEntity>>

  @Insert
  suspend fun insertActivityLog(log: ActivityLogEntity)

  @Query("SELECT DISTINCT scheduleDate FROM checklist_items ORDER BY scheduleDate DESC")
  fun getAllRecordedDates(): Flow<List<String>>
}

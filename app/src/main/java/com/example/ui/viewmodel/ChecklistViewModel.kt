package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.ActivityLogEntity
import com.example.data.model.ChecklistItemEntity
import com.example.data.model.ItemStatus
import com.example.data.repository.ChecklistRepository
import com.example.data.repository.DefaultChecklistData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class AppBottomTab {
  CHECKLIST,
  ACTIVITY,
  CHANNELS,
  HISTORY
}

data class FilterCounts(
  val all: Int = 0,
  val completed: Int = 0,
  val inReview: Int = 0,
  val attention: Int = 0,
  val pending: Int = 0
)

data class ChannelSummary(
  val channel: String,
  val totalItems: Int,
  val completedItems: Int,
  val inReviewItems: Int,
  val attentionItems: Int,
  val pendingItems: Int
) {
  val completionRate: Float
    get() = if (totalItems > 0) completedItems.toFloat() / totalItems else 0f
}

class ChecklistViewModel(
  private val repository: ChecklistRepository
) : ViewModel() {

  private val _currentChannel = MutableStateFlow("TV3")
  val currentChannel: StateFlow<String> = _currentChannel.asStateFlow()

  private val _selectedDate = MutableStateFlow(LocalDate.of(2026, 2, 24))
  val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _activeFilter = MutableStateFlow("ALL")
  val activeFilter: StateFlow<String> = _activeFilter.asStateFlow()

  private val _currentTab = MutableStateFlow(AppBottomTab.CHECKLIST)
  val currentTab: StateFlow<AppBottomTab> = _currentTab.asStateFlow()

  private val _editingItem = MutableStateFlow<ChecklistItemEntity?>(null)
  val editingItem: StateFlow<ChecklistItemEntity?> = _editingItem.asStateFlow()

  private val _showCalendarDialog = MutableStateFlow(false)
  val showCalendarDialog: StateFlow<Boolean> = _showCalendarDialog.asStateFlow()

  private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  private val displayDateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)

  val formattedSelectedDate: StateFlow<String> = MutableStateFlow("24 Feb 2026")

  init {
    viewModelScope.launch {
      val dateStr = _selectedDate.value.format(dateFormatter)
      repository.ensureDataInitialized(dateStr)
    }
  }

  // All items for the date (to compute per-channel totals & counts)
  val allDateItems: StateFlow<List<ChecklistItemEntity>> = _selectedDate.flatMapLatest { date ->
    val dateStr = date.format(dateFormatter)
    repository.getAllItemsByDate(dateStr)
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Items for the selected channel
  val currentChannelItems: StateFlow<List<ChecklistItemEntity>> = combine(
    _currentChannel,
    _selectedDate
  ) { channel, date ->
    Pair(channel, date.format(dateFormatter))
  }.flatMapLatest { (channel, dateStr) ->
    repository.getItemsByChannelAndDate(channel, dateStr)
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Filtered items based on search query & active status pill
  val filteredItems: StateFlow<List<ChecklistItemEntity>> = combine(
    currentChannelItems,
    _searchQuery,
    _activeFilter
  ) { items, query, filter ->
    val trimmed = query.trim().lowercase()
    items.filter { item ->
      val matchesFilter = when (filter) {
        "Completed" -> item.status.equals("Completed", ignoreCase = true)
        "In review" -> item.status.equals("In review", ignoreCase = true)
        "Needs attention" -> item.status.equals("Needs attention", ignoreCase = true)
        "Not started" -> item.status.equals("Not started", ignoreCase = true)
        else -> true
      }
      val matchesSearch = trimmed.isEmpty() ||
          item.item.lowercase().contains(trimmed) ||
          item.assignment.lowercase().contains(trimmed)
      matchesFilter && matchesSearch
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Counts for the current channel pills
  val filterCounts: StateFlow<FilterCounts> = currentChannelItems.combine(_activeFilter) { items, _ ->
    FilterCounts(
      all = items.size,
      completed = items.count { it.status.equals("Completed", ignoreCase = true) },
      inReview = items.count { it.status.equals("In review", ignoreCase = true) },
      attention = items.count { it.status.equals("Needs attention", ignoreCase = true) },
      pending = items.count { it.status.equals("Not started", ignoreCase = true) }
    )
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FilterCounts())

  // Channel summaries for Channels tab
  val channelSummaries: StateFlow<List<ChannelSummary>> = allDateItems.combine(_currentChannel) { all, _ ->
    DefaultChecklistData.CHANNELS.map { channelName ->
      val channelItems = all.filter { it.channel.equals(channelName, ignoreCase = true) }
      ChannelSummary(
        channel = channelName,
        totalItems = channelItems.size,
        completedItems = channelItems.count { it.status.equals("Completed", ignoreCase = true) },
        inReviewItems = channelItems.count { it.status.equals("In review", ignoreCase = true) },
        attentionItems = channelItems.count { it.status.equals("Needs attention", ignoreCase = true) },
        pendingItems = channelItems.count { it.status.equals("Not started", ignoreCase = true) }
      )
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Activity logs
  val activityLogs: StateFlow<List<ActivityLogEntity>> = repository.getActivityLogs()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun selectChannel(channel: String) {
    _currentChannel.value = channel
  }

  fun setFilter(filter: String) {
    _activeFilter.value = filter
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setTab(tab: AppBottomTab) {
    _currentTab.value = tab
  }

  fun openItemDetail(item: ChecklistItemEntity) {
    _editingItem.value = item
  }

  fun closeItemDetail() {
    _editingItem.value = null
  }

  fun setCalendarDialogVisible(visible: Boolean) {
    _showCalendarDialog.value = visible
  }

  fun selectDate(date: LocalDate) {
    _selectedDate.value = date
    _showCalendarDialog.value = false
    viewModelScope.launch {
      repository.ensureDataInitialized(date.format(dateFormatter))
    }
  }

  fun cycleItemStatus(item: ChecklistItemEntity) {
    viewModelScope.launch {
      repository.cycleItemStatus(item)
    }
  }

  fun updateItemStatus(item: ChecklistItemEntity, status: ItemStatus, notes: String) {
    viewModelScope.launch {
      repository.updateItemStatus(item, status, notes)
      _editingItem.value = null
    }
  }

  fun markCurrentChannelComplete() {
    val channel = _currentChannel.value
    val dateStr = _selectedDate.value.format(dateFormatter)
    viewModelScope.launch {
      repository.markAllChannelCompleted(channel, dateStr)
    }
  }

  fun getDisplayDate(): String {
    return _selectedDate.value.format(displayDateFormatter)
  }
}

class ChecklistViewModelFactory(
  private val repository: ChecklistRepository
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ChecklistViewModel::class.java)) {
      return ChecklistViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}

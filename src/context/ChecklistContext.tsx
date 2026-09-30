import React, { createContext, useContext, useState, useEffect, useMemo, useCallback } from 'react';
import {
  ChecklistItem,
  ActivityLog,
  AppBottomTab,
  FilterCounts,
  ChannelSummary,
  ItemStatusType,
  NEXT_STATUS_MAP,
} from '../types';
import { CHANNELS, getInitialItems } from '../data/defaultData';

interface ChecklistContextType {
  currentChannel: string;
  selectChannel: (channel: string) => void;
  selectedDate: string;
  selectDate: (date: string) => void;
  displayDateText: string;
  searchQuery: string;
  setSearchQuery: (query: string) => void;
  activeFilter: string;
  setFilter: (filter: string) => void;
  currentTab: AppBottomTab;
  setTab: (tab: AppBottomTab) => void;
  editingItem: ChecklistItem | null;
  openItemDetail: (item: ChecklistItem) => void;
  closeItemDetail: () => void;
  showCalendarDialog: boolean;
  setCalendarDialogVisible: (visible: boolean) => void;
  filteredItems: ChecklistItem[];
  filterCounts: FilterCounts;
  channelSummaries: ChannelSummary[];
  activityLogs: ActivityLog[];
  cycleItemStatus: (item: ChecklistItem) => void;
  updateItemStatus: (item: ChecklistItem, newStatus: ItemStatusType, notes: string) => void;
  markCurrentChannelComplete: () => void;
  toastMessage: string | null;
  showToast: (message: string) => void;
}

const ChecklistContext = createContext<ChecklistContextType | undefined>(undefined);

const STORAGE_KEY_ITEMS_PREFIX = 'tv_checklist_items_';
const STORAGE_KEY_LOGS = 'tv_checklist_logs';

export function formatDisplayDate(dateStr: string): string {
  try {
    const parts = dateStr.split('-');
    if (parts.length === 3) {
      const year = parseInt(parts[0], 10);
      const monthIndex = parseInt(parts[1], 10) - 1;
      const day = parseInt(parts[2], 10);
      const d = new Date(year, monthIndex, day);
      return d.toLocaleDateString('en-GB', { day: 'numeric', month: 'short', year: 'numeric' });
    }
  } catch {
    // fallback
  }
  return dateStr;
}

export const ChecklistProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [currentChannel, setCurrentChannel] = useState<string>('TV3');
  const [selectedDate, setSelectedDate] = useState<string>('2026-02-24');
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [activeFilter, setActiveFilter] = useState<string>('ALL');
  const [currentTab, setCurrentTab] = useState<AppBottomTab>('CHECKLIST');
  const [editingItem, setEditingItem] = useState<ChecklistItem | null>(null);
  const [showCalendarDialog, setShowCalendarDialog] = useState<boolean>(false);
  const [toastMessage, setToastMessage] = useState<string | null>(null);

  const showToast = useCallback((msg: string) => {
    setToastMessage(msg);
  }, []);

  useEffect(() => {
    if (toastMessage) {
      const timer = setTimeout(() => setToastMessage(null), 3000);
      return () => clearTimeout(timer);
    }
  }, [toastMessage]);

  // Load / Store items for the current selectedDate
  const [allItemsMap, setAllItemsMap] = useState<Record<string, ChecklistItem[]>>(() => {
    try {
      const stored = localStorage.getItem(`${STORAGE_KEY_ITEMS_PREFIX}2026-02-24`);
      if (stored) {
        return { '2026-02-24': JSON.parse(stored) };
      }
    } catch {
      // ignore
    }
    const initial = getInitialItems('2026-02-24');
    try {
      localStorage.setItem(`${STORAGE_KEY_ITEMS_PREFIX}2026-02-24`, JSON.stringify(initial));
    } catch {
      // ignore
    }
    return { '2026-02-24': initial };
  });

  // Ensure current date items exist
  useEffect(() => {
    if (!allItemsMap[selectedDate]) {
      try {
        const stored = localStorage.getItem(`${STORAGE_KEY_ITEMS_PREFIX}${selectedDate}`);
        if (stored) {
          setAllItemsMap((prev) => ({ ...prev, [selectedDate]: JSON.parse(stored) }));
          return;
        }
      } catch {
        // ignore
      }
      const initial = getInitialItems(selectedDate);
      try {
        localStorage.setItem(`${STORAGE_KEY_ITEMS_PREFIX}${selectedDate}`, JSON.stringify(initial));
      } catch {
        // ignore
      }
      setAllItemsMap((prev) => ({ ...prev, [selectedDate]: initial }));
    }
  }, [selectedDate, allItemsMap]);

  // Logs state
  const [activityLogs, setActivityLogs] = useState<ActivityLog[]>(() => {
    try {
      const stored = localStorage.getItem(STORAGE_KEY_LOGS);
      if (stored) {
        return JSON.parse(stored);
      }
    } catch {
      // ignore
    }
    return [];
  });

  const saveLogs = useCallback((newLogs: ActivityLog[]) => {
    setActivityLogs(newLogs);
    try {
      localStorage.setItem(STORAGE_KEY_LOGS, JSON.stringify(newLogs));
    } catch {
      // ignore
    }
  }, []);

  const addLog = useCallback(
    (channel: string, itemName: string, oldStatus: string, newStatus: string) => {
      const newLog: ActivityLog = {
        id: Date.now(),
        channel,
        itemName,
        oldStatus,
        newStatus,
        timestamp: Date.now(),
      };
      saveLogs([newLog, ...activityLogs].slice(0, 100));
    },
    [activityLogs, saveLogs]
  );

  const currentItems = useMemo(() => {
    return allItemsMap[selectedDate] || [];
  }, [allItemsMap, selectedDate]);

  const saveCurrentItems = useCallback(
    (newItems: ChecklistItem[]) => {
      setAllItemsMap((prev) => ({ ...prev, [selectedDate]: newItems }));
      try {
        localStorage.setItem(`${STORAGE_KEY_ITEMS_PREFIX}${selectedDate}`, JSON.stringify(newItems));
      } catch {
        // ignore
      }
    },
    [selectedDate]
  );

  const currentChannelItems = useMemo(() => {
    return currentItems.filter(
      (item) => item.channel.toLowerCase() === currentChannel.toLowerCase()
    );
  }, [currentItems, currentChannel]);

  const filteredItems = useMemo(() => {
    const q = searchQuery.trim().toLowerCase();
    return currentChannelItems.filter((item) => {
      const matchesFilter =
        activeFilter === 'ALL'
          ? true
          : item.status.toLowerCase() === activeFilter.toLowerCase();
      const matchesSearch =
        q === '' ||
        item.item.toLowerCase().includes(q) ||
        item.assignment.toLowerCase().includes(q);
      return matchesFilter && matchesSearch;
    });
  }, [currentChannelItems, searchQuery, activeFilter]);

  const filterCounts = useMemo<FilterCounts>(() => {
    return {
      all: currentChannelItems.length,
      completed: currentChannelItems.filter((i) => i.status.toLowerCase() === 'completed').length,
      inReview: currentChannelItems.filter((i) => i.status.toLowerCase() === 'in review').length,
      attention: currentChannelItems.filter((i) => i.status.toLowerCase() === 'needs attention').length,
      pending: currentChannelItems.filter((i) => i.status.toLowerCase() === 'not started').length,
    };
  }, [currentChannelItems]);

  const channelSummaries = useMemo<ChannelSummary[]>(() => {
    return CHANNELS.map((ch) => {
      const channelItems = currentItems.filter(
        (i) => i.channel.toLowerCase() === ch.toLowerCase()
      );
      const total = channelItems.length;
      const completed = channelItems.filter((i) => i.status.toLowerCase() === 'completed').length;
      const inReview = channelItems.filter((i) => i.status.toLowerCase() === 'in review').length;
      const attention = channelItems.filter((i) => i.status.toLowerCase() === 'needs attention').length;
      const pending = channelItems.filter((i) => i.status.toLowerCase() === 'not started').length;

      return {
        channel: ch,
        totalItems: total,
        completedItems: completed,
        inReviewItems: inReview,
        attentionItems: attention,
        pendingItems: pending,
        completionRate: total > 0 ? completed / total : 0,
      };
    });
  }, [currentItems]);

  const cycleItemStatus = useCallback(
    (item: ChecklistItem) => {
      const next = NEXT_STATUS_MAP[item.status] || 'Completed';
      const updated = currentItems.map((i) =>
        i.id === item.id ? { ...i, status: next, lastUpdated: Date.now() } : i
      );
      saveCurrentItems(updated);
      addLog(item.channel, item.item, item.status, next);
    },
    [currentItems, saveCurrentItems, addLog]
  );

  const updateItemStatus = useCallback(
    (item: ChecklistItem, newStatus: ItemStatusType, notes: string) => {
      const oldStatus = item.status;
      const updated = currentItems.map((i) =>
        i.id === item.id ? { ...i, status: newStatus, notes, lastUpdated: Date.now() } : i
      );
      saveCurrentItems(updated);
      if (oldStatus !== newStatus) {
        addLog(item.channel, item.item, oldStatus, newStatus);
      }
      setEditingItem(null);
    },
    [currentItems, saveCurrentItems, addLog]
  );

  const markCurrentChannelComplete = useCallback(() => {
    const updated = currentItems.map((i) => {
      if (i.channel.toLowerCase() === currentChannel.toLowerCase() && i.status !== 'Completed') {
        addLog(i.channel, i.item, i.status, 'Completed');
        return { ...i, status: 'Completed' as ItemStatusType, lastUpdated: Date.now() };
      }
      return i;
    });
    saveCurrentItems(updated);
    showToast(`${currentChannel} checklist marked as verified`);
  }, [currentItems, currentChannel, saveCurrentItems, addLog, showToast]);

  const selectDate = useCallback(
    (date: string) => {
      setSelectedDate(date);
      setShowCalendarDialog(false);
    },
    []
  );

  const displayDateText = useMemo(() => {
    return formatDisplayDate(selectedDate);
  }, [selectedDate]);

  return (
    <ChecklistContext.Provider
      value={{
        currentChannel,
        selectChannel: setCurrentChannel,
        selectedDate,
        selectDate,
        displayDateText,
        searchQuery,
        setSearchQuery,
        activeFilter,
        setFilter: setActiveFilter,
        currentTab,
        setTab: setCurrentTab,
        editingItem,
        openItemDetail: setEditingItem,
        closeItemDetail: () => setEditingItem(null),
        showCalendarDialog,
        setCalendarDialogVisible: setShowCalendarDialog,
        filteredItems,
        filterCounts,
        channelSummaries,
        activityLogs,
        cycleItemStatus,
        updateItemStatus,
        markCurrentChannelComplete,
        toastMessage,
        showToast,
      }}
    >
      {children}
    </ChecklistContext.Provider>
  );
};

export function useChecklist(): ChecklistContextType {
  const context = useContext(ChecklistContext);
  if (!context) {
    throw new Error('useChecklist must be used within a ChecklistProvider');
  }
  return context;
}

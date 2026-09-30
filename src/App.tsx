import React from 'react';
import { ChecklistProvider, useChecklist } from './context/ChecklistContext';
import { ChecklistHeader } from './components/ChecklistHeader';
import { ChannelTabsBar } from './components/ChannelTabsBar';
import { AppBottomBar } from './components/AppBottomBar';
import { ChecklistScreen } from './screens/ChecklistScreen';
import { ActivityScreen } from './screens/ActivityScreen';
import { ChannelsScreen } from './screens/ChannelsScreen';
import { HistoryScreen } from './screens/HistoryScreen';
import { ItemDetailDrawer } from './components/ItemDetailDrawer';
import { CalendarDatePickerDialog } from './components/CalendarDatePickerDialog';

const AppContent: React.FC = () => {
  const { currentTab, editingItem, showCalendarDialog, toastMessage } = useChecklist();

  return (
    <div className="flex flex-col min-h-screen bg-[#F4F6F9] text-slate-900 select-none">
      {/* Top Header */}
      <ChecklistHeader />

      {/* Channel Switcher Tabs */}
      <ChannelTabsBar />

      {/* Main Tab View */}
      <main className="flex-1 flex flex-col min-h-0">
        {currentTab === 'CHECKLIST' && <ChecklistScreen />}
        {currentTab === 'ACTIVITY' && <ActivityScreen />}
        {currentTab === 'CHANNELS' && <ChannelsScreen />}
        {currentTab === 'HISTORY' && <HistoryScreen />}
      </main>

      {/* Bottom Navigation */}
      <AppBottomBar />

      {/* Modals & Drawers */}
      {showCalendarDialog && <CalendarDatePickerDialog />}
      {editingItem && <ItemDetailDrawer item={editingItem} />}

      {/* Toast Notification Banner */}
      {toastMessage && (
        <div className="fixed top-4 left-1/2 -translate-x-1/2 z-50 bg-slate-900 text-white px-4 py-2 rounded-xl text-xs font-semibold shadow-xl border border-slate-700 animate-in fade-in slide-in-from-top-2 duration-150">
          {toastMessage}
        </div>
      )}
    </div>
  );
};

export default function App() {
  return (
    <ChecklistProvider>
      <AppContent />
    </ChecklistProvider>
  );
}

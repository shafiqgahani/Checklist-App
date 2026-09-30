import React from 'react';
import { ListChecks, CheckCircle2, Layers, History } from 'lucide-react';
import { AppBottomTab } from '../types';
import { useChecklist } from '../context/ChecklistContext';

export const AppBottomBar: React.FC = () => {
  const { currentTab, setTab } = useChecklist();

  const navItems = [
    {
      tab: 'CHECKLIST' as AppBottomTab,
      label: 'Checklist',
      icon: ListChecks,
      testTag: 'nav_tab_checklist',
      hasPill: true,
    },
    {
      tab: 'ACTIVITY' as AppBottomTab,
      label: 'Activity',
      icon: CheckCircle2,
      testTag: 'nav_tab_activity',
      hasPill: false,
    },
    {
      tab: 'CHANNELS' as AppBottomTab,
      label: 'Channels',
      icon: Layers,
      testTag: 'nav_tab_channels',
      hasPill: false,
    },
    {
      tab: 'HISTORY' as AppBottomTab,
      label: 'History',
      icon: History,
      testTag: 'nav_tab_history',
      hasPill: false,
    },
  ];

  return (
    <nav className="fixed bottom-0 left-0 right-0 z-40 bg-white border-t border-[#E3E8EE] shadow-lg">
      <div className="max-w-md mx-auto px-6 py-1.5 flex items-center justify-between">
        {navItems.map((item) => {
          const isSelected = currentTab === item.tab;
          const IconComponent = item.icon;

          return (
            <button
              key={item.tab}
              onClick={() => setTab(item.tab)}
              data-testid={item.testTag}
              className="flex-1 flex flex-col items-center justify-center py-1 transition-all group"
            >
              <div
                className={`flex items-center justify-center transition-all ${
                  item.hasPill && isSelected
                    ? 'w-7 h-7 rounded-full bg-[#E9F1F5] text-[#1D5872]'
                    : isSelected
                    ? 'text-[#1D5872]'
                    : 'text-slate-400 group-hover:text-slate-600'
                }`}
              >
                <IconComponent className="w-5 h-5" />
              </div>

              <span
                className={`text-[10px] mt-0.5 tracking-tight ${
                  isSelected ? 'font-bold text-[#1D5872]' : 'font-medium text-slate-400'
                }`}
              >
                {item.label}
              </span>
            </button>
          );
        })}
      </div>
    </nav>
  );
};

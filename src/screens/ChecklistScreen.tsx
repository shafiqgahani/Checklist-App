import React from 'react';
import { ClipboardList } from 'lucide-react';
import { ScheduleDateCard } from '../components/ScheduleDateCard';
import { ChecklistFilterBar } from '../components/ChecklistFilterBar';
import { ChecklistTableHeader, ChecklistItemCard } from '../components/ChecklistItemCard';
import { useChecklist } from '../context/ChecklistContext';

export const ChecklistScreen: React.FC = () => {
  const { filteredItems } = useChecklist();

  return (
    <div className="flex-1 flex flex-col pb-20 overflow-y-auto" data-testid="checklist_main_screen">
      {/* Quick Action & Date Selector Card */}
      <ScheduleDateCard />

      {/* Filter & Search Bar */}
      <ChecklistFilterBar />

      {/* Column Headers (ITEM, ASSIGNMENT, STATUS) */}
      <ChecklistTableHeader />

      {/* Checklist Scrollable Feed */}
      <div className="max-w-4xl mx-auto w-full px-4 space-y-1.5 flex-1" data-testid="checklist_feed_container">
        {filteredItems.length === 0 ? (
          <div className="py-16 flex flex-col items-center justify-center text-center">
            <ClipboardList className="w-10 h-10 text-slate-300 mb-2" />
            <p className="text-xs text-slate-500 font-medium">No items match your criteria</p>
          </div>
        ) : (
          filteredItems.map((item) => (
            <ChecklistItemCard key={item.id} item={item} />
          ))
        )}
      </div>
    </div>
  );
};

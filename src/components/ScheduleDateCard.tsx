import React from 'react';
import { ChevronDown } from 'lucide-react';
import { useChecklist } from '../context/ChecklistContext';

export const ScheduleDateCard: React.FC = () => {
  const { currentChannel, displayDateText, setCalendarDialogVisible } = useChecklist();

  return (
    <div className="px-4 py-2.5 max-w-4xl mx-auto w-full">
      <div className="bg-white rounded-xl border border-[#E3E8EE] px-3.5 py-3 shadow-xs flex items-center justify-between gap-3">
        {/* Left info: Channel Tag & description */}
        <div className="flex items-center space-x-2.5 min-w-0">
          <div className="bg-[#E0F2FE] text-[#142C4B] font-extrabold text-xs px-2.5 py-1 rounded shrink-0">
            {currentChannel}
          </div>
          <div className="truncate">
            <h2 className="text-xs sm:text-sm font-bold text-slate-900 truncate">
              Daily playlist checklist
            </h2>
            <p className="text-[11px] text-slate-500 truncate">
              Tap channel tabs to update assignments...
            </p>
          </div>
        </div>

        {/* Right: Date Selector Trigger Button */}
        <button
          onClick={() => setCalendarDialogVisible(true)}
          data-testid="date_selector_button"
          className="shrink-0 bg-[#F8FAFC] hover:bg-[#F1F5F9] border border-[#E2E8F0] rounded-lg px-2.5 py-1.5 flex items-center space-x-2 transition-colors text-left"
          title="Change schedule date"
        >
          <div>
            <span className="block text-[8px] font-bold text-slate-400 tracking-wider uppercase">
              SCHEDULE DATE
            </span>
            <span className="block text-xs font-bold text-slate-800">
              {displayDateText}
            </span>
          </div>
          <ChevronDown className="w-4 h-4 text-slate-500 shrink-0" />
        </button>
      </div>
    </div>
  );
};

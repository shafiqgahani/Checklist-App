import React from 'react';
import { ArrowLeft } from 'lucide-react';
import { useChecklist } from '../context/ChecklistContext';

export const ChecklistHeader: React.FC = () => {
  const { currentTab, setTab, showToast } = useChecklist();

  const handleBack = () => {
    if (currentTab !== 'CHECKLIST') {
      setTab('CHECKLIST');
    } else {
      showToast('Channel checklist is at root level');
    }
  };

  const handleProfile = () => {
    showToast('Operator: SY · Shift: Day Broadcast Ops');
  };

  return (
    <header className="bg-[#142C4B] text-white px-5 pt-3 pb-4 shadow-md select-none">
      {/* Top Bar */}
      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-2">
          <button
            onClick={handleBack}
            data-testid="header_back_button"
            className="p-1.5 -ml-1 text-slate-200 hover:text-white rounded-lg hover:bg-[#1C3B63] transition-colors focus:outline-none"
            aria-label="Back"
          >
            <ArrowLeft className="w-5 h-5" />
          </button>
          <span className="text-lg font-bold tracking-tight">Checklist Ops</span>
        </div>

        {/* User Avatar Circle */}
        <button
          onClick={handleProfile}
          data-testid="user_avatar_button"
          className="w-8 h-8 rounded-full bg-[#E0F2FE] text-[#142C4B] font-bold text-xs flex items-center justify-center hover:opacity-90 transition-opacity active:scale-95 shadow-sm"
          title="Operator Profile"
        >
          SY
        </button>
      </div>

      {/* Header Title & Subtitle */}
      <div className="mt-3.5">
        <h1 className="text-xl sm:text-2xl font-bold tracking-tight">Channel checklist</h1>
        <p className="text-xs text-slate-300 mt-1 font-normal">
          Choose a schedule date, then review by channel
        </p>
      </div>
    </header>
  );
};

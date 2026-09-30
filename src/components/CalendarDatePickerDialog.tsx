import React, { useState } from 'react';
import { ChevronLeft, ChevronRight, X } from 'lucide-react';
import { useChecklist } from '../context/ChecklistContext';

export const CalendarDatePickerDialog: React.FC = () => {
  const { selectedDate, selectDate, setCalendarDialogVisible } = useChecklist();

  // Parse selectedDate YYYY-MM-DD
  const [selectedY, selectedM, selectedD] = selectedDate
    .split('-')
    .map((num) => parseInt(num, 10));

  const [viewYear, setViewYear] = useState<number>(selectedY || 2026);
  const [viewMonth, setViewMonth] = useState<number>(selectedM ? selectedM - 1 : 1); // 0-indexed
  const [pickedDay, setPickedDay] = useState<number>(selectedD || 24);

  const monthNames = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
  ];

  const handlePrevMonth = () => {
    if (viewMonth === 0) {
      setViewMonth(11);
      setViewYear((y) => y - 1);
    } else {
      setViewMonth((m) => m - 1);
    }
  };

  const handleNextMonth = () => {
    if (viewMonth === 11) {
      setViewMonth(0);
      setViewYear((y) => y + 1);
    } else {
      setViewMonth((m) => m + 1);
    }
  };

  const daysInMonth = new Date(viewYear, viewMonth + 1, 0).getDate();
  const firstDayOfWeek = new Date(viewYear, viewMonth, 1).getDay(); // Sunday = 0

  const handleSelectDay = (day: number) => {
    setPickedDay(day);
  };

  const handleDone = () => {
    const mm = String(viewMonth + 1).padStart(2, '0');
    const dd = String(pickedDay).padStart(2, '0');
    selectDate(`${viewYear}-${mm}-${dd}`);
  };

  const handleToday = () => {
    const now = new Date();
    setViewYear(now.getFullYear());
    setViewMonth(now.getMonth());
    setPickedDay(now.getDate());
  };

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-xs p-4 animate-in fade-in"
      onClick={() => setCalendarDialogVisible(false)}
    >
      <div
        className="bg-white w-full max-w-sm rounded-2xl shadow-2xl overflow-hidden flex flex-col"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Modal Header */}
        <div className="bg-[#142C4B] px-4 py-3.5 flex items-center justify-between text-white">
          <h2 className="text-sm font-bold">Select Schedule Date</h2>
          <button
            onClick={() => setCalendarDialogVisible(false)}
            data-testid="close_calendar_button"
            className="p-1 text-slate-300 hover:text-white rounded-lg hover:bg-white/10 transition-colors"
            aria-label="Close"
          >
            <X className="w-4 h-4" />
          </button>
        </div>

        {/* Month Navigation */}
        <div className="px-4 py-2.5 flex items-center justify-between border-b border-slate-100">
          <button
            onClick={handlePrevMonth}
            data-testid="prev_month_button"
            className="p-1.5 text-slate-700 hover:text-slate-900 rounded-lg hover:bg-slate-100 transition-colors"
            aria-label="Previous Month"
          >
            <ChevronLeft className="w-5 h-5" />
          </button>

          <span className="text-sm font-bold text-slate-900">
            {monthNames[viewMonth]} {viewYear}
          </span>

          <button
            onClick={handleNextMonth}
            data-testid="next_month_button"
            className="p-1.5 text-slate-700 hover:text-slate-900 rounded-lg hover:bg-slate-100 transition-colors"
            aria-label="Next Month"
          >
            <ChevronRight className="w-5 h-5" />
          </button>
        </div>

        {/* Day of Week Headers */}
        <div className="grid grid-cols-7 px-4 pt-2 text-center">
          {['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'].map((d) => (
            <div key={d} className="text-[11px] font-semibold text-slate-400 py-1">
              {d}
            </div>
          ))}
        </div>

        {/* Calendar Days Grid */}
        <div className="grid grid-cols-7 gap-y-1 px-4 py-2 text-center">
          {Array.from({ length: firstDayOfWeek }).map((_, i) => (
            <div key={`empty-${i}`} className="h-8" />
          ))}

          {Array.from({ length: daysInMonth }).map((_, i) => {
            const dayNum = i + 1;
            const isSelected = pickedDay === dayNum;

            return (
              <div key={dayNum} className="flex items-center justify-center h-8">
                <button
                  type="button"
                  onClick={() => handleSelectDay(dayNum)}
                  data-testid={`calendar_day_${dayNum}`}
                  className={`w-7 h-7 rounded-full text-xs font-semibold flex items-center justify-center transition-all ${
                    isSelected
                      ? 'bg-[#142C4B] text-white shadow-xs font-bold'
                      : 'text-slate-800 hover:bg-slate-100'
                  }`}
                >
                  {dayNum}
                </button>
              </div>
            );
          })}
        </div>

        {/* Footer Actions */}
        <div className="bg-[#F8FAFC] border-t border-[#E3E8EE] px-4 py-2.5 flex items-center justify-end space-x-3">
          <button
            type="button"
            onClick={handleToday}
            data-testid="pick_today_button"
            className="text-xs font-semibold text-slate-700 hover:text-slate-900 px-2 py-1"
          >
            Today
          </button>

          <button
            type="button"
            onClick={handleDone}
            data-testid="confirm_calendar_date_button"
            className="h-8.5 px-4 bg-[#1D5872] hover:bg-[#164458] text-white font-bold text-xs rounded-lg shadow-xs transition-colors"
          >
            Done
          </button>
        </div>
      </div>
    </div>
  );
};

import React from 'react';
import { History as HistoryIcon, CheckCircle2 } from 'lucide-react';
import { useChecklist } from '../context/ChecklistContext';

export const ActivityScreen: React.FC = () => {
  const { activityLogs } = useChecklist();

  return (
    <div className="flex-1 max-w-4xl mx-auto w-full px-4 py-3 pb-24 overflow-y-auto" data-testid="activity_screen">
      {/* Header */}
      <div className="flex items-center justify-between mb-3">
        <div>
          <h2 className="text-base font-bold text-slate-900">Operations Activity Log</h2>
          <p className="text-xs text-slate-500">Real-time audit trail of playlist updates</p>
        </div>
        <span className="bg-[#E0F2FE] text-[#142C4B] font-bold text-xs px-2.5 py-1 rounded-md">
          {activityLogs.length} Events
        </span>
      </div>

      {activityLogs.length === 0 ? (
        <div className="py-20 flex flex-col items-center justify-center text-center">
          <HistoryIcon className="w-12 h-12 text-slate-300 mb-2" />
          <p className="text-sm text-slate-500 font-medium">No operational events logged yet</p>
          <p className="text-xs text-slate-400 mt-1">Changes to items or statuses will appear here</p>
        </div>
      ) : (
        <div className="space-y-2">
          {activityLogs.map((log) => {
            const date = new Date(log.timestamp);
            const timeStr = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
            const dateStr = date.toLocaleDateString('en-GB', { day: '2-digit', month: 'short', year: 'numeric' });

            return (
              <div
                key={log.id}
                className="bg-white rounded-xl border border-[#E3E8EE] p-3 shadow-2xs flex items-start justify-between gap-3 hover:border-slate-300 transition-colors"
              >
                <div className="flex items-start space-x-2.5 min-w-0 flex-1">
                  <div className="w-7 h-7 rounded-full bg-[#E0F2FE] flex items-center justify-center shrink-0 mt-0.5">
                    <CheckCircle2 className="w-4 h-4 text-[#1D5872]" />
                  </div>

                  <div className="min-w-0 flex-1">
                    <div className="flex items-center space-x-2 flex-wrap gap-y-1">
                      <span className="bg-[#E0F2FE] text-[#142C4B] text-[10px] font-bold px-1.5 py-0.5 rounded">
                        {log.channel}
                      </span>
                      <span className="text-xs font-bold text-slate-900 truncate">
                        {log.itemName}
                      </span>
                    </div>

                    <p className="text-[11px] text-slate-500 mt-1">
                      Status updated:{' '}
                      <span className="font-semibold text-slate-700">{log.oldStatus}</span>
                      {' → '}
                      <span className="font-bold text-[#1D5872]">{log.newStatus}</span>
                    </p>
                  </div>
                </div>

                <div className="text-right shrink-0">
                  <span className="block text-xs font-semibold text-slate-900">{timeStr}</span>
                  <span className="block text-[10px] text-slate-400">{dateStr}</span>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

import React from 'react';
import { useChecklist } from '../context/ChecklistContext';
import { ITEM_STATUSES } from '../types';

export const ChannelsScreen: React.FC = () => {
  const { channelSummaries, selectChannel, setTab, markCurrentChannelComplete } = useChecklist();

  const handleOpenChannel = (channel: string) => {
    selectChannel(channel);
    setTab('CHECKLIST');
  };

  const handleMarkChecked = (channel: string) => {
    selectChannel(channel);
    markCurrentChannelComplete();
  };

  return (
    <div className="flex-1 max-w-4xl mx-auto w-full px-4 py-3 pb-24 overflow-y-auto" data-testid="channels_screen">
      {/* Header */}
      <div className="mb-3.5">
        <h2 className="text-base font-bold text-slate-900">Broadcaster Channels Overview</h2>
        <p className="text-xs text-slate-500">Monitor playlist verification progress across all 5 networks</p>
      </div>

      <div className="space-y-3">
        {channelSummaries.map((summary) => {
          const pct = Math.round(summary.completionRate * 100);

          return (
            <div
              key={summary.channel}
              className="bg-white rounded-xl border border-[#E3E8EE] p-3.5 shadow-2xs space-y-3 hover:border-slate-300 transition-all"
            >
              {/* Header row */}
              <div className="flex items-center justify-between">
                <div className="flex items-center space-x-2.5">
                  <span className="bg-[#E0F2FE] text-[#142C4B] font-extrabold text-xs px-2.5 py-1 rounded-md">
                    {summary.channel}
                  </span>
                  <span className="text-xs text-slate-500 font-medium">
                    {summary.totalItems} Checklist Items
                  </span>
                </div>

                <span
                  className={`text-xs font-bold ${
                    pct === 100 ? 'text-[#2C8D7B]' : 'text-[#142C4B]'
                  }`}
                >
                  {pct}% Complete
                </span>
              </div>

              {/* Progress Bar */}
              <div className="w-full bg-[#E2E8F0] h-1.5 rounded-full overflow-hidden">
                <div
                  className="h-full bg-[#2C8D7B] rounded-full transition-all duration-300"
                  style={{ width: `${pct}%` }}
                />
              </div>

              {/* Status Breakdown Chips */}
              <div className="flex items-center justify-between text-[11px] pt-0.5">
                <div className="flex items-center space-x-1">
                  <span
                    style={{ backgroundColor: ITEM_STATUSES['Completed'].accentColor }}
                    className="text-white font-bold px-1.5 py-0.5 rounded text-[10px]"
                  >
                    {summary.completedItems}
                  </span>
                  <span className="text-slate-500">Checked</span>
                </div>

                <div className="flex items-center space-x-1">
                  <span
                    style={{ backgroundColor: ITEM_STATUSES['In review'].accentColor }}
                    className="text-white font-bold px-1.5 py-0.5 rounded text-[10px]"
                  >
                    {summary.inReviewItems}
                  </span>
                  <span className="text-slate-500">Review</span>
                </div>

                <div className="flex items-center space-x-1">
                  <span
                    style={{ backgroundColor: ITEM_STATUSES['Needs attention'].accentColor }}
                    className="text-white font-bold px-1.5 py-0.5 rounded text-[10px]"
                  >
                    {summary.attentionItems}
                  </span>
                  <span className="text-slate-500">Alert</span>
                </div>

                <div className="flex items-center space-x-1">
                  <span
                    style={{ backgroundColor: ITEM_STATUSES['Not started'].accentColor }}
                    className="text-white font-bold px-1.5 py-0.5 rounded text-[10px]"
                  >
                    {summary.pendingItems}
                  </span>
                  <span className="text-slate-500">Pending</span>
                </div>
              </div>

              {/* Action Buttons */}
              <div className="flex items-center space-x-2 pt-1">
                <button
                  type="button"
                  onClick={() => handleMarkChecked(summary.channel)}
                  className="flex-1 h-9 rounded-lg border border-[#CBD5E1] hover:bg-slate-50 text-[#142C4B] font-bold text-xs transition-colors"
                >
                  Mark Checked
                </button>

                <button
                  type="button"
                  onClick={() => handleOpenChannel(summary.channel)}
                  className="flex-1 h-9 rounded-lg bg-[#1D5872] hover:bg-[#164458] text-white font-bold text-xs transition-colors shadow-xs"
                >
                  Open Checklist
                </button>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

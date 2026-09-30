import React from 'react';
import { Copy, CheckCircle } from 'lucide-react';
import { useChecklist } from '../context/ChecklistContext';

export const HistoryScreen: React.FC = () => {
  const { displayDateText, channelSummaries, showToast } = useChecklist();

  const totalItems = channelSummaries.reduce((sum, s) => sum + s.totalItems, 0);
  const totalCompleted = channelSummaries.reduce((sum, s) => sum + s.completedItems, 0);
  const totalAttention = channelSummaries.reduce((sum, s) => sum + s.attentionItems, 0);
  const overallPct = totalItems > 0 ? Math.round((totalCompleted * 100) / totalItems) : 0;

  const handleCopyReport = () => {
    const lines = [
      'CHECKLIST OPS - BROADCAST AUDIT REPORT',
      `Schedule Date: ${displayDateText}`,
      `Overall Compliance: ${overallPct}% (${totalCompleted}/${totalItems} items)`,
      '----------------------------------------',
      ...channelSummaries.map(
        (s) =>
          `${s.channel}: ${s.completedItems}/${s.totalItems} Checked (${Math.round(s.completionRate * 100)}%)`
      ),
      'Signed off by: SY (Shift Supervisor)',
    ];
    const reportText = lines.join('\n');

    if (navigator.clipboard && navigator.clipboard.writeText) {
      navigator.clipboard.writeText(reportText).catch(() => {});
    }
    showToast('Audit report copied to clipboard');
  };

  return (
    <div className="flex-1 max-w-4xl mx-auto w-full px-4 py-3 pb-24 overflow-y-auto" data-testid="history_screen">
      {/* Header */}
      <div className="mb-3.5">
        <h2 className="text-base font-bold text-slate-900">Broadcast Shift History & Audit</h2>
        <p className="text-xs text-slate-500">
          Verification logs and compliance reporting for {displayDateText}
        </p>
      </div>

      {/* Compliance Hero Card */}
      <div className="bg-[#142C4B] rounded-2xl p-4.5 text-white shadow-md space-y-4">
        <div className="flex items-center justify-between">
          <div>
            <span className="block text-[10px] font-bold text-slate-300 tracking-wider uppercase">
              DAILY AUDIT COMPLIANCE
            </span>
            <h3 className="text-lg font-extrabold text-white mt-0.5">{displayDateText}</h3>
          </div>

          <span className="bg-[#E0F2FE] text-[#142C4B] font-extrabold text-xs px-2.5 py-1.5 rounded-lg shadow-xs">
            {overallPct}% PASSED
          </span>
        </div>

        {/* Stats Row */}
        <div className="grid grid-cols-3 gap-2 pt-1 border-t border-white/10">
          <div>
            <span className="block text-[11px] text-slate-300 font-medium">Total Checkpoints</span>
            <span className="block text-base font-bold text-white mt-0.5">{totalItems}</span>
          </div>
          <div>
            <span className="block text-[11px] text-slate-300 font-medium">Verified Items</span>
            <span className="block text-base font-bold text-[#38A169] mt-0.5">{totalCompleted}</span>
          </div>
          <div>
            <span className="block text-[11px] text-slate-300 font-medium">Needs Attention</span>
            <span className="block text-base font-bold text-[#F87171] mt-0.5">{totalAttention}</span>
          </div>
        </div>

        {/* Copy Button */}
        <button
          onClick={handleCopyReport}
          data-testid="copy_audit_report_button"
          className="w-full h-10 bg-[#1D5872] hover:bg-[#164458] text-white font-bold text-xs rounded-xl shadow-xs transition-colors flex items-center justify-center space-x-2"
        >
          <Copy className="w-4 h-4" />
          <span>Copy Shift Compliance Report</span>
        </button>
      </div>

      {/* Network Audit Status Breakdown */}
      <div className="mt-5 space-y-2">
        <h4 className="text-xs font-bold text-slate-800 uppercase tracking-wider">
          Network Audit Status Breakdown
        </h4>

        <div className="space-y-2">
          {channelSummaries.map((summary) => {
            const isAllCompleted = summary.completedItems === summary.totalItems && summary.totalItems > 0;
            const pendingCount = summary.totalItems - summary.completedItems;

            return (
              <div
                key={summary.channel}
                className="bg-white rounded-xl border border-[#E3E8EE] p-3 flex items-center justify-between shadow-2xs hover:border-slate-300 transition-colors"
              >
                <div className="flex items-center space-x-2.5">
                  <span className="bg-[#E0F2FE] text-[#142C4B] font-bold text-[11px] px-2 py-0.5 rounded">
                    {summary.channel}
                  </span>
                  <span className="text-xs text-slate-700 font-medium">
                    {summary.completedItems} / {summary.totalItems} verified
                  </span>
                </div>

                {isAllCompleted ? (
                  <div className="flex items-center space-x-1 text-[#2C8D7B]">
                    <CheckCircle className="w-4 h-4" />
                    <span className="text-[11px] font-bold tracking-wide">COMPLIANT</span>
                  </div>
                ) : (
                  <span className="text-[11px] font-bold text-slate-400 tracking-wide">
                    {pendingCount} PENDING
                  </span>
                )}
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

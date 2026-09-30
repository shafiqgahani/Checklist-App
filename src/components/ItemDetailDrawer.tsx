import React, { useState, useMemo } from 'react';
import { X, Check } from 'lucide-react';
import { ChecklistItem, ItemStatusType, ITEM_STATUSES, ParameterRowItem } from '../types';
import { useChecklist } from '../context/ChecklistContext';

export function parseParameterSettings(assignmentText: string): ParameterRowItem[] {
  return assignmentText
    .split(';')
    .map((s) => s.trim())
    .filter((s) => s.length > 0)
    .map((segment, index) => {
      const hasArrow = segment.includes('→') || segment.includes('->');
      const containsNoSetting = /no setting/i.test(segment);
      const containsLogoOnly = /logo only/i.test(segment);

      if (hasArrow) {
        const arrow = segment.includes('→') ? '→' : '->';
        const arrowIndex = segment.indexOf(arrow);
        const condition = segment.substring(0, arrowIndex).trim();
        const setting = segment.substring(arrowIndex + arrow.length).trim();
        return {
          rowNumber: index + 1,
          isOperationalNote: false,
          condition,
          setting,
          rawText: segment,
          containsNoSetting,
          containsLogoOnly,
        };
      } else {
        return {
          rowNumber: index + 1,
          isOperationalNote: true,
          condition: '',
          setting: '',
          rawText: segment,
          containsNoSetting,
          containsLogoOnly,
        };
      }
    });
}

export const ItemDetailDrawer: React.FC<{ item: ChecklistItem }> = ({ item }) => {
  const { closeItemDetail, updateItemStatus, showToast } = useChecklist();
  const [selectedStatus, setSelectedStatus] = useState<ItemStatusType>(item.status);
  const [notesText, setNotesText] = useState<string>(item.notes || '');

  const isParameterSetting = item.item.toLowerCase() === 'parameter setting';
  const parameterRows = useMemo(() => {
    return isParameterSetting ? parseParameterSettings(item.assignment) : [];
  }, [item.assignment, isParameterSetting]);

  const handleSave = () => {
    updateItemStatus(item, selectedStatus, notesText);
    showToast(`Item updated to ${ITEM_STATUSES[selectedStatus].displayTitle}`);
  };

  const currentStatusConfig = ITEM_STATUSES[selectedStatus];

  return (
    <div
      className="fixed inset-0 z-50 flex items-end sm:items-center justify-center bg-black/60 backdrop-blur-xs transition-opacity animate-in fade-in"
      onClick={closeItemDetail}
    >
      <div
        className="bg-white w-full sm:max-w-2xl max-h-[92vh] sm:max-h-[88vh] rounded-t-2xl sm:rounded-2xl shadow-2xl flex flex-col overflow-hidden animate-in slide-in-from-bottom duration-200"
        onClick={(e) => e.stopPropagation()}
      >
        {/* 1. STICKY HEADER */}
        <div className="bg-white px-5 py-3.5 border-b border-[#E3E8EE] shrink-0">
          <div className="flex items-start justify-between gap-3">
            <div className="space-y-1.5 flex-1 min-w-0">
              <div className="flex items-center space-x-2">
                {/* Channel Badge */}
                <span className="bg-[#E0F2FE] text-[#142C4B] font-extrabold text-[11px] px-2 py-0.5 rounded">
                  {item.channel}
                </span>

                {/* Status Badge */}
                <span
                  style={{
                    backgroundColor: currentStatusConfig.bgColor,
                    color: currentStatusConfig.textColor,
                    borderColor: currentStatusConfig.borderColor,
                  }}
                  className="px-2 py-0.5 rounded-full text-[10px] font-bold border"
                >
                  {currentStatusConfig.displayTitle}
                </span>
              </div>

              {/* Deep Navy Heading */}
              <h2 className="text-base sm:text-lg font-bold text-[#142C4B] leading-snug">
                {item.item}
              </h2>
            </div>

            <button
              onClick={closeItemDetail}
              data-testid="close_drawer_button"
              className="p-1.5 text-slate-400 hover:text-slate-700 rounded-lg hover:bg-slate-100 transition-colors"
              aria-label="Close details"
            >
              <X className="w-5 h-5" />
            </button>
          </div>
        </div>

        {/* 2. SCROLLABLE BODY */}
        <div className="p-5 overflow-y-auto space-y-4 flex-1">
          {isParameterSetting ? (
            <div>
              <div className="flex items-center justify-between pb-2 mb-2">
                <span className="text-[11px] font-extrabold text-[#142C4B] tracking-wider uppercase">
                  STRUCTURED PARAMETER SPECIFICATIONS
                </span>
                <span className="text-[11px] font-semibold text-slate-500">
                  {parameterRows.length} Rows
                </span>
              </div>

              <div className="space-y-2" data-testid="parameter_setting_rows_container">
                {parameterRows.map((row) => {
                  let rowBg = '#FFF1F2'; // default light rose
                  if (row.containsNoSetting) rowBg = '#EFF6FF'; // light blue
                  else if (row.containsLogoOnly) rowBg = '#FFFBEB'; // light amber
                  else if (row.isOperationalNote) rowBg = '#F8FAFC'; // light grey

                  return (
                    <div
                      key={row.rowNumber}
                      data-testid={`parameter_row_${row.rowNumber}`}
                      style={{ backgroundColor: rowBg }}
                      className="rounded-lg border border-[#E3E8EE] p-3 space-y-2 text-xs"
                    >
                      <div className="flex items-center justify-between">
                        <span className="bg-white border border-[#CBD5E1] text-slate-700 text-[10px] font-bold px-1.5 py-0.5 rounded">
                          #{String(row.rowNumber).padStart(2, '0')}
                        </span>

                        <div className="flex items-center space-x-1.5">
                          {row.isOperationalNote && (
                            <span className="bg-[#E2E8F0] text-slate-700 text-[9px] font-extrabold px-1.5 py-0.5 rounded uppercase tracking-wider">
                              OPERATIONAL NOTE
                            </span>
                          )}
                          {row.containsNoSetting && (
                            <span className="bg-[#DBEAFE] text-[#1E40AF] text-[9px] font-extrabold px-1.5 py-0.5 rounded uppercase tracking-wider">
                              NO SETTING
                            </span>
                          )}
                          {row.containsLogoOnly && (
                            <span className="bg-[#FEF3C7] text-[#92400E] text-[9px] font-extrabold px-1.5 py-0.5 rounded uppercase tracking-wider">
                              LOGO ONLY
                            </span>
                          )}
                        </div>
                      </div>

                      {row.isOperationalNote ? (
                        <div>
                          <div className="text-[10px] font-bold text-slate-500 tracking-wider">
                            INSTRUCTION / NOTE
                          </div>
                          <div className="text-slate-900 text-xs sm:text-sm font-medium mt-0.5">
                            {row.rawText}
                          </div>
                        </div>
                      ) : (
                        <>
                          <div>
                            <div className="text-[10px] font-bold text-slate-500 tracking-wider">
                              PROGRAMME / CONDITION
                            </div>
                            <div className="text-[#142C4B] text-xs sm:text-sm font-bold mt-0.5">
                              {row.condition}
                            </div>
                          </div>
                          <div>
                            <div className="text-[10px] font-bold text-slate-500 tracking-wider">
                              REQUIRED SETTING
                            </div>
                            <div className="text-slate-900 text-xs sm:text-sm font-normal mt-0.5">
                              {row.setting}
                            </div>
                          </div>
                        </>
                      )}
                    </div>
                  );
                })}
              </div>
            </div>
          ) : (
            <div>
              <span className="block text-[10px] font-bold text-slate-400 tracking-wider uppercase mb-1.5">
                COMPLETE ASSIGNMENT & PARAMETERS
              </span>
              <div className="bg-[#F8FAFC] border border-[#E3E8EE] rounded-lg p-3">
                <p className="font-mono text-xs text-slate-700 leading-relaxed whitespace-pre-wrap">
                  {item.assignment}
                </p>
              </div>
            </div>
          )}

          {/* Status Radio Choices */}
          <div className="pt-2">
            <span className="block text-[11px] font-bold text-[#142C4B] tracking-wider uppercase mb-2">
              UPDATE ITEM STATUS
            </span>

            <div className="space-y-2">
              {(Object.keys(ITEM_STATUSES) as ItemStatusType[]).map((statusKey) => {
                const conf = ITEM_STATUSES[statusKey];
                const isSelected = selectedStatus === statusKey;
                return (
                  <button
                    type="button"
                    key={statusKey}
                    onClick={() => setSelectedStatus(statusKey)}
                    data-testid={`status_option_${conf.dbValue}`}
                    className={`w-full text-left rounded-lg p-2.5 flex items-center justify-between border transition-all ${
                      isSelected
                        ? 'bg-[#F0FDFA] border-[#1D5872] shadow-2xs'
                        : 'bg-white border-[#E2E8F0] hover:border-slate-300'
                    }`}
                  >
                    <div className="flex items-center space-x-2.5">
                      <span
                        className="w-2.5 h-2.5 rounded-full shrink-0"
                        style={{ backgroundColor: conf.accentColor }}
                      />
                      <span
                        className={`text-xs ${
                          isSelected ? 'font-bold text-[#142C4B]' : 'font-medium text-slate-700'
                        }`}
                      >
                        {conf.displayTitle}
                      </span>
                    </div>

                    {isSelected && (
                      <Check className="w-4 h-4 text-[#1D5872] shrink-0" />
                    )}
                  </button>
                );
              })}
            </div>
          </div>

          {/* Shift Remarks / Notes */}
          <div className="pt-1">
            <label className="block text-[11px] font-bold text-[#142C4B] tracking-wider uppercase mb-1.5">
              OPERATOR SHIFT NOTES (OPTIONAL)
            </label>
            <input
              type="text"
              value={notesText}
              onChange={(e) => setNotesText(e.target.value)}
              placeholder="e.g. Verified by SY at 18:45, clock locked"
              data-testid="notes_input_field"
              className="w-full text-xs font-medium px-3 py-2.5 bg-[#F8FAFC] border border-[#CBD5E1] rounded-lg text-slate-900 placeholder:text-slate-400 focus:outline-none focus:ring-1 focus:ring-[#1D5872] focus:border-[#1D5872]"
            />
          </div>
        </div>

        {/* 3. FOOTER ACTION */}
        <div className="p-4 bg-slate-50 border-t border-[#E3E8EE] shrink-0">
          <button
            onClick={handleSave}
            data-testid="drawer_save_button"
            className="w-full h-11 bg-[#1D5872] hover:bg-[#164458] text-white font-bold text-xs rounded-xl shadow-md transition-colors active:scale-98 flex items-center justify-center space-x-2"
          >
            <span>Confirm & Save</span>
          </button>
        </div>
      </div>
    </div>
  );
};

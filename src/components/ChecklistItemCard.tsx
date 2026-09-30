import React from 'react';
import { ChecklistItem, ITEM_STATUSES } from '../types';
import { useChecklist } from '../context/ChecklistContext';

export const ChecklistTableHeader: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto w-full px-5 py-1.5 flex items-center text-[10px] font-bold text-slate-400 tracking-wider">
      <div className="w-[35%]">ITEM</div>
      <div className="w-[42%] px-1">ASSIGNMENT</div>
      <div className="w-[23%] text-right">STATUS</div>
    </div>
  );
};

export const ChecklistItemCard: React.FC<{ item: ChecklistItem }> = ({ item }) => {
  const { openItemDetail, cycleItemStatus } = useChecklist();
  const statusConfig = ITEM_STATUSES[item.status] || ITEM_STATUSES['Not started'];

  return (
    <div
      onClick={() => openItemDetail(item)}
      data-testid={`checklist_card_${item.id}`}
      className="bg-white rounded-xl border border-[#E2E8F0] shadow-xs hover:border-slate-300 hover:shadow-sm transition-all cursor-pointer overflow-hidden flex"
    >
      {/* Left 4px accent color bar */}
      <div
        className="w-1 shrink-0"
        style={{ backgroundColor: statusConfig.accentColor }}
      />

      {/* Card Content Row */}
      <div className="flex-1 px-3 py-2.5 flex items-center justify-between gap-2 min-w-0">
        {/* Col 1: Item Title (~35%) */}
        <div className="w-[35%] pr-1 min-w-0">
          <h3 className="text-xs font-bold text-slate-900 leading-tight line-clamp-2">
            {item.item}
          </h3>
          {item.notes && (
            <p className="text-[10px] text-teal-700 italic truncate mt-0.5">
              Note: {item.notes}
            </p>
          )}
        </div>

        {/* Col 2: Assignment Description (~42%) */}
        <div className="w-[42%] px-1 min-w-0">
          <p className="text-[11px] text-slate-500 leading-tight line-clamp-2">
            {item.assignment}
          </p>
        </div>

        {/* Col 3: Status Badge Button (~23%) */}
        <div className="w-[23%] flex justify-end shrink-0">
          <button
            type="button"
            onClick={(e) => {
              e.stopPropagation();
              cycleItemStatus(item);
            }}
            data-testid={`status_toggle_${item.id}`}
            style={{
              backgroundColor: statusConfig.bgColor,
              color: statusConfig.textColor,
              borderColor: statusConfig.borderColor,
            }}
            className="px-2 py-1 rounded-md text-[10px] font-extrabold tracking-wide border transition-transform active:scale-95 text-center min-w-[62px]"
            title={`Status: ${statusConfig.displayTitle}. Click to cycle.`}
          >
            {statusConfig.badgeLabel}
          </button>
        </div>
      </div>
    </div>
  );
};

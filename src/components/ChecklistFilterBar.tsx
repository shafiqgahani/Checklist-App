import React from 'react';
import { Search, X } from 'lucide-react';
import { useChecklist } from '../context/ChecklistContext';

export const ChecklistFilterBar: React.FC = () => {
  const { searchQuery, setSearchQuery, activeFilter, setFilter, filterCounts } = useChecklist();

  const filterOptions = [
    { title: 'All', count: filterCounts.all, key: 'ALL' },
    { title: 'Completed', count: filterCounts.completed, key: 'Completed' },
    { title: 'Review', count: filterCounts.inReview, key: 'In review' },
    { title: 'Attention', count: filterCounts.attention, key: 'Needs attention' },
    { title: 'Pending', count: filterCounts.pending, key: 'Not started' },
  ];

  return (
    <div className="px-4 py-1.5 max-w-4xl mx-auto w-full space-y-2">
      {/* Search Input Box */}
      <div className="relative flex items-center">
        <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-slate-400">
          <Search className="w-4 h-4" />
        </div>
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Filter item name or assignment..."
          data-testid="item_search_input"
          className="w-full h-9.5 pl-9 pr-8 text-xs font-medium bg-white border border-[#CBD5E1] rounded-lg text-slate-900 placeholder:text-slate-400 focus:outline-none focus:ring-1 focus:ring-[#1D5872] focus:border-[#1D5872] transition-all shadow-2xs"
        />
        {searchQuery && (
          <button
            onClick={() => setSearchQuery('')}
            data-testid="clear_search_button"
            className="absolute inset-y-0 right-0 pr-2.5 flex items-center text-slate-400 hover:text-slate-600"
            aria-label="Clear search"
          >
            <X className="w-4 h-4" />
          </button>
        )}
      </div>

      {/* Status Filter Pills Row */}
      <div className="flex items-center space-x-1.5 overflow-x-auto pb-1 scrollbar-none">
        {filterOptions.map((opt) => {
          const isSelected = activeFilter === opt.key;
          return (
            <button
              key={opt.key}
              onClick={() => setFilter(opt.key)}
              data-testid={`filter_pill_${opt.key}`}
              className={`shrink-0 px-2.5 py-1 rounded-full text-[11px] font-semibold transition-all border ${
                isSelected
                  ? 'bg-[#1D5872] border-[#1D5872] text-white shadow-xs'
                  : 'bg-white border-[#E2E8F0] text-slate-700 hover:border-slate-300'
              }`}
            >
              {opt.title} ({opt.count})
            </button>
          );
        })}
      </div>
    </div>
  );
};

import React from 'react';
import { CHANNELS } from '../data/defaultData';
import { useChecklist } from '../context/ChecklistContext';

export const ChannelTabsBar: React.FC = () => {
  const { currentChannel, selectChannel, currentTab, setTab } = useChecklist();

  const handleSelectChannel = (channel: string) => {
    selectChannel(channel);
    if (currentTab !== 'CHECKLIST') {
      setTab('CHECKLIST');
    }
  };

  return (
    <nav className="bg-white border-b border-[#E3E8EE] px-4 py-2.5 shadow-xs">
      <div className="flex space-x-2 max-w-4xl mx-auto">
        {CHANNELS.map((channel) => {
          const isSelected = channel.toLowerCase() === currentChannel.toLowerCase();
          return (
            <button
              key={channel}
              onClick={() => handleSelectChannel(channel)}
              data-testid={`channel_tab_${channel}`}
              className={`flex-1 h-9 rounded-lg font-bold text-xs sm:text-sm transition-all flex items-center justify-center ${
                isSelected
                  ? 'bg-[#142C4B] text-white shadow-sm'
                  : 'bg-[#F1F4F8] text-[#334155] hover:bg-[#E2E8F0]'
              }`}
            >
              {channel}
            </button>
          );
        })}
      </div>
    </nav>
  );
};

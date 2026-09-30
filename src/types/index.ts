export type ItemStatusType = 'Completed' | 'In review' | 'Needs attention' | 'Not started';

export interface StatusConfig {
  dbValue: ItemStatusType;
  badgeLabel: string;
  displayTitle: string;
  bgColor: string;
  textColor: string;
  borderColor: string;
  accentColor: string;
}

export const ITEM_STATUSES: Record<ItemStatusType, StatusConfig> = {
  'Completed': {
    dbValue: 'Completed',
    badgeLabel: 'CHECKED',
    displayTitle: 'Completed',
    bgColor: '#E8F7F2',
    textColor: '#1E7B68',
    borderColor: '#38A169',
    accentColor: '#2C8D7B',
  },
  'In review': {
    dbValue: 'In review',
    badgeLabel: 'REVIEW',
    displayTitle: 'In review',
    bgColor: '#FFF6E5',
    textColor: '#B7791F',
    borderColor: '#ECC94B',
    accentColor: '#EEB35B',
  },
  'Needs attention': {
    dbValue: 'Needs attention',
    badgeLabel: 'ALERT',
    displayTitle: 'Needs attention',
    bgColor: '#FDEDEC',
    textColor: '#C53030',
    borderColor: '#E53E3E',
    accentColor: '#E53E3E',
  },
  'Not started': {
    dbValue: 'Not started',
    badgeLabel: 'PENDING',
    displayTitle: 'Not started',
    bgColor: '#F1F4F8',
    textColor: '#64748B',
    borderColor: '#94A3B8',
    accentColor: '#94A3B8',
  },
};

export const NEXT_STATUS_MAP: Record<ItemStatusType, ItemStatusType> = {
  'Completed': 'In review',
  'In review': 'Needs attention',
  'Needs attention': 'Not started',
  'Not started': 'Completed',
};

export interface ChecklistItem {
  id: string;
  channel: string;
  item: string;
  assignment: string;
  status: ItemStatusType;
  scheduleDate: string; // YYYY-MM-DD
  notes: string;
  lastUpdated: number;
}

export interface ActivityLog {
  id: number;
  channel: string;
  itemName: string;
  oldStatus: string;
  newStatus: string;
  timestamp: number;
}

export type AppBottomTab = 'CHECKLIST' | 'ACTIVITY' | 'CHANNELS' | 'HISTORY';

export interface FilterCounts {
  all: number;
  completed: number;
  inReview: number;
  attention: number;
  pending: number;
}

export interface ChannelSummary {
  channel: string;
  totalItems: number;
  completedItems: number;
  inReviewItems: number;
  attentionItems: number;
  pendingItems: number;
  completionRate: number;
}

export interface ParameterRowItem {
  rowNumber: number;
  isOperationalNote: Boolean;
  condition: string;
  setting: string;
  rawText: string;
  containsNoSetting: Boolean;
  containsLogoOnly: Boolean;
}

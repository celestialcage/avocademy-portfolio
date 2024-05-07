/**
 * 
 */

const Calendar = tui.Calendar;

const container = document.getElementById('calendar');
const options = {
  usageStatistics: false, 
  defaultView: 'week',
  timezone: {
    zones: [
      {
        timezoneName: 'Asia/Seoul',
        displayLabel: 'Seoul',
      },
      {
        timezoneName: 'Europe/London',
        displayLabel: 'London',
      },
    ],
  },
  calendars: [
    {
      id: 'cal1',
      name: '개인',
      backgroundColor: '#03bd9e',
    },
    {
      id: 'cal2',
      name: '직장',
      backgroundColor: '#00a9ff',
    },
  ],
};

const calendar = new Calendar(container, options);
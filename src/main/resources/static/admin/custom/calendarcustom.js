/**
 * 
 */

const Calendar = tui.Calendar;

const container = document.getElementById('calendar');
const options = {
  usageStatistics: false, 
  defaultView: 'month',
  timezone: {
    zones: [
      {
        timezoneName: 'Asia/Seoul',
        displayLabel: 'Seoul',
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
  month: {
    visibleWeeksCount: 2,
    workweek: true,
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
  },
  // week: {
  //   eventView: false,
  //   taskView: false,
  //   hourStart: 9, 
  //   hourEnd: 18, 
  //   dayNames: ['월', '화', '수', '목', '금', '토', '일'],
  // },
};

const calendar = new Calendar(container, options);
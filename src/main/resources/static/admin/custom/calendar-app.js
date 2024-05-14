(function(Calendar) {
	// const Calendar = tui.Calendar;
	const container = document.getElementById('calendar');
	const options = {
	  gridSelection: {
		enableDblClick: false,
		enableClick: false,
	  },
	  useFormPopup: false,
	  usageStatistics: false, 
	  defaultView: 'week',
	  timezone: {
	    zones: [
	      {
	        timezoneName: 'Asia/Seoul',
	        displayLabel: 'Seoul',
			timeHeight: 60,
	      },
	    ],
	  },
	  calendars: [
	    {
	      id: 'cal1',
	      name: '개인',
	      backgroundColor: '#03bd9e',
	    },
	  ],
	//   month: {
	//     visibleWeeksCount: 2,
	//     workweek: true,
	//     dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	//   },
	  week: {
        workweek: true,
	      eventView: true,
	      taskView: false,
	      hourStart: 9, 
	      hourEnd: 18, 
	      dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	  },
	  template: {
		titlePlaceholder: function () {
			return '예약 가능';
		  },
		allday: function (event) {
		  return getEventTemplate(event, true);
		},
		time: function (event) {
		  return getEventTemplate(event, false);
		},
	  },
	};
	
	const calendar = new Calendar(container, options);
	
	const navbarRange = document.querySelector('.calendar-render-range');
	
	function displayRenderRange() {
	  let rangeStart = calendar.getDateRangeStart();
	  let rangeEnd = calendar.getDateRangeEnd();
	
	  navbarRange.textContent = getNavbarRange(rangeStart, rangeEnd, calendar.getViewName());
	}

  function reloadEvents() {
    calendar.clear();
    getDBEvents(
      calendar.getViewName()//,
      //calendar.getDateRangeStart(), // data_TZDate 리턴
      //calendar.getDateRangeEnd() // data_TZDate 리턴
    ).then(data => {
		calendar.createEvents(data);
	});
  }
	
	function update() {
		//setDropdownTriggerText();
	    displayRenderRange();
	    reloadEvents();
	}

	function bindInstanceEvents() {
		calendar.on({
		  clickMoreEventsBtn: function (btnInfo) {
			console.log('clickMoreEventsBtn', btnInfo);
		  },
		  clickEvent: function (eventInfo) {
			console.log('clickEvent', eventInfo);
			
		  },
		  clickDayName: function (dayNameInfo) {
			console.log('clickDayName', dayNameInfo);
		  },
		  selectDateTime: function (dateTimeInfo) {
			console.log('selectDateTime', dateTimeInfo);
		  },
		  beforeCreateEvent: function (event) {
			console.log('beforeCreateEvent', event);
			event.id = chance.guid();
	
			calendar.createEvents([event]);
			calendar.clearGridSelections();
		  },
		  beforeUpdateEvent: function (eventInfo) {
			var event, changes;
	
			console.log('beforeUpdateEvent', eventInfo);
	
			event = eventInfo.event;
			changes = eventInfo.changes;
	
			calendar.updateEvent(event.id, event.calendarId, changes);
		  },
		  beforeDeleteEvent: function (eventInfo) {
			console.log('beforeDeleteEvent', eventInfo);
	
			calendar.deleteEvent(eventInfo.id, eventInfo.calendarId);
		  },
		});
	  }

	  function getEventTemplate(event, isAllday) {
		let html = [];
		let start = moment(event.start.toDate().toUTCString());
		if (!isAllday) {
		  html.push('<strong>' + start.format('HH:mm') + '</strong> ');
		}
	
		if (event.isPrivate) {
		  html.push('<span class="calendar-font-icon ic-lock-b"></span>');
		  html.push(' Private');
		} else {
		  if (event.recurrenceRule) {
			html.push('<span class="calendar-font-icon ic-repeat-b"></span>');
		  } else if (event.attendees.length > 0) {
			html.push('<span class="calendar-font-icon ic-user-b"></span>');
		  } else if (event.location) {
			html.push('<span class="calendar-font-icon ic-location-b"></span>');
		  }
		  html.push(' ' + event.title);
		}
	
		return html.join('');
	  }

	
	
	// 캘린더 메뉴
	const todayBtn = document.querySelector('#todayBtn');
	const prevBtn = document.querySelector('#prevBtn');
	const nextBtn = document.querySelector('#nextBtn');
	
	const monthViewBtn = document.querySelector('#monthViewBtn');
    const twoWeeksViewBtn = document.querySelector('#twoWeeksViewBtn');
	const weekViewBtn = document.querySelector('#weekViewBtn');
	
	todayBtn.addEventListener("click", () => {
		calendar.today();
		update();
		calendar.clearGridSelections();
	});
	prevBtn.addEventListener("click", () => {
		calendar.prev();
		update();
		calendar.clearGridSelections();
	});
	nextBtn.addEventListener("click", () => {
		calendar.next();
		update();
		calendar.clearGridSelections();
	});
	
	monthViewBtn.addEventListener("click", () => {
		calendar.changeView('month');
		update();
		calendar.setOptions({
		month: {
			visibleWeeksCount: 0,
			workweek: false,
			narrowWeekend: true,
			dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		},
		});
		calendar.clearGridSelections();
	});
    twoWeeksViewBtn.addEventListener("click", () => {
		calendar.changeView('month');
		update();
		calendar.setOptions({
		month: {
			visibleWeeksCount: 2,
			workweek: true,
			dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		},
		});
		calendar.clearGridSelections();
	});

	weekViewBtn.addEventListener("click", () => {
	  calendar.changeView('week');
      update();
	  calendar.setOptions({
	    week: {
        workweek: true,
	      eventView: true,
	      taskView: false,
	      hourStart: 9, 
	      hourEnd: 18, 
	      dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	    },
	  });
	  calendar.clearGridSelections();
	});

	console.log(calendar);

	// Init
	bindInstanceEvents();
	// bindAppEvents();
	update();
	
})(tui.Calendar);



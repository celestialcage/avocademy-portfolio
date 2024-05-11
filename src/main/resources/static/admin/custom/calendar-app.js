(function(Calendar) {
	// const Calendar = tui.Calendar;
	const container = document.getElementById('calendar');
	const options = {
	  gridSelection: {
		enableDblClick: false,
		enableClick: true,
	  },
	  useFormPopup: true,
	  usageStatistics: false, 
	  defaultView: 'month',
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
	  month: {
	    visibleWeeksCount: 2,
	    workweek: true,
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
	  // week: {
	  //   eventView: false,
	  //   // taskView: false,
	  //   hourStart: 9, 
	  //   hourEnd: 18, 
	  //   dayNames: ['월', '화', '수', '목', '금', '토', '일'],
	  // },
	};
	
	const calendar = new Calendar(container, options);
	
	const navbarRange = document.querySelector('.calendar-render-range');
	
	function displayRenderRange() {
	  let rangeStart = calendar.getDateRangeStart();
	  let rangeEnd = calendar.getDateRangeEnd();
	
	  navbarRange.textContent = getNavbarRange(rangeStart, rangeEnd, calendar.getViewName());
	  //console.log('displayRenderRange() 실행');
	}

  function reloadEvents() {
    let eventsList;

    calendar.clear();
    eventsList = getDBEvents(
      calendar.getViewName(),
      calendar.getDateRangeStart(),
      calendar.getDateRangeEnd()
    );
    calendar.createEvents(eventsList);
  }
	
	function update() {
		//setDropdownTriggerText();
	    displayRenderRange();
	    reloadEvents();
		//console.log('update() 실행');
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

	  function bindAppEvents() {
		// dropdownTrigger.addEventListener('click', toggleDropdownState);
	
		// prevButton.addEventListener('click', function () {
		//   cal.prev();
		//   update();
		// });
	
		// nextButton.addEventListener('click', function () {
		//   cal.next();
		//   update();
		// });
	
		// todayButton.addEventListener('click', function () {
		//   cal.today();
		//   update();
		// });
	
		// dropdownContent.addEventListener('click', function (e) {
		//   var targetViewName;
	
		//   if ('viewName' in e.target.dataset) {
		// 	targetViewName = e.target.dataset.viewName;
		// 	cal.changeView(targetViewName);
		// 	checkboxCollapse.disabled = targetViewName === 'month';
		// 	toggleDropdownState();
		// 	update();
		//   }
		// });
	
		// checkboxCollapse.addEventListener('change', function (e) {
		//   if ('checked' in e.target) {
		// 	cal.setOptions({
		// 	  week: {
		// 		collapseDuplicateEvents: !!e.target.checked,
		// 	  },
		// 	  useDetailPopup: !e.target.checked,
		// 	});
		//   }
		// });
	
		// sidebar.addEventListener('click', function (e) {
		//   if ('value' in e.target) {
		// 	if (e.target.value === 'all') {
		// 	  if (appState.activeCalendarIds.length > 0) {
		// 		cal.setCalendarVisibility(appState.activeCalendarIds, false);
		// 		appState.activeCalendarIds = [];
		// 		setAllCheckboxes(false);
		// 	  } else {
		// 		appState.activeCalendarIds = MOCK_CALENDARS.map(function (calendar) {
		// 		  return calendar.id;
		// 		});
		// 		cal.setCalendarVisibility(appState.activeCalendarIds, true);
		// 		setAllCheckboxes(true);
		// 	  }
		// 	} else if (appState.activeCalendarIds.indexOf(e.target.value) > -1) {
		// 	  appState.activeCalendarIds.splice(appState.activeCalendarIds.indexOf(e.target.value), 1);
		// 	  cal.setCalendarVisibility(e.target.value, false);
		// 	  setCheckboxBackgroundColor(e.target);
		// 	} else {
		// 	  appState.activeCalendarIds.push(e.target.value);
		// 	  cal.setCalendarVisibility(e.target.value, true);
		// 	  setCheckboxBackgroundColor(e.target);
		// 	}
		//   }
		// });
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


	
	// const rangepicker = DatePicker.createRangePicker({
	// 	     startpicker: {
	// 	         input: '#start-input',
	// 	         container: '#start-container'
	// 	     },
	// 	     endpicker: {
	// 	         input: '#end-input',
	// 	         container: '#end-container'
	// 	     },
	// 	     type: 'date',
	// 	     format: 'yyyy-MM-dd',
	// 	     selectableRanges: [
	// 	         [new Date(2017, 3, 1), new Date(2017, 5, 1)],
	// 	         [new Date(2017, 6, 3), new Date(2017, 10, 5)]
	// 	     ]
	// 	 });

	// calendar.on('clickDayname', function(event) {
	// 	let date = event.date;
	// 	console.log(date);
	// });
	// calendar.on('beforeCreateSchedule', function(event) {
	// 	const { start } = event;
	// 	console.log('Clicked date: ', start);
	// });

	// function addDateClickEvent() {
	// 	const dateElements = document.querySelectorAll('.toastui-calendar-daygrid-cell');
	// 	dateElements.forEach(function(dateElement) {
	// 		dateElement.addEventListener('click', function() {
	// 			const date = dateElement.getAttribute('data-date');
	// 			console.log(date);
	// 		});
	// 	});
	// }
	
	// calendar.on('afterRender', addDateClickEvent);
	// calendar.on('');
	// .toastui-calendar-grid-selection

	// calendar.on('beforeCreateSchedule', function(e) {
	// 	console.log('Date clicked:', e.start.toDate());
	// });
	
	console.log(calendar);
	
	// Init
	bindInstanceEvents();
	bindAppEvents();
	update();
	
})(tui.Calendar);



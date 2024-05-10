/**
 * 
 */

(function(Calendar) {
	// const Calendar = tui.Calendar;
	
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
	  console.log('displayRenderRange() 실행');
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
		console.log('update() 실행');
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
  });
	prevBtn.addEventListener("click", () => {
    calendar.prev();
    update();
  });
	nextBtn.addEventListener("click", () => {
    calendar.next();
    update();
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
	});
	
	console.log(calendar);
	console.log(typeof calendar.renderRange);
	console.log(calendar.renderRange.start.d);
	console.log(calendar.renderRange.end.d);
	
	// Init
	update();
	
})(tui.Calendar);



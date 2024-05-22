(function (Calendar) {
    const container = document.getElementById('calendar');
    const options = {
        gridSelection: {
            enableDbClick: false,
            enableClick: false,
        },
        useFormPopup: false,
        useDetailPopup: false,
        usageStatistics: false,
        defaultView: 'week',
		eventFilter: function (event) {
	      let currentView = cal.getViewName();
	      if (currentView === 'month') {
	        return ['allday', 'time'].includes(event.category) && event.isVisible;
	      }
	      return event.isVisible;
	    },
        timezone: {
            zones: [
                {
                    timezoneName: 'Asia/Seoul',
                    displayLabel: 'Seoul',
                    timeHeight: 60,
                },
            ],
        },
        calendars: COUNSEL_CALENDARS,
        week: {
            workweek: true, // 이거 주말에 하긴 힘드니까 일단은 주말포함 (다하면 true로 변경)
			narrowWeekend: false, 
            eventView: true,
            taskView: false,
            hourStart: 9,
            hourEnd: 18,
            dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        },
        template: { // 이거는 팝업때 쓰는것같다.
            titlePlaceholder: () => {'제목'},
            popupDetailTitle({ title }) {
                return title;
            },
            popupStateFree: function() {
                return '예약 가능';
            },
            popupStateBusy: function() {
                return '예약 불가';
            },
            allday: (event) => {getEventTemplate(event, true)},
            // time: (event) => {getEventTemplate(event, false)}
        },
    };

    const cal = new Calendar(container, options);
    let cno = 1;

    // Constants
    const CALENDAR_CSS_PREFIX = 'toastui-calendar-';
    const cls = function (className) {
        return CALENDAR_CSS_PREFIX + className;
    };

    // Elements
    const navbarRange = document.querySelector('.calendar-render-range');
    const todayBtn = document.querySelector('#todayBtn');
	const prevBtn = document.querySelector('#prevBtn');
	const nextBtn = document.querySelector('#nextBtn');
    const dropdown = document.querySelector('.dropdown');
    const dropdownTrigger = document.querySelector('.dropdown-trigger');
    const dropdownTriggerIcon = document.querySelector('.dropdown-icon');
    const dropdownContent = document.querySelector('.dropdown-content');
	const modalBtn = document.querySelector("#modalBtn");
	
	// 신청용 정보
	let schNo = document.querySelector("#schNo"); // 스케줄번호
	let stNo = document.querySelector("#stNo");
	// 모달 내용
	let csName = document.querySelector("#csName");
	let csLoc = document.querySelector("#csLoc");
	let csDate = document.querySelector("#csDate");
	let csTime = document.querySelector("#csTime");
	
    // const checkboxCollapse = document.querySelector('.checkbox-collapse');
    // const sidebar = document.querySelector('.sidebar');

    // App State
    let appState = {
            activeCalendarIds: [COUNSEL_CALENDARS[0].id],
    //     activeCalendarIds: COUNSEL_CALENDARS.map(function (calendar) {
    //     return calendar.id;
    // }),
        isDropdownActive: false,
    };

    // functions to handle calendar behaviors
    function displayRenderRange() {
        let rangeStart = cal.getDateRangeStart();
        let rangeEnd = cal.getDateRangeEnd();
        navbarRange.textContent = getNavbarRange(rangeStart, rangeEnd, cal.getViewName());
    }

    function setDropdownTriggerText() {
		function isThisField(e) {
			if(e.id == appState.activeCalendarIds[0]) {
				return true;
			}
		}
        let csField = COUNSEL_CALENDARS.find(isThisField);
        let buttonText = document.querySelector('.dropdown .button-text');
        buttonText.textContent = csField.name;
    }
    
    function toggleDropdownState() {
        appState.isDropdownActive = !appState.isDropdownActive;
        dropdown.classList.toggle('is-active', appState.isDropdownActive);
        dropdownTriggerIcon.classList.toggle(cls('open'), appState.isDropdownActive);
    }

    function reloadEvents(cnsno) {
        cal.clear();
        getDBEvents(cal.getViewName(), cnsno).then(data => {
            cal.createEvents(data);
        });
    }

    function update(cnsno) {
        setDropdownTriggerText();
        displayRenderRange();
        reloadEvents(cnsno);
    }

    function bindAppEvents(cnsno) {
        dropdownTrigger.addEventListener('click', toggleDropdownState);
    
        todayBtn.addEventListener('click', () => {
            cal.today();
            update(cnsno);
        })
    
        prevBtn.addEventListener('click', () => {
            cal.prev();
            update(cnsno);
        })
        nextBtn.addEventListener('click', () => {
            cal.next();
            update(cnsno);
        })
    
        dropdownContent.addEventListener('click', function (e) {
		  let cno = '1';
		  let calIdArr = COUNSEL_CALENDARS.map(ele => { ele.id });
		  cal.setCalendarVisibility(calIdArr, false);
          if ('counselNo' in e.target.dataset) {
			// console.log(appState.activeCalendarIds.indexOf(e.target.dataset.counselNo) == -1);
            if(appState.activeCalendarIds.indexOf(e.target.dataset.counselNo) == -1) { // 이게 없으면
				appState.activeCalendarIds = [];
                appState.activeCalendarIds.push(e.target.dataset.counselNo);
                cal.setCalendarVisibility(appState.activeCalendarIds, true);
                cno = e.target.dataset.counselNo;
            }
            toggleDropdownState();
            update(cno);
          }
        });
      }

	function bindInstanceEvents() {
		cal.on({
		  clickEvent: function (eventInfo) {
			console.log('clickEvent', eventInfo);
			// 신청자명, 학번 => 세션
			// 상담사명, 상담실, 날짜, 시간단위 => 이벤트 정보
			
			schNo.value = eventInfo.event.id;
			csName.value = eventInfo.event.title;
			csLoc.value = eventInfo.event.location;
			csDate.value = moment(eventInfo.event.start.toDate()).format("YYYY-MM-DD");
			csTime.value = eventInfo.event.body;
			
			modalBtn.click();
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
			let event, changes;
	
			console.log('beforeUpdateEvent', eventInfo);
	
			event = eventInfo.event;
			changes = eventInfo.changes;

			console.log(changes);
	
			calendar.updateEvent(event.id, event.calendarId, changes);
		  },
		  beforeDeleteEvent: function (eventInfo) {
			console.log('beforeDeleteEvent', eventInfo);
	
			calendar.deleteEvent(eventInfo.id, eventInfo.calendarId);
		  },
		});
	  }
    
	/*modalBtn.addEventListener("click", () => {
		console.log("모달 버튼 클릭");
	})*/
    

    // Init
    COUNSEL_CALENDARS.forEach((e) => {
        cal.setCalendarVisibility(e.id, false);
    })
    cal.setCalendarVisibility(COUNSEL_CALENDARS[0].id, true);
    bindAppEvents(cno);
    bindInstanceEvents();
    update(cno);

})(tui.Calendar);
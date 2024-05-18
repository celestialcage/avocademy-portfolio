(function (Calendar) {
    const container = document.getElementById('calendar');
    const options = {
        gridSelection: {
            enableDbClick: false,
            enableClick: false,
        },
        useFormPopup: false,
        useDetailPopup: true,
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
                name: '심리',
                backgroundColor: '#03bd9e'
            },
            {
                id: 'cal2',
                name: '취업',
                backgroundColor: '#00a9ff'
            },
        ],
        week: {
            workweek: false, // 이거 주말에 하긴 힘드니까 일단은 주말포함 (다하면 true로 변경)
            eventView: true,
            taskView: false,
            hourStart: 9,
            hourEnd: 18,
            dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        },
        // template: { // 이거는 팝업때 쓰는것같다.
        //     titlePlaceholder: () => {'제목'},
        //     allday: (event) => {getEventTemplate(event, true)},
        //     time: (event) => {getEventTemplate(event, false)}
        // },
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
    // const checkboxCollapse = document.querySelector('.checkbox-collapse');
    // const sidebar = document.querySelector('.sidebar');

    // App State
    let appState = {
        activeCalendarIds: COUNSEL_CALENDARS.map(function (calendar) {
        return calendar.id;
    }),
        isDropdownActive: false,
    };

    // functions to handle calendar behaviors
    function displayRenderRange() {
        let rangeStart = cal.getDateRangeStart();
        let rangeEnd = cal.getDateRangeEnd();
        navbarRange.textContent = getNavbarRange(rangeStart, rangeEnd, cal.getViewName());
    }

    function setDropdownTriggerText() {
        let csField = COUNSEL_CALENDARS[0].name;
        let buttonText = document.querySelector('.dropdown .button-text');
        buttonText.textContent = csField;
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
          let targetCounselField;
          console.log(e.target.dataset.counselField);
    
          if ('counselField' in e.target.dataset) {
            targetCounselField = e.target.dataset.counselField;
            // cal.changeView(targetViewName);
			appState.activeCalendarIds = COUNSEL_CALENDARS.map(function (calendar) {
              return calendar.id;
            });
            cal.setCalendarVisibility(appState.activeCalendarIds, true);
            // setAllCheckboxes(true);
            // checkboxCollapse.disabled = targetViewName === 'month';
            toggleDropdownState();
            update(cnsno);
          }
        });
    
        // checkboxCollapse.addEventListener('change', function (e) {
        //   if ('checked' in e.target) {
        //     cal.setOptions({
        //       week: {
        //         collapseDuplicateEvents: !!e.target.checked,
        //       },
        //       useDetailPopup: !e.target.checked,
        //     });
        //   }
        // });
    
        // sidebar.addEventListener('click', function (e) {
        //   if ('value' in e.target) {
        //     if (e.target.value === 'all') {
        //       if (appState.activeCalendarIds.length > 0) {
        //         cal.setCalendarVisibility(appState.activeCalendarIds, false);
        //         appState.activeCalendarIds = [];
        //         setAllCheckboxes(false);
        //       } else {
        //         appState.activeCalendarIds = MOCK_CALENDARS.map(function (calendar) {
        //           return calendar.id;
        //         });
        //         cal.setCalendarVisibility(appState.activeCalendarIds, true);
        //         setAllCheckboxes(true);
        //       }
        //     } else if (appState.activeCalendarIds.indexOf(e.target.value) > -1) {
        //       appState.activeCalendarIds.splice(appState.activeCalendarIds.indexOf(e.target.value), 1);
        //       cal.setCalendarVisibility(e.target.value, false);
        //       setCheckboxBackgroundColor(e.target);
        //     } else {
        //       appState.activeCalendarIds.push(e.target.value);
        //       cal.setCalendarVisibility(e.target.value, true);
        //       setCheckboxBackgroundColor(e.target);
        //     }
        //   }
        // });
      }
    
    

    // Init
    bindAppEvents(cno);
    // bindInstanceEvents();
    update(cno);

})(tui.Calendar);
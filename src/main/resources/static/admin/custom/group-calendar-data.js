let COUNSEL_CALENDARS = [
    {
      id: 'P210', // 1 블베빔
      name: '대인관계',
      color: '#ffffff',
      borderColor: '#9e5fff',
      backgroundColor: '#9e5fff',
      dragBackgroundColor: '#9e5fff',
    },
    {
      id: 'P220', // 7 최행복
      name: '명상',
      color: '#ffffff',
      borderColor: '#00a9ff',
      backgroundColor: '#00a9ff',
      dragBackgroundColor: '#00a9ff',
    },
    {
      id: 'P230', // 2 크랜박
      name: '진로탐색',
      color: '#ffffff',
      borderColor: '#DB473F',
      backgroundColor: '#DB473F',
      dragBackgroundColor: '#DB473F',
    },
  ];

function getDBEvent(calendar, dbEle) {
    let id, calendarId, title, body, location, state, isReadOnly;
    id = `${dbEle.grsch_no}`;
    calendarId = `${dbEle.prg_cd}`;
    title = `${dbEle.prg_nm}`;
    body = ``;
    location = `${dbEle.prg_place}`;
    state = dbEle.req_open == 0 ? `신청 마감` : `신청 가능`;
    // isReadOnly = dbEle.scheduleState === 0 ? true : false;
    // let calendarId, start, end;
    let attendees = [];
	attendees.push(`${dbEle.cns_nm}`);
    let raw = {
        //memo: `${dbEle.scheduleNo}`,
    };
    // var raw = {
    //     memo: chance.sentence(),
    //     creator: {
    //       name: chance.name(),
    //       avatar: chance.avatar(),
    //       email: chance.email(),
    //       phone: chance.phone(),
    //     },
    //   };

    let event = {
        id: id, // 스케줄번호
        calendarId: calendarId, // 캘린더 id...
        //start: start, // 시작날짜나 끝날짜나.. 하루씩 할거라
        //end: end, // 같을거같다. 근데 시각만 다름.
        title: title, // 일정 제목 -> db Free일 때 (상담사 이름) 상담 가능
        body: body, // 일정 내용 -> db Busy일 때 (학생 이름) 상담 예정
        location: location, // 장소 -> db 상담사 정보
        state: state, // 일정 상태 -> db 예약 차면 Busy, 안 차면 Free
        attendees: attendees, // 참석자 -> db 상담사, db 학생
        raw: raw, // 일정 상세.. 메모.. 일정 작성자
        isVisible: true, // 관리자에서는 다 보여주기
        isReadOnly: true, // 수정 여부. Free일 경우에만? (어차피 새로 로드하는거같은데...)
    }	
	
    function getDBTime(event, dbEle) {
        let startDate = moment(dbEle.prg_ymd); // db에서 가져온 날짜 (moment로)
        let endDate = moment(dbEle.prg_ymd); // db에서 가져온 날짜.. (moment로)
        // let diffDate = endDate.diff(startDate, 'days'); // 안필요할듯
    
        startDate.hours(dbEle.prg_hr);
		//startDate.format('YYYY-MM-DD HH:mm')
		console.log(startDate);

        endDate = moment(startDate); // 같은 날이니까.
		event.start = startDate.toDate();
        event.end = endDate.add(50, 'm').toDate();
    }

    getDBTime(event, dbEle);

    return event;
}

async function getDBEvents(viewName) {
    // let i;
    let event;
	let events = [];
    
    // db 통신 ajax
    let dbEvents = await getData('./grSchedule').then(data => {
        data.forEach(e => {
            e.prg_ymd = moment(e.prg_ymd, "YYYYMMDD").format('YYYY-MM-DD');
        });
        
        return data;
    })
	.then(dbList => {
		dbList.forEach(dbEle => {
			//console.log(dbEle);
	        event = getDBEvent(calendar, dbEle);
	        events.push(event);
	    });
		return events;
	})
    .catch(error => console.log(error));

    return dbEvents;
}

function changeDBSchedule(change) {

}
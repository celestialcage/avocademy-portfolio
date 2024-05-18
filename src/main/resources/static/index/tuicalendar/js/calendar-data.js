let COUNSEL_CALENDARS = [
    {
      id: '1', // 1 블베빔
      name: '심리 - 블베빔',
      color: '#ffffff',
      borderColor: '#9e5fff',
      backgroundColor: '#9e5fff',
      dragBackgroundColor: '#9e5fff',
    },
    {
      id: '7', // 7 최행복
      name: '취업 - 최행복',
      color: '#ffffff',
      borderColor: '#00a9ff',
      backgroundColor: '#00a9ff',
      dragBackgroundColor: '#00a9ff',
    },
    {
      id: '2', // 2 크랜박
      name: '취업 - 크랜박',
      color: '#ffffff',
      borderColor: '#DB473F',
      backgroundColor: '#DB473F',
      dragBackgroundColor: '#DB473F',
    },
    {
      id: '3', // 3 라이미
      name: '심리 - 라이미',
      color: '#ffffff',
      borderColor: '#03bd9e',
      backgroundColor: '#03bd9e',
      dragBackgroundColor: '#03bd9e',
    },
    {
      id: '8', // 8 박지혜
      name: '심리 - 박지혜',
      color: '#ffffff',
      borderColor: '#bbdc00',
      backgroundColor: '#bbdc00',
      dragBackgroundColor: '#bbdc00',
    },
  ];

function getDBEvent(calendar, dbEle) {
    let id, title, body, location, state;
    id = dbEle.scheduleNo;
    title = `상담사 ${dbEle.counselorNo} 신청 가능`;
    body = ``;
    location = `상담사 ${dbEle.counselorNo} 사무실 N호`;
    state = `Free`;
    // let calendarId, start, end;
    let attendees = [];
    let raw = {};
    // var raw = {
    //     memo: chance.sentence(),
    //     creator: {
    //       name: chance.name(),
    //       avatar: chance.avatar(),
    //       email: chance.email(),
    //       phone: chance.phone(),
    //     },
    //   };

    function getDBTime(event, dbEle) {
        let startDate = moment(dbEle.scheduleDate); // db에서 가져온 날짜 (moment로)
        let endDate = moment(dbEle.scheduleDate); // db에서 가져온 날짜.. (moment로)
        // let diffDate = endDate.diff(startDate, 'days'); // 안필요할듯
    
        startDate.hours(dbEle.scheduleTime);
        // startDate.minutes(/* 여기다 db에서 minute 정보? 안쓰나? */);
        endDate = moment(startDate); // 같은 날이니까.
        event.start = startDate.toDate();
        event.end = endDate.add(50, 'm').toDate();
    }

    let event = {
        id: id, // 스케줄번호
        calendarId: 'cal1', // 캘린더 id...
        // start: start, // 시작날짜나 끝날짜나.. 하루씩 할거라
        // end: end, // 같을거같다. 근데 시각만 다름.
        title: title, // 일정 제목 -> db Free일 때 (상담사 이름) 상담 가능
        body: body, // 일정 내용 -> db Busy일 때 (학생 이름) 상담 예정
        location: location, // 장소 -> db 상담사 정보
        state: state, // 일정 상태 -> db 예약 차면 Busy, 안 차면 Free
        attendees: attendees, // 참석자 -> db 상담사, db 학생
        raw: raw, // 일정 상세.. 메모.. 일정 작성자
        isVisible: true, // 관리자에서는 다 보여주기
        isReadOnly: true, // 수정 여부. Free일 경우에만? (어차피 새로 로드하는거같은데...)
    }

    getDBTime(event, dbEle);

    return event;
}

async function getDBEvents(viewName, cno) {
    // let i;
    let event;
	let events = [];
    
    // db 통신 ajax
    let dbEvents = await getData('/cs-schedule', cno).then(data => {
        data.schedules.forEach(e => {
            e.scheduleDate = moment(e.scheduleDate, "YYYYMMDD").format('YYYY-MM-DD');
        });
        
        return data.schedules;
    })
	.then(dbList => {
		dbList.forEach(dbEle => {
	        event = getDBEvent(calendar, dbEle);
	        events.push(event);
	    });
		return events;
	})
    .catch(error => console.log(error));

    return dbEvents;
}
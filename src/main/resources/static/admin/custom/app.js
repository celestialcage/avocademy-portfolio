

const addEventBtn = document.querySelector('#addEventBtn');

function addEBHandler() {
    alert("add event");
    console.log('clicked');
}

addEventBtn.addEventListener("click", addEBHandler);

function getDBEvent(calendar, renderStart, renderEnd) {
    
    let startDate; // db에서 가져온 날짜 (moment로)
    let endDate; // db에서 가져온 날짜.. (moment로)
    // let diffDate = endDate.diff(startDate, 'days'); // 안필요할듯

    startDate.hours(/* 여기다 db에서 시간 정보. moment() */);
    // startDate.minutes(/* 여기다 db에서 minute 정보? 안쓰나? */);
    endDate = moment(startDate); // 같은 날이니까.
    event.start = startDate.toDate();
    event.end = endDate.add(/* db에서 숫자 가져와서 계산? */ 1, 'hour').toDate();
    

    

    let id, title, body, location, state;
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

    return event;
}

function getDBEvents(viewName, renderStart, renderEnd) {
    // 표시되는 date... renderStart, renderEnd 사이에 있는 event들만..?
    let dbList = [];
    // db 통신 ajax
    let dbData = getData('/cs-schedule');
    dbData.then(data => {
        data.schedules.forEach(e => {
            e.scheduleDate = moment(e.scheduleDate, "YYYYMMDD").format('YYYY-MM-DD');
        });
        dbList = data.schedules;
        console.log(dbList);
    })
    .then(data => console.log(data));

    let i;
    let event;
    let events = [];

    // for (i=0; i < )
    event = getDBEvent(calendar, renderStart, renderEnd);
    events.push(event);

}


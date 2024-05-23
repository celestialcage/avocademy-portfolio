const Grid = tui.Grid;

Grid.setLanguage('ko');

let params = {}; // 세션 cns_no만 넣자
const initData = postData('/cs-schedule-list', params).then(data => data);

const instance = new Grid({
    el: document.getElementById('grid'),
    columns: [
        {
            header: '신청번호',
            name: 'aply_no'
        },
        {
            header: '신청자(학번)',
            name: 'stud_no'
        },
        {
            header: '상담분야',
            name: 'cns_field'
        },
        {
            header: '상담사',
            name: 'cns_no'
        },
        {
            header: '상담일자',
            name: 'dscsn_ymd'
        },
        {
            header: '상담시간',
            name: 'dscsn_hr'
        },
        {
            header: '신청상태',
            name: 'dscsn_stat'
        },
        {
            header: '승인여부',
            name: 'aply_confirm'
        },
    ],
    data: initData,
});

Grid.applyTheme('striped'); // Call API of static method
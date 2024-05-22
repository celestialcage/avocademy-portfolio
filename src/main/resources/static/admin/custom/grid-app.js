const Grid = tui.Grid;

Grid.setLanguage('ko');

{
	let params = {};
	const initData = postData('/', params).then(data => console.log(data));
}

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
    data: [
        {
			aply_no: 1,
        }
    ]
});

Grid.applyTheme('striped'); // Call API of static method
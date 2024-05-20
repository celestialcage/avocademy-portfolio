const Grid = tui.Grid;

Grid.setLanguage('ko');

const instance = new Grid({
    el: document.getElementById('grid'),
    columns: [
        {
            header: '시간',
            name: 'time'
        },
        {
            header: '월',
            name: 'mon'
        },
        {
            header: '화',
            name: 'tue'
        },
        {
            header: '수',
            name: 'wed'
        },
        {
            header: '목',
            name: 'thr'
        },
        {
            header: '금',
            name: 'fri'
        },
    ],
    data: [
        {
            time: `시간`,
            mon: `<input type="checkbox">`,
            tue: `<input type="checkbox">`,
            wed: `<input type="checkbox">`,
            thr: `<input type="checkbox">`,
            fri: `<input type="checkbox">`
        }
    ]
});

Grid.applyTheme('striped'); // Call API of static method
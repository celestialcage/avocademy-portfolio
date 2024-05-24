/*const csNmInput = document.getElementById("csName");
const csLocInput = document.getElementById("csLoc");
let sessionCno;
const addSchBtn = document.querySelector('#addSchBtn');

console.log(csNmInput);
console.log(csLocInput);

let csInfoUrl = '/cs-info';
let csInfoParams = makeUserNoObj(document.querySelector("#sessionInfo").value);

// 폼 데이터 넣기 (상담사 로그인)
postData(csInfoUrl, csInfoParams).then(data => {
	sessionCno = data.cns_no;
	csNmInput.value = data.cns_nm;
	csLocInput.value = data.ofc_no;
})


function addSchedule() {
    let selectedTimes = document.querySelectorAll('input[name="cslTimes"]:checked');
	if (selectedTimes.length == 0) {
		alert('가능한 시간을 선택하세요.');
		return false;
	}
	let url = '/add-cschedule'
	let cno = sessionCno;
	let cslDate = document.querySelector('#datepicker-autoclose').value;
	if (!cslDate) {
		console.log(cslDate);
		alert('가능한 날짜를 선택하세요.');
		return false;
	}
	let schDate = moment(cslDate).format('YYYYMMDD');
	
	// 배열로 보내서 서버에서 처리하는게 좋을까... 통신 횟수가 너무 늘어나는 느낌.
	selectedTimes.forEach(e => {
		let params = {
			cns_no: cno,
			sch_ymd: schDate,
		}
		params.sch_hr = e.value;
		updateData(url, params).then(data => {
			console.log(data);
		})
	})
	/*let params = {
		cns_no: ,
		sch_ymd: ,
		sch_hr: ,
	};*/
//}

/* addSchBtn.addEventListener("click", addSchedule);
*/



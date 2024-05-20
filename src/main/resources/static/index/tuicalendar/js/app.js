// console.log("app.js mounted");

const applyBtn = document.getElementById("applyBtn");

function applySchedule() {
	// params 만들어서 신청 caply 레코드 생성하기
	// 신청 입력 정보
	// 학번, 일자, 시간, 상태(처음 만들때는 CS001 고정), 이전신청번호(상담사가), 스케줄번호 sch_no
	let params = {
		sch_no: schNo.value,
		stud_no: stNo.value,
		// 얘네는 sch_no만 가지고 서브쿼리로 입력할지 고민...
		// dscsn_ymd: moment(eventInfo.event.start.toDate()).format("YYYYMMDD"), 
		// dscsn_hr: moment(eventInfo.event.start.toDate()).hour()
	};
	
	postData('/apply-schedule', params).then(data => {
		console.log(data);
		if(data.result == 0) {
			alert(data.message);
		} else {
			alert(data.message);			
			location.reload(true);
		}
		
	}).catch(error => {
		console.log(error);
	})
	
	
	
}

applyBtn.addEventListener("click", applySchedule);
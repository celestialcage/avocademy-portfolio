// const confirmBtn = document.querySelector(".confirmBtn");

$(".confirmBtn").click(function() {
	let row = $(this).parent().parent();
	console.log(row);
});

/*confirmBtn.addEventListener("click", (e) => {
	let row = e.target.parentNode.parentNode;
    console.log(row);
    console.log(row.querySelector(".aply_no"));
})*/


    function detail(no) {
    	let detailEle = document.querySelector('#detail');
    	let detailModal = new bootstrap.Modal(detailEle, {}); // {옵션}
    	fetch('./appointment-detail?aply_no=' + no, {
    		method: "GET", 
    		headers: {"Content-Type": "application/json",}, 
    	}).then(response => {
    		if(!response.ok) {
    			throw new Error(response.statusText);
    		} else {
    			return response.json();
    		}
    	}).then(data => {
    		console.log(data.dscsn_cn);
    		document.querySelector('#modalContent').innerHTML = data.dscsn_cn;
    		document.querySelector('#updateLinkBtn').setAttribute("href", './update-comment@' + data.aply_no);
    		document.querySelector('#applySeriesLinkBtn').setAttribute("href", './appointment-series?aply_no=' + data.aply_no);
    		detailModal.show();
    	}).catch(error => {
    		console.log(error);
    	})
    }
    
    function statusApply(no) {
    	fetch('/apply-status', {
    		method: 'POST',
    		body: JSON.stringify({
    			aply_no: no,
    		}), 
    		headers: {"Content-Type": "application/json",},
    	}).then(res => {
    		if(!res.ok) {
    			throw new Error(res.statusText);
    		} else {
    			return res.json();
    		}
    	}).then(data => {
    		console.log(data.result);
    		location.reload(true);
    	}).catch(err => {
    		console.log(err);
    	})
    }
    
    function confirmApply(no) {
    	fetch('/appointment-confirm', {
    		method: 'POST',
    		body: JSON.stringify({
    			aply_no: no,
    		}), 
    		headers: {"Content-Type": "application/json",},
    	}).then(res => {
    		if(!res.ok) {
    			throw new Error(res.statusText);
    		} else {
    			return res.json();
    		}
    	}).then(data => {
    		console.log(data.result);
    		location.reload(true);
    	}).catch(err => {
    		console.log(err);
    	})
    }

    function completeAppointment(no) {
    	fetch('/appointment-complete', {
    		method: 'POST',
    		body: JSON.stringify({
    			aply_no: no,
    		}), 
    		headers: {"Content-Type": "application/json",},
    	}).then(res => {
    		if(!res.ok) {
    			throw new Error(res.statusText);
    		} else {
    			return res.json();
    		}
    	}).then(data => {
    		console.log(data.result);
    		location.reload(true);
    	}).catch(err => {
    		console.log(err);
    	})
    }
    
    function skipAppointment(no) {
    	fetch('/appointment-skip', {
    		method: 'POST',
    		body: JSON.stringify({
    			aply_no: no,
    		}), 
    		headers: {"Content-Type": "application/json",},
    	}).then(res => {
    		if(!res.ok) {
    			throw new Error(res.statusText);
    		} else {
    			return res.json();
    		}
    	}).then(data => {
    		console.log(data.result);
    		location.reload(true);
    	}).catch(err => {
    		console.log(err);
    	})
    }
    
    function cancelReservation(no) {
    	fetch('/appointment-cancel', {
    		method: 'POST',
    		body: JSON.stringify({
    			aply_no: no,
    		}), 
    		headers: {"Content-Type": "application/json",},
    	}).then(res => {
    		if(!res.ok) {
    			throw new Error(response.statusText);
    		} else {
    			return res.json();
    		}
    	}).then(data => {
    		console.log(data.result);
    		location.reload(true);
    	}).catch(err => {
    		console.log(err);
    	})
    }
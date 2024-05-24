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
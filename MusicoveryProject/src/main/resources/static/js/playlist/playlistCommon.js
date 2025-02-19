

//생성하기 버튼 제어
$("#insertFormBtn").click(function(){
	locationProcess("/playlist/insertForm");
});


/* 취소 버튼 제어 */
$("#playlistCancelBtn").on("click", function(){
	let form = $(this).parents("form").attr("id");
	console.log(form);
	$("#"+form+"").each(function(){
		this.reset();
	});
});

/* 목록 버튼 제어 */
$("#playlistListBtn").click(function(){
	locationProcess("/playlist/playlistList");
});
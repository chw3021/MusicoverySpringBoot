$("#playlistInsertBtn").on("click", function(){
	if(!chkData("#title", "제목을")) return;
	else if(!chkData("#comment", "설명을")) return;
	else if(!chkData("#userId", "이메일을")) return;
	else{
		actionProcess("#insertForm","post","/playlist/insertForm");
	}
})
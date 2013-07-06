function openServerInfoDialog() {

	$.mobile.changePage('serverinfo.html', {
		transition : 'pop',
		role : 'dialog'
	});
}

function submitLogin() {
	var params = $('#loginForm').serialize();
	$.post($("#loginForm").attr('action'), params);
}
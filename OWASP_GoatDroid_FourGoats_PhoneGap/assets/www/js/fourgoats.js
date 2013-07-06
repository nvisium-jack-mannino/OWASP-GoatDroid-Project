function openServerInfoDialog() {

	$.mobile.changePage('serverinfo.html', {
		transition : 'pop',
		role : 'dialog'
	});
}

var submitLogin = function()
{
	var params = $('#loginForm').serialize();
	var destinationInfo = $.parseJSON(SharedPrefs.getDestinationInfo());
	$.post("http://" + destinationInfo["serverIp"] + ":"
			+ destinationInfo["serverPort"]
			+ "/fourgoats/api/v1/login/authenticate", params);
}
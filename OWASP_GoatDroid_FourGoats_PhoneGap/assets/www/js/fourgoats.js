function updateFormAction(formId) {
	var action = $("#" + formId).attr("action");
	$("#" + formId).attr("action", SharedPrefs.getDestinationInfo() + action);
}

function openServerInfoDialog() {

	$.mobile.changePage('serverinfo.html', {
		transition : 'pop',
		role : 'dialog'
	});
}

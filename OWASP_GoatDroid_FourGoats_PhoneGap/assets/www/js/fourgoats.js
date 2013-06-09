function updateFormAction(formId) {
	var action = $("#" + formId).attr("action");
	$("#" + formId).attr("action", "IP"+ action);
}

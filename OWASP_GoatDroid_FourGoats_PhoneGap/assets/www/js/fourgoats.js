function updateFormAction(formId) {
	var action = $("#" + formId).attr("action");
	$("#" + formId).attr("action", getDestinationInfo() + action);
}

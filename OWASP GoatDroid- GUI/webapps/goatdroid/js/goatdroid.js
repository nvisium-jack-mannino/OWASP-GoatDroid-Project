var DEFAULT_APP = "fourgoats-native";

function initializeContext() {
	if (getCookie() == 'undefined') {
		loadSidebar(DEFAULT_APP);
		loadLesson(DEFAULT_APP);
	} else {
		loadSidebar(getCookie());
		loadLesson(getCookie());
	}
}

function loadNewContent(app) {
	loadLesson(app);
	loadSidebar(app);
	setCookie(app);
}

function loadLesson(app) {
	var url = "lessons/" + app + "/description.html";
	$('#main-container').load(url);
}

function loadSidebar(app) {
	var sidebar = "lessons/" + app + "/sidebar.html";
	$("#sidebar").load(sidebar);
}

function setCookie(app) {
	$.cookie('currentApp', app);
}

function getCookie() {
	return $.cookie('currentApp');
}
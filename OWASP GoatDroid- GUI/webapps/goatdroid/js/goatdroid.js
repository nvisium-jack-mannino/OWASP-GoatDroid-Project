var DEFAULT_APP = "fourgoats-native";

function initializeContext(app) {
	if (getCookie() != null) {
		loadNewContent(getCookie());
	} else if (app != null) {
		loadNewContent(app);
	} else {
		loadNewContent(DEFAULT_APP);
	}
}

function loadNewContent(app) {
	/*
	 * If we aren't at index.jsp, we redirect to it and pass in the app as a
	 * parameter. This method will get called again once you get onto thepage,
	 * but it should hit the "else" where the app's content is properly
	 * initialized
	 * 
	 */
	if ($.url("file") != "index.jsp") {
		window.location.href = "index.jsp?app=" + app;
	} else {
		loadLesson(app);
		loadSidebar(app);
		setCookie(app);
	}
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
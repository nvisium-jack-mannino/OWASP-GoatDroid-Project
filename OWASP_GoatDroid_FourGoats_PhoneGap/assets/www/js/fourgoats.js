function openServerInfoDialog() {

	$.mobile.changePage('serverinfo.html', {
		transition : 'pop',
		role : 'dialog'
	});
}

var submitLogin = function() {
	var params = $('#loginForm').serialize();
	var destinationInfo = $.parseJSON(SharedPrefs.getDestinationInfo());
	$.ajax({
		url : getDestinationInfoString()
				+ "/fourgoats/api/v1/login/authenticate",
		type : 'POST',
		dataType : 'html',
		data : params,
		success : getLoginSuccess,
		error : function(xhr, textStatus, errorThrown) {
			// do stuff here
		}
	});
}

function getLoginSuccess(response) {
	var json = $.parseJSON(response);
	if (isSuccess(json)) {
		/*
		 * First, we have to store your session token, admin status, and
		 * settings
		 * 
		 */
		insertUserInfo(json);
		/*
		 * Next, based on your settings we initialize the location tracking
		 * service
		 * 
		 */
		if (isAutoCheckinEnabled()) {
			/*
			 * start service
			 */
			IntentGenerator
					.startService(
							null,
							null,
							null,
							null,
							"org.owasp.goatdroid.fourgoats.phonegap.services.LocationService",
							null, null);
		}
		/*
		 * Then, we redirect you to the home page and render the correct view
		 * based on your admin status
		 * 
		 */

	} else {
		/*
		 * We display the appropriate error message (your creds either worked or
		 * didn't work, basically
		 * 
		 */
	}
}

/*
 * You pass a JSON array into this. It tells you if success was true or not.
 * 
 */
function isSuccess(json) {
	if (json["success"] == "true")
		return true;
	else
		return false;
}

function validateLoginForm() {
	$('#loginForm').validate({
		highlight : function(element, errorClass) {
			$(element).addClass(errorClass)
		},
		unhighlight : function(element, errorClass) {
			$(element).removeClass(errorClass)
		},
		rules : {
			username : {
				required : true,
				minlength : 1,
				maxlength : 100
			},
			password : {
				required : true,
				minlength : 1,
				maxlength : 100
			},
		},
		errorClass : "validationError",
		errorElement : "div",
		errorPlacement : function(error, element) {
			$(element).before(error);
		},
	});
	$('#login-submit-button').click(function(e) {
		if ($('#loginForm').valid()) {
			submitLogin();
		}
	})
}

function createDatabases() {
	window.db = window.openDatabase("fourgoats", "1.0", "FourGoats Database",
			2000000);
	window.db.transaction(createTables, createDatabasesSuccess,
			createDatabasesError);
}

function createTables(db) {
	db
			.executeSql('CREATE TABLE IF NOT EXISTS info (id INTEGER PRIMARY KEY AUTOINCREMENT, sessionToken, userName, isPublic,'
					+ 'autoCheckin, isAdmin)');
	db
			.executeSql('CREATE TABLE IF NOT EXISTS checkins (id INTEGER PRIMARY KEY AUTOINCREMENT, checkinID, venueName, '
					+ 'dateTime, latitude, longitude)');
	db
			.executeSql('CREATE TABLE IF NOT EXISTS autocheckin (id INTEGER PRIMARY KEY AUTOINCREMENT, dateTime, latitude, longitude)');

}

function createDatabasesSuccess(tx, err) {
	// console.write("db success");
}

function createDatabasesError(tx, err) {
	// console.write("db error");
}

/*
 * Pass parsed JSON into this
 * 
 * 
 */
function insertUserInfo(json) {

	/*
	 * First, we purge old settings from the DB
	 * 
	 */
	window.db.transaction(function(db) {
		db.executeSql('DELETE FROM info');
	})
	var sql = 'INSERT INTO info (sessionToken, username,isPublic, autoCheckin, isAdmin) VALUES (?,?,?,?,?)';
	var values = [ json["sessionToken"], json["userName"] ];
	var isPublic;
	var autoCheckin;
	var isAdmin;
	$.each(json.preferences, function(index, val) {
		$.each(val, function(index, val2) {
			if (val2["key"] == "isPublic")
				isPublic = val2["value"];
			else if (val2["key"] == "autoCheckin")
				autoCheckin = val2["value"];
			else if (val2["key"] == "isAdmin")
				isAdmin = val2["value"];
		});
	});
	values.push(isPublic);
	values.push(autoCheckin);
	values.push(isAdmin);
	window.db.transaction(function(db) {
		db.executeSql(sql, values);
	}, null, null);
}

function isAutoCheckinEnabled() {
	var isEnabled;
	window.db.transaction(function(db) {
		db.executeSql("SELECT autoCheckin FROM info", [],
				function(tx, results) {
					if (results.rows.item(0).autoCheckin == "true")
						isEnabled = true;
					else
						isEnabled = false;
				}, function() {
					isEnabled = false;
				})
	});
	return isEnabled;
}
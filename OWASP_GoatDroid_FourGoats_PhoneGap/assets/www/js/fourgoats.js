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

		/*
		 * Next, based on your settings we initialize the location tracking
		 * service
		 * 
		 */

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
	$('#login-submit-button').click(function() {
		if ($('#loginForm').valid()) {
			submitLogin();
		}
	})
}
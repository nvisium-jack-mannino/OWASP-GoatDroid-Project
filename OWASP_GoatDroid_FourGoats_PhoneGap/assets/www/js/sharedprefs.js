var SharedPrefs = {

	getDestinationInfo : function() {
		var destination = "";
		cordova.exec(function(result) {
			destination = result
		}, null, 'SharedPrefs', 'getDestinationInfo', []);
		return destination;
	},

	setDestinationInfo : function() {
		var serverIp = $('#serverIp').val();
		var serverPort = $('#serverPort').val();
		cordova.exec(null, null, 'SharedPrefs', 'setDestinationInfo', [
				serverIp, serverPort ]);
		history.back();
	}
};

function isDestinationSet() {
	var destinationInfo = $.parseJSON(SharedPrefs.getDestinationInfo());
	$.each(destinationInfo, function(index, value) {
		if (value === "") {
			// Pop up dialog here
			openServerInfoDialog();
			return false;
		}
	});
}

function validateServerInfo() {
	$('#serverInfoForm').validate({
		highlight : function(element, errorClass) {
			$(element).addClass(errorClass)
		},
		unhighlight : function(element, errorClass) {
			$(element).removeClass(errorClass)
		},
		rules : {
			serverIp : {
				required : true,
				ipv4 : true
			},
			serverPort : {
				required : true,
				digits : true,
				range : [ 1, 65535 ]
			},
		},
		errorClass : "validationError",
		errorElement : "div",
		errorPlacement : function(error, element) {
			$(element).before(error);
		},
	});
	$('#submitButton').click(function() {
		if ($('#serverInfoForm').valid()) {
			SharedPrefs.setDestinationInfo();
		}
	});
}

function populateDestinationInfo() {
	var destinationInfo = $.parseJSON(SharedPrefs.getDestinationInfo());
	if (!(destinationInfo["serverIp"] === ""))
		$('#serverIp').val(destinationInfo["serverIp"]);

	if (destinationInfo["serverPort"] === "")
		$('#serverPort').val(9888)
	else
		$('#serverPort').val(destinationInfo["serverPort"])

}
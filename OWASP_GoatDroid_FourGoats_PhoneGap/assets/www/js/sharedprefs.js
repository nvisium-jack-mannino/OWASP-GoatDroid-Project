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
		console.log(value);
		if (value === "") {
			// Pop up dialog here
			openServerInfoDialog();
			return false;
		}
	});

	function validateServerInfo(event) {
		$.validator.addMethod("portValidator", function(value, elem, args) {
			return val >= 0 && val <= 65535
		})
		$('form').validate({
			rules : {
				serverIp : {
					required : true
				},
				serverPort : {
					required : true,
					min : 1,
					max : 5,
					digits : true,
					portValidator : serverPort.val()
				}
			}
		});
	}
}

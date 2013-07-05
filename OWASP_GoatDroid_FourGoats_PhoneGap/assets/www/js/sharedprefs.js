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
}

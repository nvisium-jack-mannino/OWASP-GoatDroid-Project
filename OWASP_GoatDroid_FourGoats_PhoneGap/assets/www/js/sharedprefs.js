var SharedPrefs = {

	getDestinationInfo : function() {
		var destination = "";
		cordova.exec(function(result) {
			destination = result
		}, null, 'SharedPrefs', 'getDestinationInfo', []);
		return destination;
	}
};

function isDestinationSet() {
	var destinationInfo = $.parseJSON(SharedPrefs.getDestinationInfo());
	$.each(destinationInfo, function(index, value) {
		console.log(value);
		if (value === "") {
			// Pop up dialog here
			console.log("kazaam");
			openServerInfoDialog();
			return false;
		}
	});
}

function updateDestinationInfo() {
	var serverIp = $('#serverIp').value;
	alert(serverIp);
}

var IntentGenerator = {

	startService : function(things, stuff) {
		cordova.exec(null, null, 'IntentGenerator', 'startService', [ things,
				stuff ]);
	},

	startActivity : function() {
		cordova.exec(null, null, 'IntentGenerator', 'startActivity', []);
	},

	sendBroadcast : function() {
		cordova.exec(null, null, 'IntentGenerator', 'sendBroadcast', []);
	}
};
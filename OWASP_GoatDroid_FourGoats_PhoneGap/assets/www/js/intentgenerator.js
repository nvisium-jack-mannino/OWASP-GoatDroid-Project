var IntentBuilder = {

	startService : function() {
		cordova.exec(null, null, 'IntentGenerator', 'startService', []);
	},

	startActivity : function() {
		cordova.exec(null, null, 'IntentGenerator', 'startActivity', []);
	},

	sendBroadcast : function() {
		cordova.exec(null, null, 'IntentGenerator', 'sendBroadcast', []);
	}
};
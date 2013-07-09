var IntentGenerator = {

	startService : function(action, data, category, type, component, extras,
			flags) {
		cordova.exec(null, null, 'IntentGenerator', 'startService', [ action,
				data, category, type, component, extras, flags ]);
	},

	startActivity : function() {
		cordova.exec(null, null, 'IntentGenerator', 'startActivity', []);
	},

	sendBroadcast : function() {
		cordova.exec(null, null, 'IntentGenerator', 'sendBroadcast', []);
	}
};
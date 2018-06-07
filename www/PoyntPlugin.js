



var Poynt = {
	launchPayment: function (amount, referenceId, successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchPayment', 
			[{
				"amount": amount, 
				"referenceId": referenceId
			}]);
	},
	launchAskConf: function (successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchAskConf', 
			[]);
	},
	launchSign: function (successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchSign', 
			[]);
	},
	launchInit: function (msg,successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchInit', 
			[]);
	},
	launchMsg: function (msg,successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchMsg', 
			[{"msg": msg}]);
	},
	launchTest: function (successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchTest', 
			[]);
	}
}

 module.exports = Poynt;

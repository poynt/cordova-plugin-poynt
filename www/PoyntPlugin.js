



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
	launchAskConf: function (msg,successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchAskConf', 
			[{"msg": msg}]);
	},
	launchSign: function (msg,successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchSign', 
			[{"msg": msg}]);
	},
	launchInit: function (successCallback, errorCallback){
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
	launchInfo: function (successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchInfo', 
			[]);
	}
}

 module.exports = Poynt;





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
	launchTest: function (successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchTest', 
			[]);
	}
}

 module.exports = Poynt;

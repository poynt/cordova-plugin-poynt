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
	Init: function (successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchInit', 
			[]);
	},
	
	
	billing: {
		launchBilling: function (successCallback, errorCallback,options){
			cordova.exec(successCallback, 
				errorCallback, 
				'Poynt', 
				'launchBilling', 
				[options]);
		},
		getPlans: function (successCallback,errorCallback,options){
			cordova.exec(successCallback, 
				errorCallback, 
				'Poynt', 
				'launchPlans', 
				[options]);
		}
	},
	business: {
		getBusiness: function (successCallback, errorCallback){
		cordova.exec(successCallback, 
			errorCallback, 
			'Poynt', 
			'launchInfo', 
			[]);
		}
	},
	secondScreen: {
			collectAgreement: function (successCallback, errorCallback,options){
				cordova.exec(successCallback, 
				errorCallback, 
				'Poynt', 
				'launchAskConf', 
				[options]);
			},
			displayMessage: function (successCallback, errorCallback,options){
				cordova.exec(successCallback, 
					errorCallback, 
					'Poynt', 
					'launchMsg', 
					[options]);
			},
			collectSignature: function (successCallback, errorCallback,options){
			cordova.exec(successCallback, 
				errorCallback, 
				'Poynt', 
				'launchSign', 
				[options]);
			}
	}
}

 module.exports = Poynt;
 

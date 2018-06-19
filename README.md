---
title: Poynt
description: Work with Poynt Payment Fragment, SecondScreen Service, Business Service, Billing Service
---

# cordova-plugin-poynt

This plugin defines a global `Poynt` object, which provides an API for working with Poynt Payment Fragment, SecondScreen and Business services.

## Installation
The plugin was written and tested using with cordova v6.3.0

    cordova plugin add cordova-plugin-poynt
    
It is also possible to install via repo url directly

    cordova plugin add https://github.com/poynt/cordova-plugin-poynt.git
    


## How to Contribute

Please feel free to contribute. You can [report bugs](https://github.com/poynt/cordova-plugin-poynt/issues), improve the documentation, or [contribute code](https://github.com/poynt/cordova-plugin-poynt/pulls).

---

# API Reference <a name="reference"></a>
* [Poynt](#module_Poynt)
    * [.launchInit(successCallback, errorCallback)](#Poynt.launchInit)
    * [.launchPayment(amount, referenceId, successCallback, errorCallback)](#Poynt.launchPayment)
    * [.secondScreen.collectAgreement(successCallback, errorCallback,options)](#Poynt.launchAskConf)
    * [.secondScreen.collectSignature(successCallback, errorCallback,options)](#Poynt.launchSign)
    * [.secondScreen.displayMessage(successCallback, errorCallback,options)](#Poynt.launchMsg)
    * [.business.getBusiness(successCallback, errorCallback)](#Poynt.launchInfo)

---

 
## Poynt
<a name="Poynt.launchPayment"></a>
### Poynt.launchPayment(amount, referenceId, successCallback, errorCallback)
Launches Payment Fragment with the payment amount as `amount`.  The transaction response passed to the success callback as a JSON object.

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| amount | long (e.g. `755` would translate to `$7.55` | payment amount |
| referenceId | String | referenceId return in transaction response |
| successCallback |  |  |
| errorCallback |  |  |

**Example Request**  
```js
Poynt.launchPayment(777, 'myRefId', succcessCallback, failureCallback);
```

**Example Response**
```js
{
   "transactions": [
     {
       "action": "SALE",
       "amounts": {
         "cashbackAmount": 0,
         "currency": "USD",
         "customerOptedNoTip": false,
         "orderAmount": 777,
         "tipAmount": 0,
         "transactionAmount": 777
     },
       "fundingSource": {
         "type": "CASH"
        },
       "id": "abb0093c-2e3e-4500-9b16-658ec2eb8287",
       "references": [
         {
           "customType": "referenceId",
           "id": "myRefId",
           "type": "CUSTOM"
         }
       ],
       "status": "CAPTURED"
     }
    ],
   "status": "COMPLETED",
   "currency": "USD",
   "referenceId": "myRefId",
   "amount": 777,
   "tipAmount": 0,
   "cashbackAmount": 0,
   "disableDebitCards": false,
   "disableCash": false,
   "debit": false,
   "disableTip": false,
   "cashOnly": false,
   "nonReferencedCredit": false,
   "authzOnly": false,
   "multiTender": false
}
```
<a name="Poynt.launchAskConf"></a>
### Poynt.secondScreen.collectAgreement(successCallback, errorCallback,options)
Launches secondScreenService->captureAgreement passing msg as URL. "YES" or "NOT" strings are passed back in callback. In case of error, errorCallback is called with error string in argument

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| successCallback |  |  |
| errorCallback |  |  |
| options | JSON | parameters: msg |

**Example Request**  
```js
Poynt.secondScreen.collectAgreement( succcessCallback, failureCallback,{"msg":'http://example.com/privacy.html');
```

<a name="Poynt.launchSign"></a>
### Poynt.secondScreen.collectSignature(successCallback, errorCallback,options)
Launches secondScreenService->captureSignature passing title, button name (only for Accept behaviour) and message. In case of success the function returns a base64 string that represents the bitmap bytestream of the signature as argument in successCallback. In case of error an error string in errorCallback. 

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| successCallback |  |  |
| errorCallback |  |  |
| options | JSON | parameters: title,leftbutton,msg |

**Example Request**  
```js
Poynt.launchSign(succcessCallback, failureCallback,{"title":'Confirm by sign',"leftbutton":'Signed',"msg":'by signing you agree...'});
```
<a name="Poynt.launchInit"></a>
### Poynt.Init(successCallback, errorCallback)
This function initialize services for Business and SecondScreen services. In case of error a string is returned in errorCallback 

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| successCallback |  |  |
| errorCallback |  |  |

**Example Request**  
```js
Poynt.Init(succcessCallback, failureCallback);
```

<a name="Poynt.launchMsg"></a>
### Poynt.secondScreen.displayMessage(successCallback, errorCallback,options)
Launches secondScreenService->displayMessage passing the msg as parameter.

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| successCallback |  |  |
| errorCallback |  |  |
| options | JSON | parameters: msg |

**Example Request**  
```js
Poynt.secondScreen.displayMessage(succcessCallback, failureCallback,{"msg":'a message in second screen'});
```
<a name="Poynt.launchInfo"></a>
### Poynt.business.getBusiness(successCallback, errorCallback)
Launches businessService->getBusiness. The response is passed to the success callback as a JSON object. In case of error a string is passed in errorCallback

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| successCallback |  |  |
| errorCallback |  |  |

**Example Request**  
```js
Poynt.business.getBusiness(succcessCallback, failureCallback);
```

**Example Response**
```js
{
   "name": "My Business Name",
   "email": "email@email....",
   "phone": "021223-.....",
   "mcc": "8099" 
}
```



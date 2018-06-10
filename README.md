---
title: Poynt
description: Work with Poynt Payment Fragment, Second Screen Service
---

# cordova-plugin-poynt2

This plugin defines a global `Poynt` object, which provides an API for working with Poynt Payment Fragment.

## Installation
The plugin was written and tested using with cordova v6.3.0

    cordova plugin add cordova-plugin-poynt2
    
It is also possible to install via repo url directly

    cordova plugin add https://github.com/xale76/cordova-plugin-poynt2.git
    


## How to Contribute

Please feel free to contribute. You can [report bugs](https://github.com/poynt/cordova-plugin-poynt/issues), improve the documentation, or [contribute code](https://github.com/xale76/cordova-plugin-poynt2/pulls).

---

# API Reference <a name="reference"></a>
* [Poynt](#module_Poynt)
    * [.launchPayment(amount, referenceId, successCallback, errorCallback)](#Poynt.launchPayment)
    * [.launchAskConf(msg,  successCallback, errorCallback)](#Poynt.launchAskConf)
    * [.launchSign(title, butt, msg,  successCallback, errorCallback)](#Poynt.launchSign)
    * [.launchInit(successCallback, errorCallback)](#Poynt.launchInit)
    * [.launchMsg(msg, successCallback, errorCallback)](#Poynt.launchMsg)
    * [.launchInfo(successCallback, errorCallback)](#Poynt.launchInfo)

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
### Poynt.launchAskConf(msg,  successCallback, errorCallback)
Launches captureAgreement passing msg as URL. "YES" or "NOT" strings are passed back in callback. In case of error, errorCallback is called with error string in argument

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| msg | string | URL for text/HTML |
| successCallback |  |  |
| errorCallback |  |  |

**Example Request**  
```js
Poynt.launchAskConf('http://example.com/privacy.html', succcessCallback, failureCallback);
```

<a name="Poynt.launchSign"></a>
### Poynt.launchSign(title, butt, msg, successCallback, errorCallback)
Launches captureSignature passing title, button name (only for Accept behaviour) and message. In case of success the function returns a base64 string that represents the bitmap bytestream of the signature as argument in successCallback. In case of error an error string in errorCallback. 

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| title | string | title of the actvity |
| butt | string | text for Accept button |
| msg | string | string for text unde sign region |
| successCallback |  |  |
| errorCallback |  |  |

**Example Request**  
```js
Poynt.launchSign('Confirm by sign','Signed','by signing you agree...', succcessCallback, failureCallback);
```
<a name="Poynt.launchInit"></a>
### Poynt.launchInit(successCallback, errorCallback)
This function initialize services for Business and SecondScreen services. In case of error a string is returned in errorCallback 

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| successCallback |  |  |
| errorCallback |  |  |

**Example Request**  
```js
Poynt.launchInit(succcessCallback, failureCallback);
```

<a name="Poynt.launchMsg"></a>
### Poynt.launchMsg(msg,successCallback, errorCallback)
Launches displayMessage passing the msg as parameter.

__Supported Platforms__

- PoyntOS


| Param | Type | Description |
| --- | --- | --- |
| successCallback |  |  |
| errorCallback |  |  |

**Example Request**  
```js
Poynt.launchMsg('a message in second screen',succcessCallback, failureCallback);
```




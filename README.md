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
    * [.launchAskConf(msg,  successCallback, errorCallback)](#module_Poynt.launchAskConf)
    * [.launchSign(title, butt, msg,  successCallback, errorCallback)](#module_Poynt.launchSign)
    * [.launchInit(successCallback, errorCallback)](#module_Poynt.launchInit)
    * [.launchMsg(msg, successCallback, errorCallback)](#module_Poynt.launchMsg)
    * [.launchInfo(successCallback, errorCallback)](#module_Poynt.launchInfo)

---

 
## Poynt
 
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




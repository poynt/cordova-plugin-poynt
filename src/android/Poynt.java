package co.poynt.cordova.plugin;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaPlugin;
//import org.apache.cordova.cordova;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.NumberFormat;

import co.poynt.os.model.Intents;
import co.poynt.os.model.Payment;

/* ale */
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.IBinder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Binder;
import co.poynt.os.services.v2.IPoyntSecondScreenService.Stub;
import co.poynt.os.services.v2.IPoyntSecondScreenService;
import co.poynt.os.services.v2.IPoyntSignatureListener;
import co.poynt.os.services.v2.IPoyntActionButtonListener;
import java.io.ByteArrayOutputStream;
import android.util.Base64;
/* ale */

/* ale2 */
import co.poynt.os.model.PoyntError;
import co.poynt.api.model.Business;
import co.poynt.api.model.Phone;
import co.poynt.api.model.Store;
import co.poynt.api.model.StoreDevice;
import co.poynt.os.services.v1.IPoyntBusinessReadListener;
import co.poynt.os.services.v1.IPoyntBusinessService;
import static android.content.Context.BIND_AUTO_CREATE;
/* */

/* ale3 */
 
import co.poynt.os.services.v1.IPoyntInAppBillingService;
import co.poynt.os.services.v1.IPoyntInAppBillingServiceListener;
import android.app.PendingIntent; 
import android.content.Context;
import android.content.IntentSender;
import java.util.UUID;
/* */



public class Poynt extends CordovaPlugin  {
    private static final String TAG = "Poynt";
    private static final String LAUNCH_PAYMENT="launchPayment";
    private static final String LAUNCH_ASKCONF="launchAskConf";
    private static final String LAUNCH_SIGN="launchSign";
    private static final String LAUNCH_MSG="launchMsg";
    private static final String LAUNCH_INIT="launchInit";
    private static final String LAUNCH_TEST="launchInfo";
    private static final String LAUNCH_HOME="launchHome";
	
    private static final String LAUNCH_INITB="launchInitB";
    private static final String LAUNCH_BILLING="launchBilling";
    private static final String LAUNCH_PLANS="launchPlans";
    private static final String LAUNCH_SUBSCR="launchSubscr";
    
    private CallbackContext callbackContext;        // The callback context from which we were invoked.
    private JSONArray executeArgs;

    public static final int UNKNOWN_ERROR = 0;
    public static final int INVALID_ARGUMENT_ERROR = 1;
    public static final int TIMEOUT_ERROR = 2;
    public static final int PENDING_OPERATION_ERROR = 3;
    public static final int IO_ERROR = 4;
    public static final int NOT_SUPPORTED_ERROR = 5;
    public static final int OPERATION_CANCELLED_ERROR = 6;
    public static final int PERMISSION_DENIED_ERROR = 20;

    public static final int COLLECT_PAYMENT_REQUEST = 54321;
    public static final int BUY_INTENT_REQUEST_CODE = 98765;
    
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action        The action to execute.
     * @param args          JSONArry of arguments for the plugin.
     * @param callbackId    The callback id used when calling back into JavaScript.
     * @return              A PluginResult object with a status and message.
     */
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        //TODO add different actions, the one below shoud be "charge"
        this.callbackContext = callbackContext;
        this.executeArgs = args;
        
        if (LAUNCH_INIT.equals(action))
        {
           cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    bindService();
                    
                }
            }); 
            
            //bindService();
            return true;
        }
        if (LAUNCH_INITB.equals(action))
        {
           cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    bindServiceBilling();
                    
                }
            }); 
            
            //bindService();
            return true;
        }
       
         
        if (LAUNCH_PAYMENT.equals(action)) {
            JSONObject arg_object = args.getJSONObject(0);
            Long amount = arg_object.getLong("amount");
            String referenceId = arg_object.getString("referenceId");
            String currencyCode = NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();

            Payment payment = new Payment();
            payment.setReferenceId(referenceId);

            payment.setCurrency(currencyCode);

            payment.setAmount(amount);


            // start Payment activity for result
            try {
                Intent collectPaymentIntent = new Intent(Intents.ACTION_COLLECT_PAYMENT);
                collectPaymentIntent.putExtra(Intents.INTENT_EXTRAS_PAYMENT, payment);
                this.cordova.startActivityForResult(this, collectPaymentIntent, COLLECT_PAYMENT_REQUEST);
            } catch (ActivityNotFoundException ex) {
                Log.e(TAG, "Poynt Payment Activity not found - did you install PoyntServices?", ex);
                this.callbackContext.error(getErrorString());
            }
        }
        else if (LAUNCH_BILLING.equals(action)) {
            JSONObject arg_object = args.getJSONObject(0);
            String referenceId = arg_object.getString("planid");
            Boolean isreplace = arg_object.getBoolean("replace");
            
            try
            {
            Bundle bundle = getBillingFragmentIntent(referenceId, isreplace);
            if (bundle != null && bundle.containsKey("BUY_INTENT")) {
                            PendingIntent intent = bundle.getParcelable("BUY_INTENT");
                            if (intent != null) {
                                try {
                                    cordova.getActivity().startIntentSenderForResult(intent.getIntentSender(), BUY_INTENT_REQUEST_CODE,null,
                                            Integer.valueOf(0),
                                            Integer.valueOf(0),
                                            Integer.valueOf(0));
                                     
                                } catch (IntentSender.SendIntentException e) {
                                    this.callbackContext.error("Failed to launch billing fragment!");
                                }
                            } else {
                                this.callbackContext.error("Did not receive buy intent!");
                            }
                        } else {
                            this.callbackContext.error("Failed to obtain billing fragment intent!");
                        }
            }
            catch (RemoteException e) {
              this.callbackContext.error("General Error in Billing!");
            }
        }
        else if (LAUNCH_PLANS.equals(action)) {	    
             cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                   getPlans();
                    
                }
             }); 
             return true;
		
        }
	else if (LAUNCH_SUBSCR.equals(action)) {	    
             cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                   checkSubscriptionStatus();
                    
                }
             }); 
             return true;
		
        }
        else if (LAUNCH_ASKCONF.equals(action)) {
            JSONObject arg_object = args.getJSONObject(0);
            String referencemsg = arg_object.getString("msg");
            showCollectAgreement(referencemsg);
        }
        else if (LAUNCH_SIGN.equals(action)) {
            JSONObject arg_object = args.getJSONObject(0);
            String referencemsg = arg_object.getString("msg");
            String titlemsg = arg_object.getString("title");
            String leftbutt = arg_object.getString("leftbutton");
            collectSignature(titlemsg,titlemsg,leftbutt);
        }
        else if (LAUNCH_MSG.equals(action)) {
            JSONObject arg_object = args.getJSONObject(0);
            String referencemsg = arg_object.getString("msg");
            showWelcome(referencemsg);
        }
	else if (LAUNCH_HOME.equals(action)) {
           showWelcomeHome();
        }
        else if (LAUNCH_TEST.equals(action)) {
           getBusiness(); 
        }
        return true;
    }

    private Bundle getBillingFragmentIntent(String planId, boolean replace) throws RemoteException {
        Bundle bundle = new Bundle();
        // add plan Id
        bundle.putString("plan_id", planId);
        bundle.putBoolean("replace", replace);
        String ida=cordova.getActivity().getPackageName();
        

        return mBillingService.getBillingIntent(ida, bundle);
    }
    
    private void checkSubscriptionStatus() {
        final CallbackContext cbk=this.callbackContext;
	try {
            if (mBillingService != null) {
                String ida=cordova.getActivity().getPackageName();
				String requestId = UUID.randomUUID().toString();
                mBillingService.getSubscriptions(ida, requestId,
                        new IPoyntInAppBillingServiceListener.Stub() {
                            @Override
                            public void onResponse(final String resultJson, final PoyntError poyntError, String requestId)
                                    throws RemoteException {
									if (poyntError != null) {
										Log.d(TAG, "poyntError: " + poyntError.toString());
									}
                                    else
									{
										/*JsonParser parser = new JsonParser();
                                            JsonObject json = parser.parse(resultJson).getAsJsonObject();
                                            JsonArray plans = json.getAsJsonArray("list");
                                            if (plans.size() > 0){
                                                setStatus(status, "PLANS RECEIVED");
                                            }
                                            logReceivedMessage("Result for get plans: "
                                                    + toPrettyFormat(resultJson));*/
										cbk.success(resultJson);	
									}
                            }
                        });

            } else {
                 cbk.error(  "Not connected to InAppBillingService!");
            }
        } catch (SecurityException e) {
            cbk.error(e.getMessage());
        } catch (RemoteException e) {
            cbk.error("Remote Error");
        }
    }

	
    private void getPlans() {
	    final CallbackContext cbk=this.callbackContext;
        try {
            if (mBillingService != null) {
                String ida=cordova.getActivity().getPackageName();
                String requestId = UUID.randomUUID().toString();
                mBillingService.getPlans(ida, requestId,
                        new IPoyntInAppBillingServiceListener.Stub() {
                            @Override
                            public void onResponse(final String resultJson, final PoyntError poyntError, String requestId)
                                    throws RemoteException {
									if (poyntError != null) {
										cbk.error( poyntError.toString());
									}
									else
									{
										/*JsonParser parser = new JsonParser();
                                            JsonObject json = parser.parse(resultJson).getAsJsonObject();
                                            JsonArray plans = json.getAsJsonArray("list");
                                            if (plans.size() > 0){
                                                setStatus(status, "PLANS RECEIVED");
                                            }
                                            logReceivedMessage("Result for get plans: "
                                                    + toPrettyFormat(resultJson));*/
										cbk.success(resultJson);	
									}
                                     
                            }
                        });

            } else {
                cbk.error(  "Not connected to InAppBillingService!");
            }
        } catch (SecurityException e) {
            cbk.error(e.getMessage());
        } catch (RemoteException e) {
            cbk.error("Remote Error");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == COLLECT_PAYMENT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data!=null) {

                    Payment payment = data.getParcelableExtra(Intents.INTENT_EXTRAS_PAYMENT);


                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Type paymentType = new TypeToken<Payment>(){}.getType();
                    String paymentResult = gson.toJson(payment, paymentType);

                    this.callbackContext.success(paymentResult);
                    return;
                }else{
                    //TODO no payment was returned. Notify callback
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.callbackContext.error(getErrorString());
                return;
            }

            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, UNKNOWN_ERROR));
        }
       else if (requestCode == BUY_INTENT_REQUEST_CODE) {
           if (resultCode == Activity.RESULT_OK) {
                this.callbackContext.success("Subscription request was successful - run Check Subscription to confirm!");
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.callbackContext.error("Subscription request failed!");
            }
       }
    }

    private String getErrorString(){
        return "{\n" + 
               "    \"status\": \"TRANSACTION_CANCELLED\"\n" +
               "}";
    }
    
    /* ALE */
    
    private String getGenString(String st){
        return "{\n" + 
               "    \"value\": \"" + st + "\"\n" +
               "}";
    }
    
     
    private IPoyntBusinessService businessService;
    private IPoyntSecondScreenService secondScreenService;
    private co.poynt.os.services.v1.IPoyntSecondScreenService secondScreenServiceV1;

    private ServiceConnection serviceConnection;
    private ServiceConnection serviceConnectionI;
    private ServiceConnection serviceConnectionV1;    	
	
    private IPoyntInAppBillingService mBillingService;
    private ServiceConnection serviceConnectionB;
    
    public void bindServiceBilling()
    {
       final CallbackContext cbk=this.callbackContext;
       if (mBillingService!=null)
         {
	       cbk.success("");
	       return;
         }
	    
       serviceConnectionB = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBillingService = null;
                cbk.error("Error Binding BillingService");
                }

            @Override
            public void onServiceConnected(ComponentName name,
                    IBinder service) {
                mBillingService = IPoyntInAppBillingService.Stub.asInterface(service);
                cbk.success("");
            }
        };
	
	Intent serviceIntent = new Intent("com.poynt.store.PoyntInAppBillingService.BIND");
        serviceIntent.setPackage("com.poynt.store");
        cordova.getActivity().bindService(serviceIntent,serviceConnectionB, BIND_AUTO_CREATE);
    }	    
	    
    public void bindService() {
        //setup service connection
        final CallbackContext cbk=this.callbackContext;
        
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                secondScreenService=null;
                cbk.error("Error Binding SecondScreen");
                }

            @Override
            public void onServiceConnected(ComponentName name,
                    IBinder service) {
                secondScreenService = IPoyntSecondScreenService.Stub.asInterface(service);
                cbk.success("");
            }
        };
        serviceConnectionI = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                businessService = null;
                cbk.error("Error Binding businessService");
                }

            @Override
            public void onServiceConnected(ComponentName name,
                    IBinder service) {
                businessService = IPoyntBusinessService.Stub.asInterface(service);
                cbk.success("");
            }
        };
        
	serviceConnectionV1 = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                secondScreenServiceV1=null;
                cbk.error("Error Binding SecondScreen");
                }

            @Override
            public void onServiceConnected(ComponentName name,
                    IBinder service) {
                secondScreenServiceV1 = co.poynt.os.services.v1.IPoyntSecondScreenService.Stub.asInterface(service);
                cbk.success("");
            }
        };
	    
        cordova.getActivity().bindService(Intents.getComponentIntent(Intents.COMPONENT_POYNT_SECOND_SCREEN_SERVICE_V2),serviceConnection, BIND_AUTO_CREATE);
        cordova.getActivity().bindService(Intents.getComponentIntent(Intents.COMPONENT_POYNT_BUSINESS_SERVICE),serviceConnectionI, BIND_AUTO_CREATE);
   	cordova.getActivity().bindService(Intents.getComponentIntent(Intents.COMPONENT_POYNT_SECOND_SCREEN_SERVICE),serviceConnectionV1, BIND_AUTO_CREATE);
   
    }
 
    public void showWelcome(String message) {
        try {
            Bundle options = new Bundle();
            /*options.putString("FONT_COLOR", "#f07f22");
            options.putString(Intents.EXTRA_CONTENT_TYPE, Intents.EXTRA_CONTENT_TYPE_HTML);
            secondScreenService.displayMessage(message, options);*/
            options.putString("FONT_COLOR", "#f07f22");
            options.putString(Intents.EXTRA_CONTENT_TYPE, Intents.EXTRA_CONTENT_TYPE_HTML);
            secondScreenService.displayMessage(message, options);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
   
    public void showWelcomeHome()
    { 
      try {
       secondScreenServiceV1.displayWelcome(null, null, null);
      } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
	
    public void collectSignature(String tit, String msg, String butt) {
        try {
            final CallbackContext cbk=this.callbackContext;
            Bundle options = new Bundle();
            options.putString(Intents.EXTRA_TITLE, tit);
            options.putString(Intents.EXTRA_RIGHT_BUTTON_TITLE, butt);
             
            options.putString(Intents.EXTRA_TEXT_UNDER_LINE, msg);
            secondScreenService.captureSignature(null, options, new IPoyntSignatureListener.Stub() {
                @Override
                public void onSignatureEntered(Bitmap bitmap) throws RemoteException {
                     
                    if (bitmap != null){
                      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
                      bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                      byte[] byteArray = byteArrayOutputStream .toByteArray();
                      String encoded = Base64.encodeToString(byteArray,Base64.DEFAULT); 
                      cbk.success(encoded);  
                    }
                }

            });
        } catch (RemoteException e) {
            e.printStackTrace();
            this.callbackContext.error("Error");
        }
    }
 
     
    
    public void showCollectAgreement(String msg) {
        try {
            final CallbackContext cbk=this.callbackContext;
            Bundle options = new Bundle();
            options.putString(Intents.EXTRA_LEFT_BUTTON_TITLE, "NO");
            options.putString(Intents.EXTRA_RIGHT_BUTTON_TITLE, "ACCETTO");
            /** AS URL **/
            //options.putString(Intents.EXTRA_CONTENT_TYPE, Intents.EXTRA_CONTENT_TYPE_URL);
            //String agreement = "https://www.visitamiapp.com/note-legali";
            /* */
           
            //options.putString(Intents.EXTRA_CONTENT_TYPE, Intents.EXTRA_CONTENT_TYPE_HTML);
            options.putString(Intents.EXTRA_CONTENT_TYPE, Intents.EXTRA_CONTENT_TYPE_URL);
            String agreement = msg;
//            /** AS TEXT **/
//            options.putString(Intents.EXTRA_CONTENT_TYPE, Intents.EXTRA_CONTENT_TYPE_TEXT);
//            String agreement = getAgreementText(R.raw.customer_agreement);
            secondScreenService.captureAgreement(agreement,
                    options, new IPoyntActionButtonListener.Stub() {
                        @Override
                        public void onLeftButtonClicked() throws RemoteException {
                            cbk.success("NO");
                        }

                        @Override
                        public void onRightButtonClicked() throws RemoteException {
                            cbk.success("YES");
                        }

                    });
        } catch (RemoteException e) {
            e.printStackTrace();
            this.callbackContext.error("ERROR");
        }
    }
 
    /* ALE */
    
    /* ALE2 */
     public void getBusiness() {
        
            final CallbackContext cbk=this.callbackContext;
            IPoyntBusinessReadListener bizListener = new IPoyntBusinessReadListener.Stub() {
            @Override
            public void onResponse(Business business, PoyntError poyntError) throws RemoteException {
                        if (business != null){
                             Gson gson = new GsonBuilder().setPrettyPrinting().create();
                             Type businessType = new TypeToken<Business>(){}.getType();
                             String businessS = gson.toJson(business, businessType);
                             cbk.success(businessS);
                        }
                        else
                        {
                            cbk.error("NO BUSINESS");
                        }
                    }
                };
        try{
                businessService.getBusiness(bizListener);
            } catch (RemoteException e) {
                e.printStackTrace();
                this.callbackContext.error("ERROR"); 
            } 
    }   
    
    
    /*  */
    
      
    
}

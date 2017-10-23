package com.truxapp.fbfire;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.truxapp.fbfire.Activity.CommonBaseActivity;
import com.truxapp.fbfire.Activity.HomeActivity1;
import com.truxapp.fbfire.Activity.RegisterProfileActivity;
import com.truxapp.fbfire.Model.LoginModel;
import com.truxapp.fbfire.app.Constants;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.response.LoginResponsee;
import com.truxapp.fbfire.util.NotificationUtils;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends CommonBaseActivity {
    private CallbackManager callbackManager;
    public static final String TAG = "MainActivity";
    Button google_sign, fbBtn, linkedinBtn;
    LoginButton loginButton;
    LinearLayout signUpLayout;
    String emailAddress = "";
    private static final String host = "api.linkedin.com";
    private static final String LinkedInurl = "https://" + host
            + "/v1/people/~:" +
            "(email-address,formatted-name,phone-numbers,picture-urls::(original))";

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private static final int RC_SIGN_IN = 0 ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    String manufacturer, model, androidOS, devicemac;
    int androidAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        google_sign = (Button) findViewById(R.id.google_sign);
        linkedinBtn = (Button) findViewById(R.id.linkedinBtn);
        signUpLayout = (LinearLayout) findViewById(R.id.signUpLayout);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        fbBtn = (Button) findViewById(R.id.facebook_login);
        // Device Info
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        devicemac = telephonyManager.getDeviceId();
        manufacturer = Build.MANUFACTURER;
        model = android.os.Build.MODEL;
        androidAPI = Build.VERSION.SDK_INT;
        androidOS = android.os.Build.VERSION.RELEASE;
        askForPermissions();
        // Initialize your instance of callbackManager
        callbackManager = CallbackManager.Factory.create();
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CommonBaseActivity.isOnline(MainActivity.this)) {
                    mSnackBarMessage(findViewById(R.id.show_error), "Please check Internet Connection");
                } else {
                    loginButton.performClick();
                    loginButton.setReadPermissions("email", "public_profile");
                }
            }
        });

        signUpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterProfileActivity.class));
            }
        });

        // Register your callback
        LoginManager.getInstance().registerCallback(callbackManager,
                // If the login attempt is successful, then call onSuccess and pass the LoginResult//
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                            Log.d(TAG, "User ID: " +
                                loginResult.getAccessToken().getUserId() + "\n" +
                                "Auth Token: " + loginResult.getAccessToken().getToken());
                          Log.d("Auth Token: " ,loginResult.getAccessToken().getToken());
                        AccessToken accesstoken = loginResult.getAccessToken();
                        RequestData(accesstoken);
                    }
                    // If the user cancels the login, then call onCancel
                    @Override
                    public void onCancel() {
                    }
                    // If an error occurs, then call onError
                    @Override
                    public void onError(FacebookException exception) {
                    }
                });

        //Google Code
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
      /*  mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if(user.getDisplayName() != null)
                        emailAddress = user.getEmail().toString();
//                      loginWithAPI(emailLogin);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };*/

        linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CommonBaseActivity.isOnline(MainActivity.this)) {
                    mSnackBarMessage(findViewById(R.id.show_error), "Please check Internet Connection");
                } else {
                    LISessionManager.getInstance(getApplicationContext())
                            .init(MainActivity.this, buildScope(), new AuthListener() {
                                @Override
                                public void onAuthSuccess() {
                                    linkededinApiHelper();
                                }
                                @Override
                                public void onAuthError(LIAuthError error) {
                                }
                            }, true);
                }
            }
        });

        google_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonBaseActivity.isOnline(MainActivity.this)) {
                    mSnackBarMessage(findViewById(R.id.show_error), "Please check Internet Connection");
                } else {
                    signIn();
                }
            }
        });
        //Firebase Notifications
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
                    displayFirebaseRegId();
                } else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                }
            }
        };
        displayFirebaseRegId();




        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.truxapp.fbfire",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }




    }

    private void askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_PHONE_STATE
                }, 2);
                return;
            }
        }
    }

    public void RequestData(AccessToken accesstoken){
        GraphRequest request = GraphRequest.newMeRequest(accesstoken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                try {
                    System.out.println("Json data :"+object);
                    System.out.println("Json Response :"+response);
                    String email = object.getString("email");
                    loginViaRetrofit(email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,gender,email,first_name,last_name,location,locale,timezone,age_range");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);
//        if (!TextUtils.isEmpty(regId))
//            txtRegId.setText("Firebase Reg Id: " + regId);
//        else
//            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //Linkedin
        LISessionManager.getInstance(getApplicationContext())
                .onActivityResult(this,
                        requestCode, resultCode, data);
        //Google
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            googleeSignAct(result);
        }
    }

    //LINKEDIN LOGIN CODE
  /*  public void login(View view){
        LISessionManager.getInstance(getApplicationContext())
                .init(this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {
                        linkededinApiHelper();
                    }
                    @Override
                    public void onAuthError(LIAuthError error) {
                    }
                }, true);
    }*/


    public void linkededinApiHelper(){
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(MainActivity.this, LinkedInurl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {
                    showResult(result.getResponseDataAsJson());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onApiError(LIApiError error) {
            }
        });
    }

    public  void  showResult(JSONObject response){
        try {
            emailAddress = response.get("emailAddress").toString();
            loginViaRetrofit(emailAddress);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    // set the permission to retrieve basic information of User's linkedIn account
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    //Google Code
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void googleeSignAct(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String email = account.getEmail();
            loginViaRetrofit(email);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.REGISTRATION_COMPLETE));
        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));
        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

    private void loginViaRetrofit(String email){
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("email_id",email);
            reqData.put("mac_id", devicemac);
            reqData.put("device_manufacturer",manufacturer);
            reqData.put("model",model);
            reqData.put("os_version",androidOS);
            reqData.put("os_api",androidAPI);
            reqData.put("login_as", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("ReqData", ""+ reqData);
        WebcallMaegerClass.getInstance().loginAPI(this, com.truxapp.fbfire.util.Constants.POST_REQUEST, reqData, true);
    }

    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (result instanceof LoginResponsee) {
            LoginResponsee res = (LoginResponsee) result;
            if (baseResponse.getErrorCode().equalsIgnoreCase("200")) {
                LoginModel loginResponse = res.getResult();
                if (loginResponse!=null) {
                    String reg_id = loginResponse.getId();
                    String fName = loginResponse.getFirstname();
                    String lName = loginResponse.getLastname();
                    String email = loginResponse.getEmail_id();
                    String alternateEmail = loginResponse.getAlternate_email_id();
                    String mobileNumber = loginResponse.getMobile_no();
                    String alternateMobileNumber = loginResponse.getAlternate_mobile_no();
                    String companyDetails = loginResponse.getClient_name();

                    savePreferences(reg_id, fName, lName, email, alternateEmail, mobileNumber, alternateMobileNumber, companyDetails);
                    Intent intent = new Intent(MainActivity.this, HomeActivity1.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                mSnackBarMessage(findViewById(R.id.show_error), baseResponse.getErrorMessage());
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        startActivity(new Intent(MainActivity.this, RegisterProfileActivity.class));
                        }
                    },
                2000);
            }

        }
    }

    private void savePreferences(String reg_id, String fName, String lName, String email, String alternateEmail, String mobileNumber,
                                 String alternateMobileNumber, String companyDetails) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("registration_id", reg_id);
        editor.putString("fName", fName);
        editor.putString("lName", lName);
        editor.putString("email", email);
        editor.putString("alternateEmail", alternateEmail);
        editor.putString("mobileNumber", mobileNumber);
        editor.putString("alternateMobileNumber", alternateMobileNumber);
        editor.putString("companyDetails", companyDetails);
//        editor.putString("address",address);
        editor.commit();
    }
}
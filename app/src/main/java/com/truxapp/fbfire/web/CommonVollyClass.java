package com.truxapp.fbfire.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.truxapp.fbfire.Activity.CommonBaseActivity;
import com.truxapp.fbfire.Fragment.CommonBaseFragment;
import com.truxapp.fbfire.util.AppPreference;
import com.truxapp.fbfire.util.Constants;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommonVollyClass<T> extends CommonBaseActivity {
    private static final String TAG = CommonVollyClass.class.getSimpleName();
    protected ProgressDialog _dialog;
    protected String message = "Loading...";
    Context _context;
    Class<T> _responseType;
    String _requestUrl;
    String methodName;
    private boolean isPost;
    private boolean isForFragment;
    private boolean isUserResponse;
    private boolean isApiRequired = true;
    private boolean isToShowProgress = true;
    private String apiKey;
    private String mode;
    private CommonBaseFragment mFragment;
    JSONObject object;
    int mMODE;
    T result = null;
    String response = "";

    public CommonVollyClass(Context context, String requestUrl, Class<T> responseType, String methodName, String Mode, JSONObject obj ,boolean isProgress) {
        _context = context;
        _requestUrl = requestUrl;
        _responseType = responseType;
        this.methodName = methodName;
        this.isToShowProgress = isProgress;
        this.mode = Mode;
        this.object = obj;
        if (mode.equalsIgnoreCase("1")) {
            mMODE = Request.Method.GET;
        } else {
            mMODE = Request.Method.POST;
        }
        AppPreference prefs = new AppPreference(_context.getApplicationContext());
        apiKey = prefs.getStringValueForTag(Constants.API_KEY);
        if (isToShowProgress) {
            _dialog = ProgressDialog.show(_context, "", message, true);
        }
        if(object == null){
            CallWithNullBody(methodName);
        }else {
            someMethodName(object, methodName);
        }
    }

    public void someMethodName(JSONObject object, String url) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Accept", "application/json;versions=1");
                        if (isApiRequired) {
                            ongoing.addHeader("authkey", apiKey);
                        }
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST_URL)
                .client(httpClient)
                .build();

        ApiCalls fdtgh = retrofit.create(ApiCalls.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), object.toString());
        Call<ResponseBody> requestCall;
        if(mode.equalsIgnoreCase("1")){
            requestCall = fdtgh.someCallGet(url, body);
        }else {
            requestCall = fdtgh.someCallPost(url, body);
        }
        requestCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    String response = rawResponse.body().string();
                    Log.d("Responce_data", response);
                    if (_dialog != null) {
                        _dialog.cancel();
                    }
                    result = new Gson().fromJson(response, _responseType);
                    if (isFragment()) {
                        mFragment.processFragmentResponse(result);
                    } else {
                        ((CommonBaseActivity) _context).processResponse(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (_dialog != null) {
                    _dialog.cancel();
                }
                Toast.makeText(_context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CallWithNullBody(String url) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Accept", "application/json; charset=utf-8");
                        if (isApiRequired) {
                            ongoing.addHeader("authKey", "JKWDOGgIkLNLia38rryFxKL7HEoKeqXSrxoedbjCTZ0+IP5WGZXRHof3wfRBo6am");
                        }
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCalls apiCalls = retrofit.create(ApiCalls.class);
        Call requestCall;
        if (mode.equalsIgnoreCase("1")) {
            requestCall = apiCalls.someCallGetNoBody(url);
        } else {
            requestCall = apiCalls.someCallPostNoBody(url);
        }
        requestCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String dre = response.body().string().toString();
                    Log.d("Responce_data", dre);
                    if (_dialog != null) {
                        _dialog.cancel();
                    }
                    result = new Gson().fromJson(dre, _responseType);
                    if (isFragment()) {
                        mFragment.processFragmentResponse(result);
                    } else {
                        ((CommonBaseActivity) _context).processResponse(result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (_dialog != null) {
                    _dialog.cancel();
                }
                String response = throwable.getMessage();
            }
        });

    }

    public void setIsApiKeyRequired(boolean isReqoured) {
        this.isApiRequired = isReqoured;
    }

    public boolean isFragment() {
        return isForFragment;
    }

    void setFragment(CommonBaseFragment fragment) {
        mFragment = fragment;
    }

    public void setForFragment(boolean isFragment) {
        this.isForFragment = isFragment;
    }

    public void setMessage(String loadingMessage) {
        message = loadingMessage;
    }

    public void setShowProgress(boolean isToShowProgress) {
        this.isToShowProgress = isToShowProgress;
    }


}

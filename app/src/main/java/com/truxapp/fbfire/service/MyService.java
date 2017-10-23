package com.truxapp.fbfire.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.truxapp.fbfire.app.Constants;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service{
    public static final int notify = 10000;
        private Handler mHandler = new Handler();
        private Timer mTimer = null;
        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        @Override
        public void onCreate() {
            if (mTimer != null)
                mTimer.cancel();
            else
                mTimer = new Timer();
                mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            mTimer.cancel();
        }
       class TimeDisplay extends TimerTask {
      @Override
      public void run() {
          mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {

                    Intent registrationComplete = new Intent(Constants.CONFIRM_ORDER_RESPONCE);
                    LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(registrationComplete);
//                    Toast.makeText(MyService.this, "Running", Toast.LENGTH_LONG).show();
                    //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                     //send_mgs_toset_vts("http://180.151.15.77:8080/truxapiv2/leads/getAccountOpenCount");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
    private void send_mgs_toset_vts(String SubURL){
        String URL =SubURL;
        final RequestQueue queue = Volley.newRequestQueue(this);
        queue.getCache().remove(URL);
        queue.getCache().clear();
        /*JSONObject jsonobject_one = new JSONObject();
        try {
          //  jsonobject_one.put("current_time", currentTime);
        }catch (JSONException e) {
            e.printStackTrace();
        }*/
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, URL,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resultJson) {
                String resultObj = resultJson.toString();
                try {
                    if (resultObj.equals("")) {
                        return;
                    }
                    JSONObject resultObject = new JSONObject(resultObj);

                    Intent registrationComplete = new Intent(Constants.CONFIRM_ORDER_RESPONCE);
                    registrationComplete.putExtra("name" ,"vinay");
                    LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(registrationComplete);




                    /*if (resultObject.getString("errorCode").equals("100")) {
                        Responce rsp =  new Gson().fromJson(resultObj,Responce.class);
                        Model [] data = rsp.getData();
                        ArrayList<Model> list = new ArrayList<>(Arrays.asList(data));
                    }else{
                       // Toast.makeText(MyService.this,resultObject.getString("errorMesaage"), Toast.LENGTH_SHORT).show();
                    }*/
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }

        });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);
    }


}
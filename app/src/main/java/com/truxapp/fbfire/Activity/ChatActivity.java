package com.truxapp.fbfire.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truxapp.fbfire.Model.ChatHistoryModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.app.Constants;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.response.ChatHistoryResponse;
import com.truxapp.fbfire.response.PingMessageResponse;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ChatActivity extends CommonBaseActivity {
    ScrollView scrollView;
    EditText chatEditText;
    Button chatSendButton;
    String sendMessage, order_id;
    ArrayList<ChatHistoryModel> arrayList;
    ImageView back_btn;

    public BroadcastReceiver apiResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pingAPI();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        scrollView = (ScrollView) findViewById(R.id.chat_scrollView);
        chatSendButton = (Button) findViewById(R.id.chatSendButton);
        chatEditText = (EditText) findViewById(R.id.chatEditText);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus().getWindowToken()!=null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                finish();
            }
        });

        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage = chatEditText.getText().toString();
                sendAPI();
            }
        });

        Intent intent = getIntent();
        if (intent!=null){
            order_id = intent.getStringExtra("Order_id");
        }

        String chatHistorySaveString = prefs.getStringValueForTag(order_id);
        if (chatHistorySaveString!=null && !chatHistorySaveString.equalsIgnoreCase("")){
            Gson gson = new Gson();
            ArrayList<ChatHistoryModel> lstArrayList = gson.fromJson(chatHistorySaveString, new TypeToken<List<ChatHistoryModel>>(){}.getType());
            arrayList = new ArrayList<ChatHistoryModel>();
            arrayList.addAll(lstArrayList);
            showHistoryChatList(lstArrayList);
        }else {
            chatHistoryAPI();
        }
    }

    private void sendAPI() {
        if (chatEditText.getText().toString()!=null && chatEditText.getText().toString().length()>0){
            sendMessage = chatEditText.getText().toString();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("msg",sendMessage);
                jsonObject.put("msg_from","2");
                jsonObject.put("order_id",order_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            WebcallMaegerClass.getInstance().sendMessageAPI(this, com.truxapp.fbfire.util.Constants.POST_REQUEST, jsonObject, true);
        }
    }

    private void pingAPI(){
        JSONObject jsonObject = new JSONObject();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateandTime = sdf.format(new Date());
            jsonObject.put("order_id", order_id);
//            jsonObject.put("last_updated_time", "2017-10-13T16:55:14.000Z");
            jsonObject.put("last_updated_time", currentDateandTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebcallMaegerClass.getInstance().pingAPI(this, com.truxapp.fbfire.util.Constants.POST_REQUEST, jsonObject, false);
    }


    private void chatHistoryAPI(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("order_id", order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebcallMaegerClass.getInstance().chatHistoryAPI(this, com.truxapp.fbfire.util.Constants.POST_REQUEST, jsonObject, true);
    }

    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        if (result instanceof ChatHistoryResponse){
            ChatHistoryResponse res = (ChatHistoryResponse) result;
            if (res.getErrorCode().equalsIgnoreCase("201")){
                ChatHistoryModel [] model = res.getResult();
                arrayList = new ArrayList<ChatHistoryModel>(Arrays.asList(model));
                if(arrayList !=null && arrayList.size()>0){
                    showHistoryChatList(arrayList);
                }
            }
        }

        if (result instanceof BaseResponse){
            BaseResponse res = (BaseResponse) result;
            if (res.getErrorCode().equalsIgnoreCase("201")){
                chatEditText.setText("");
                showSendMessage();
            }
        }

        if (result instanceof PingMessageResponse){
            PingMessageResponse res = (PingMessageResponse) result;
            if (res.getErrorCode().equalsIgnoreCase("201")){
                ChatHistoryModel [] model = res.getResult();
                ArrayList<ChatHistoryModel> pingArray = new ArrayList<>(Arrays.asList(model));
                if (pingArray!=null && pingArray.size()>0){
                    String chat_last_time = prefs.getStringValueForTag(Constants.CHAT_LAST_TIME);
                    if (!chat_last_time.equalsIgnoreCase("") && chat_last_time.length()>0){
                        if (chat_last_time.equalsIgnoreCase(pingArray.get(pingArray.size()-1).getCreatetime())){

                        }else {
                            prefs.setStringValueForTag(Constants.CHAT_LAST_TIME, pingArray.get(pingArray.size()-1).getCreatetime());
                            arrayList.addAll(pingArray);
                            showHistoryChatList(pingArray);

                        }
                    }else {
                        prefs.setStringValueForTag(Constants.CHAT_LAST_TIME, pingArray.get(pingArray.size()-1).getCreatetime());
                        arrayList.addAll(pingArray);
                        showHistoryChatList(pingArray);

                    }
                }
            }
        }
    }

    private void showHistoryChatList(ArrayList<ChatHistoryModel> arrayList) {
        for (ChatHistoryModel p : arrayList) {
            LinearLayout chatLayout = (LinearLayout) findViewById(R.id.chatLayout);
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (p.getMsgfrom().equalsIgnoreCase("2")) {
                View chatfragment_right = layoutInflater.inflate(R.layout.chat_right, null);
                TextView chat_r_mesg = (TextView) chatfragment_right.findViewById(R.id.chat_r_mesg);
                TextView chat_r_time = (TextView) chatfragment_right.findViewById(R.id.chat_r_time);
                chat_r_mesg.setText(p.getMessage());
                chat_r_time.setText(p.getCreatetime());
                try {
                    chatLayout.addView(chatfragment_right);
                } catch (Exception e) {

                }
            } else {
                View chatfragment_left = layoutInflater.inflate(R.layout.chat_left, null);
                TextView chat_l_mesg = (TextView) chatfragment_left.findViewById(R.id.chat_l_mesg);
                TextView chat_l_time = (TextView) chatfragment_left.findViewById(R.id.chat_l_time);
                chat_l_mesg.setText(p.getMessage());
                chat_l_time.setText(p.getCreatetime());

/*                Picasso.with(getActivity())
                        .load(userimage).transform(new CircleTransform())
                        .into(chat_l_pro_pic);*/
                try {
                    chatLayout.addView(chatfragment_left);
                } catch (Exception e) {
                }
            }
            try {
                scrollView = (ScrollView) findViewById(R.id.chat_scrollView);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            } catch (Exception e) {

            }
        }
    }


    private void showSendMessage(){

        ChatHistoryModel chatHistoryModel  = new ChatHistoryModel();
        chatHistoryModel.setMessage(sendMessage);
        chatHistoryModel.setCreatetime(sendMessage);
        chatHistoryModel.setMsgfrom("2");
        ArrayList<ChatHistoryModel> newMsgList = new ArrayList<ChatHistoryModel>(Arrays.asList(chatHistoryModel));
        arrayList.addAll(newMsgList);
        showHistoryChatList(newMsgList);
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(ChatActivity.this).registerReceiver(apiResponseReceiver, new IntentFilter(Constants.CONFIRM_ORDER_RESPONCE));
      /*  ArrayList<Lcv_Mcv_Model> sd = ((MyOrder) getParentFragment()).getConformOderListFromBrodCast();
        setAdapterDataFromBrodCast(sd);*/
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(ChatActivity.this).unregisterReceiver(apiResponseReceiver);
        Gson gson = new Gson();
        String listString = gson.toJson(arrayList, new TypeToken<ArrayList<ChatHistoryModel>>() {}.getType());
        prefs.setStringValueForTag(order_id, listString);
        super.onPause();
    }
}

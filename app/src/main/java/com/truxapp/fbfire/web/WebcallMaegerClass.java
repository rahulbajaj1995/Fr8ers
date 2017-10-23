package com.truxapp.fbfire.web;

import android.content.Context;

import com.truxapp.fbfire.Fragment.CommonBaseFragment;
import com.truxapp.fbfire.response.AircraftLoadTypeResponse;
import com.truxapp.fbfire.response.AirportResponse;
import com.truxapp.fbfire.response.ApprovedQuotationResponse;
import com.truxapp.fbfire.response.AvailableAircraftResponse;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.response.ChangeQuoatationResponse;
import com.truxapp.fbfire.response.ChatHistoryResponse;
import com.truxapp.fbfire.response.CountQuotationHistoryResponse;
import com.truxapp.fbfire.response.LoginResponsee;
import com.truxapp.fbfire.response.PendingQuotationResponse;
import com.truxapp.fbfire.response.PingMessageResponse;
import com.truxapp.fbfire.response.RequestForAvailablityResponse;
import com.truxapp.fbfire.response.SerchFrighterResponse;
import com.truxapp.fbfire.response.SharedQuotationResponse;
import com.truxapp.fbfire.util.Constants;

import org.json.JSONObject;

public class WebcallMaegerClass {
    private static WebcallMaegerClass singleInstance;
    protected WebcallMaegerClass() {
    }
    public static WebcallMaegerClass getInstance() {
        if (singleInstance == null) {
            singleInstance = new WebcallMaegerClass();
        }
        return singleInstance;
    }

    public void loginAPI(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<LoginResponsee> httptask = new CommonVollyClass<>(context, Constants.HOST_URL, LoginResponsee.class, Constants.LOGIN_API,Mode,obj, isProgress);
        httptask.setIsApiKeyRequired(false);
        httptask.setForFragment(false);
        httptask.setShowProgress(isProgress);
    }

    public void registerLogin(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<LoginResponsee> httptask = new CommonVollyClass<>(context, Constants.HOST_URL, LoginResponsee.class, Constants.REGISTER_LOGIN_API,Mode,obj, isProgress);
        httptask.setIsApiKeyRequired(false);
        httptask.setForFragment(false);
        httptask.setShowProgress(isProgress);
    }

    public void  findLoad(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<SerchFrighterResponse> httptask = new CommonVollyClass<>(context, Constants.HOST_URL, SerchFrighterResponse.class, Constants.FIND_LOAD,Mode,obj, isProgress);
        httptask.setIsApiKeyRequired(false);
        httptask.setForFragment(false);
        httptask.setShowProgress(isProgress);
    }

    public void findAirport(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<AirportResponse> airportResponse  = new CommonVollyClass<>(context, Constants.HOST_URL, AirportResponse.class, Constants.FIND_AIRPORT,Mode,obj, isProgress);
        airportResponse.setIsApiKeyRequired(false);
        airportResponse.setForFragment(false);
        airportResponse.setShowProgress(isProgress);
    }

    public void chatHistoryAPI(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<ChatHistoryResponse> airportResponse  = new CommonVollyClass<>(context, Constants.HOST_URL, ChatHistoryResponse.class, Constants.CHAT_HISTORY,Mode,obj, isProgress);
        airportResponse.setIsApiKeyRequired(false);
        airportResponse.setForFragment(false);
        airportResponse.setShowProgress(isProgress);
    }

    public void pingAPI(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<PingMessageResponse> airportResponse  = new CommonVollyClass<>(context, Constants.HOST_URL, PingMessageResponse.class, Constants.PING_MESSAGE,Mode,obj, isProgress);
        airportResponse.setIsApiKeyRequired(false);
        airportResponse.setForFragment(false);
        airportResponse.setShowProgress(isProgress);
    }

    public void sendMessageAPI(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<BaseResponse> sendResponse  = new CommonVollyClass<>(context, Constants.HOST_URL, BaseResponse.class, Constants.SEND_MSG,Mode,obj, isProgress);
        sendResponse.setIsApiKeyRequired(false);
        sendResponse.setForFragment(false);
        sendResponse.setShowProgress(isProgress);
    }

    public void requestForAvailablity(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<RequestForAvailablityResponse> availablityResponse  = new CommonVollyClass<>(context, Constants.HOST_URL, RequestForAvailablityResponse.class, Constants.REQUEST_AVAILABILITY,Mode,obj, isProgress);
        availablityResponse.setIsApiKeyRequired(false);
        availablityResponse.setForFragment(false);
        availablityResponse.setShowProgress(isProgress);
    }

    public void sharedQuotation1(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<SharedQuotationResponse> sharedQuotationRespnse = new CommonVollyClass<>(context, Constants.HOST_URL, SharedQuotationResponse.class, Constants.SHARED_QUOTATION,Mode,obj, isProgress);
        sharedQuotationRespnse.setIsApiKeyRequired(false);
        sharedQuotationRespnse.setForFragment(false);
        sharedQuotationRespnse.setShowProgress(isProgress);
    }
    public void pendingQuotation1(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<PendingQuotationResponse> PendingQuotationResponse = new CommonVollyClass<>(context, Constants.HOST_URL, PendingQuotationResponse.class, Constants.PENDING_QUOTATION,Mode,obj, isProgress);
        PendingQuotationResponse.setIsApiKeyRequired(false);
        PendingQuotationResponse.setForFragment(false);
        PendingQuotationResponse.setShowProgress(isProgress);
    }

    public void approvedQuotation1(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<ApprovedQuotationResponse> approveQuotationRespnse = new CommonVollyClass<>(context, Constants.HOST_URL, ApprovedQuotationResponse.class, Constants.STATUS_QUOTATION,Mode,obj, isProgress);
        approveQuotationRespnse.setIsApiKeyRequired(false);
        approveQuotationRespnse.setForFragment(false);
        approveQuotationRespnse.setShowProgress(isProgress);
    }

    public void aircraftLoadType(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<AircraftLoadTypeResponse> aircraftLoadTypeRespnse = new CommonVollyClass<>(context, Constants.HOST_URL, AircraftLoadTypeResponse.class, Constants.GET_AIRCRAFT_LOAD_TYPE,Mode,obj, isProgress);
        aircraftLoadTypeRespnse.setIsApiKeyRequired(false);
        aircraftLoadTypeRespnse.setForFragment(false);
        aircraftLoadTypeRespnse.setShowProgress(isProgress);
    }

    public void availableAircraft(Context context, CommonBaseFragment fag, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<AvailableAircraftResponse> AvailableAircraftResponse = new CommonVollyClass<>(context, Constants.HOST_URL, AvailableAircraftResponse.class, Constants.AVAILABLE_AIRPORT,Mode,obj, isProgress);
        AvailableAircraftResponse.setIsApiKeyRequired(false);
        AvailableAircraftResponse.setForFragment(true);
        AvailableAircraftResponse.setFragment(fag);
        AvailableAircraftResponse.setShowProgress(isProgress);
    }

    public void countQuotationHistory(Context context, CommonBaseFragment fag, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<CountQuotationHistoryResponse> AvailableAircraftResponse = new CommonVollyClass<>(context, Constants.HOST_URL, CountQuotationHistoryResponse.class, Constants.COUNT_QUOTATION,Mode,obj, isProgress);
        AvailableAircraftResponse.setIsApiKeyRequired(false);
        AvailableAircraftResponse.setForFragment(true);
        AvailableAircraftResponse.setFragment(fag);
        AvailableAircraftResponse.setShowProgress(isProgress);
    }

    public void pendingQuotation(Context context, CommonBaseFragment fag, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<PendingQuotationResponse> PendingQuotationResponse = new CommonVollyClass<>(context, Constants.HOST_URL, PendingQuotationResponse.class, Constants.PENDING_QUOTATION,Mode,obj, isProgress);
        PendingQuotationResponse.setIsApiKeyRequired(false);
        PendingQuotationResponse.setForFragment(true);
        PendingQuotationResponse.setFragment(fag);
        PendingQuotationResponse.setShowProgress(isProgress);
    }

    public void sharedQuotation(Context context, CommonBaseFragment fag, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<SharedQuotationResponse> sharedQuotationRespnse = new CommonVollyClass<>(context, Constants.HOST_URL, SharedQuotationResponse.class, Constants.SHARED_QUOTATION,Mode,obj, isProgress);
        sharedQuotationRespnse.setIsApiKeyRequired(false);
        sharedQuotationRespnse.setForFragment(true);
        sharedQuotationRespnse.setFragment(fag);
        sharedQuotationRespnse.setShowProgress(isProgress);
    }

    public void approvedQuotation(Context context, CommonBaseFragment fag, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<ApprovedQuotationResponse> approveQuotationRespnse = new CommonVollyClass<>(context, Constants.HOST_URL, ApprovedQuotationResponse.class, Constants.STATUS_QUOTATION,Mode,obj, isProgress);
        approveQuotationRespnse.setIsApiKeyRequired(false);
        approveQuotationRespnse.setForFragment(true);
        approveQuotationRespnse.setFragment(fag);
        approveQuotationRespnse.setShowProgress(isProgress);
    }

    public void changeQuotation(Context context, String Mode, JSONObject obj , boolean isProgress){
        CommonVollyClass<ChangeQuoatationResponse> availablityResponse  = new CommonVollyClass<>(context, Constants.HOST_URL, ChangeQuoatationResponse.class, Constants.CHNAGE_QUOTATION,Mode,obj, isProgress);
        availablityResponse.setIsApiKeyRequired(false);
        availablityResponse.setForFragment(false);
        availablityResponse.setShowProgress(isProgress);
    }
}

package com.truxapp.fbfire.app;

public class Constants {
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
    public static final String CONFIRM_ORDER_RESPONCE = "Confirm Chat";
    public static final String SAVE_CHAT_HISTORY = "dff";
    public static final String CHAT_LAST_TIME = "";
    public static final String splashValue = "false";

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10;//*60*60;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public static final int REQUEST_COARSE_LOCATION = 10;

}
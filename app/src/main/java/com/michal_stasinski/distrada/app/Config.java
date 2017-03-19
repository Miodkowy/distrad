package com.michal_stasinski.distrada.app;

/**
 * Created by win8 on 02.01.2017.
 */

public class Config {
    // global topic to receive app wide push notifications
  // public  static final String TOPIC_GLOBAL = "global";
    public static final String TOPIC_GLOBAL = "swift_fans_distrada64";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "miodkowy_firebase";
}

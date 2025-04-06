package com.anaylitics.test;

import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.twinnet_analytics.notification.MyFirebaseMessagingService;


public class FMService extends FirebaseMessagingService {

    private MyFirebaseMessagingService myFirebaseMessagingService;

    @Override
    public void onCreate() {
        super.onCreate();
        myFirebaseMessagingService = new MyFirebaseMessagingService();
        myFirebaseMessagingService.init(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        myFirebaseMessagingService.onMessageReceived(message);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        myFirebaseMessagingService.onNewToken(token);
    }
}

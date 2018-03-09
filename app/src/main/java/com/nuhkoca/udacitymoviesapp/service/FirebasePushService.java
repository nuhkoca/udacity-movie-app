package com.nuhkoca.udacitymoviesapp.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

/**
 * Created by nuhkoca on 3/9/18.
 */

public class FirebasePushService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Timber.d(remoteMessage.getMessageId());
    }
}
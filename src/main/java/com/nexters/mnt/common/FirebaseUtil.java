package com.nexters.mnt.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class FirebaseUtil {


    public FirebaseUtil(@Value("${fcm.path}") String fcmJsonPath) {
        try {
            log.info(fcmJsonPath);
            FileInputStream serviceAccount = new FileInputStream(fcmJsonPath);

            log.info("path : " + fcmJsonPath);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://manito-1650f.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFcmUserToUser(String tokenId, String title, String content){

        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.NORMAL)
                        .setNotification(AndroidNotification.builder()
                                .setTitle(title)
                        .setBody(content)
                        .setColor("#f45342")
                        .build())
                        .build())
                .setToken(tokenId)
                .build();
        try {
            log.info(FirebaseMessaging.getInstance().send(message));
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendFcmToAll(List<String> tokenIds, String title, String content){

        MulticastMessage message = MulticastMessage.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .setNotification(AndroidNotification.builder()
                                .setTitle(title)
                                .setBody(content)
                                .setColor("#f45342")
                                .build())
                        .build())
                .addAllTokens(tokenIds)
                .build();
        try {
            log.info(FirebaseMessaging.getInstance().sendMulticast(message).toString());
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}

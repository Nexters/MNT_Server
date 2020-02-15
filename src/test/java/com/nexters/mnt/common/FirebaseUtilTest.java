package com.nexters.mnt.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirebaseUtilTest {

    @Autowired
    FirebaseUtil firebaseUtil;

    @Test
    void sendFcmUserToUser() {

        firebaseUtil.sendFcmUserToUser("c2kbP0XvAyA:APA91bEWgOxKDJyXM4XJx_sSIa0HBP23u3tZL5b9RdFeuLCHSsUUDOxbEz5sSKkQiUOkMpbba1QxgeMr0j4DoZl9AQYQiyogRFpybiDCNn7UfhvdDZtFYlnem7xT7yAAxme8ZImrtd05","test", "test");
    }
}
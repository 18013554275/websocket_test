package com.hs.websocket_test.configration;

import com.hs.websocket_test.entity.MemberMessage;
import com.hs.websocket_test.point.MessageHandleInterface;
import com.hs.websocket_test.point.WebConnectionManager;
import com.hs.websocket_test.point.WebConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
public class MessageHandleConfiguration {

    public MessageHandleConfiguration(){
        WebConnectionManager.getInstance().setMessageHandle(new MessageHandleInterface() {
            @Override
            public void sendReLoginMessage(WebConnector oldConnector) {

            }

            @Override
            public void msgHandle(MemberMessage memberMessage) {

            }
        });

    }

}

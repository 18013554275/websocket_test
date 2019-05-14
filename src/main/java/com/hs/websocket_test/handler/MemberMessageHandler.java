package com.hs.websocket_test.handler;

import com.hs.websocket_test.entity.MemberMessage;
import com.hs.websocket_test.exception.ServerSqlErrorException;
import com.hs.websocket_test.point.MessageHandleInterface;
import com.hs.websocket_test.point.WebConnectionManager;
import com.hs.websocket_test.point.WebConnector;
import com.hs.websocket_test.service.impl.MemberMessageService;
import javax.annotation.Resource;

public class MemberMessageHandler implements MessageHandleInterface {
    @Resource
    private MemberMessageService memberMessageService;
    @Override
    public void sendReLoginMessage(WebConnector oldConnector) {

    }

    @Override
    public void msgHandle(MemberMessage message) {
        try {
            memberMessageService.save(message);
            WebConnectionManager.getInstance().sendMessage(message);
        } catch (ServerSqlErrorException e) {
            e.printStackTrace();
        }


    }
}

package com.hs.websocket_test.point;

import com.hs.websocket_test.entity.MemberMessage;

import java.util.Map;

public interface MessageHandleInterface {
    public void sendReLoginMessage(WebConnector oldConnector);
    public void msgHandle(MemberMessage memberMessage);
}

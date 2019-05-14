package com.hs.websocket_test.point;

public interface UpdateListener {
	
	void updateHandle(String message);
	
	String getUid();

}

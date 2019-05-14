package com.hs.websocket_test;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class WebServiceTest {

    @WebMethod
    public String getInfo(){
        System.out.println("发布消息");
        return "你好啊！";
    }


    public static void main(String[] args) {
        Object implementor = new WebServiceTest();
        String address = "http://localhost:8989/sayhello";     //发布到的地址
        Endpoint.publish(address, implementor);
        System.out.println("发布成功");
    }
}

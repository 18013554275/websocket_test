/***
 * 用户的信息，该类主要用于封装用户的信息，包括用户id、session、角色、消息类型（个人消息或群消息）、消息内容等；
 */
package com.hs.websocket_test.entity;

import java.io.Serializable;
import java.util.Map;

public class MemberMessage implements Serializable {

    private Long id;    //消息id
    private Long senderId;     //消息发送者id
    private Long receiverId;   //消息接收者id
    private Integer senderRole;   //发送者角色
    private Integer receiverRole;   //发送者角色
    private String type;  //personal个人消息，group群消息
    private String classify;  //消息分类，text是文字消息，image是图片消息，voice是语音消息，video是视频消息，event是事件消息
    private String content;    //消息内容
    private Map infoMap;          //其他信息，放在map中

    public Map getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map infoMap) {
        this.infoMap = infoMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(Integer senderRole) {
        this.senderRole = senderRole;
    }

    public Integer getReceiverRole() {
        return receiverRole;
    }

    public void setReceiverRole(Integer receiverRole) {
        this.receiverRole = receiverRole;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

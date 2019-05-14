package com.hs.websocket_test.point;


import com.hs.websocket_test.entity.MemberMessage;
import com.hs.websocket_test.util.JsonUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebConnectionManager {

	private static WebConnectionManager instance = new WebConnectionManager();

	private Map<Integer, Map<Long, WebConnector>> connectorMap;  //储存通过websocket连接的用户

	private WebConnectionManager() {
		connectorMap = new HashMap<>();
	}

	private MessageHandleInterface messageHandle;    //消息处理接口，由系统自定义

	public static WebConnectionManager getInstance() {
		return instance;
	}

	/**
	 * 由系统调用
	 * @param messageHandle
	 */
	public void setMessageHandle(MessageHandleInterface messageHandle){
		this.messageHandle = messageHandle;
	}

	/**
	 * 用户登录，将登录信息存入Map集合中，也可以存入redis缓存中
	 * @param connector 用户的登录信息，包含角色、id和session
	 */
	public void addConneector(WebConnector connector){
		/*
		   需要先判断该用户是否存在，如果存在则说明有两个用户端在登录同一个账号，
		   之前登录的用户被挤掉，后台发送掉线通知
		*/
		WebConnector oldConnector = isExist(connector);
		if(oldConnector != null){
			synchronized (oldConnector){
				//发送掉线通知
				sendReLoginMessage(oldConnector);
				connector.close();
			}
		}
		//新增用户
		Map map = connectorMap.get(connector.getRole());
		//判断该角色的map是否存在，如果不存在则需要新建
		if(map == null){
			map = new HashMap<Long, WebConnector>();
			synchronized (connectorMap) {
				if(connectorMap.get(connector.getRole())==null){
					connectorMap.put(connector.getRole(), map);
				}
			}
		}
		//将用户添加到map中完成登录
		map.put(connector.getId(), connector);

	}

	/**
	 * 移除用户
	 * @param connector
	 */
	public void removeConnector(WebConnector connector){
		synchronized (connector){
			if(connector == null){
				return;
			}
			Map map = connectorMap.get(connector.getRole());
			synchronized (map) {
				if(map == null){
					return;
				}
				if(map.get(connector.getId()) == null){
					return;
				}
				map.remove(connector.getId());
			}
		}
	}

	/**
	 * 发送异地登录信息,系统可以自定义，不定义的话则调用默认方法
	 * @param oldConnector
	 */
	private void sendReLoginMessage(WebConnector oldConnector){
		if(messageHandle != null){
			messageHandle.sendReLoginMessage(oldConnector);
		}
	}

	/**
	 * 判断用户是否存在,如果存在则返回原来的对象
	 * @param connector
	 * @return
	 */
	private WebConnector isExist(WebConnector connector) {
		Map map = connectorMap.get(connector.getRole());
		if(map != null && map.get(connector.getId()) != null){
			return (WebConnector)map.get(connector.getId());
		}
		return null;
	}

	private WebConnector getConnector(Integer role, Long id){
		if(id == null){
			return null;
		}
		Map map = connectorMap.get(role);
		if(map == null){
			return null;
		}
		return (WebConnector)map.get(id);
	}

    /**
     * 发送消息，外部统一调用的方法
     * @param message   消息信息，包括接收者、发送者、消息类型等信息
     * @param data    系统自己添加的信息
     */
	public void sendMessage(MemberMessage message, Map data){
	    data.put("$_sys_message", message);
	    if("personal".equals(message.getType())){
	        WebConnector receiver = getConnector(message.getReceiverRole(), message.getReceiverId());
	        sendMessage(receiver, data);
        }
    }

    public void sendMessage(MemberMessage message){
        sendMessage(message, new HashMap());
    }

	/**
	 * 给单一用户发送消息
	 * @param receiver
	 * @param data
	 */
	private void sendMessage(WebConnector receiver, Map data) {
		String msgJson = JsonUtil.toJson(data);
        receiver.updateHandle(msgJson);
	}

	/**
	 * 给多个用户发送群消息
	 * @param message
	 * @param connectorList
	 */
	private void sendMessage(String message, List<WebConnector> connectorList){
		synchronized (connectorList) {
			for(WebConnector connector: connectorList){
				MemberMessage msg = new MemberMessage();
				msg.setContent(message);
				msg.setId(connector.getId());
				msg.setReceiverRole(connector.getRole());
				msg.setType("group");
				String msgJson = JsonUtil.toJson(msg);
				connector.updateHandle(msgJson);
			}

		}
	}

	/**
	 * 处理接收到的信息，由系统定义，可以发送给其他人，也可以存入数据库
	 * @param memberMessage
	 */
	private void msgHandle(MemberMessage memberMessage){
		if(messageHandle != null){
			messageHandle.msgHandle(memberMessage);
		}else{
			sendMessage(memberMessage);
		}
	}

	/**
	 * 服务端接收信息，并解析信息
	 * @param msgJson  json字符串，信息包含：type:消息类型，senderId:发送者id，sendRole:发送者角色，content:消息内容，
	 * @return
	 */
	public MemberMessage getMessage(String msgJson, WebConnector sender) {
		if(msgJson == null){
			return null;
		}
		MemberMessage memberMessage = (MemberMessage) JsonUtil.json2Object(msgJson, MemberMessage.class);
		if(memberMessage == null){
			return null;
		}
		Integer role = memberMessage.getReceiverRole();
		Long id = memberMessage.getReceiverId();
		WebConnector receiver = getConnector(role, id);
		msgHandle(memberMessage);
		return memberMessage;
	}
	
	public static void main(String[] args) {
	}
	

}

package com.hs.websocket_test.point;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@ServerEndpoint(value = "/monitor/{role}/{id}")
@Component
public class MonitorServerEndpoint {
	
	private Session targetSession;
	private WebConnector connector;
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50, 50, 2, TimeUnit.SECONDS,
            new LinkedBlockingQueue(), new ThreadPoolExecutor.AbortPolicy());    //处理信息交互的线程池
	private static final Logger sysLogger = Logger.getLogger("sysLog");

    /**
     * 用户的所有的信息操作都在线程池中进行
     * @param session
     * @param role 角色
     * @param id   用户系统id
     */
	@OnOpen
	public void open(Session session, @PathParam("role")int role, @PathParam("id")Long id) {

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                targetSession = session;
                connector = new WebConnector(targetSession, role, id);
                WebConnectionManager.getInstance().addConneector(connector);
                sysLogger.info("*** WebSocket opened from sessionId " + targetSession.getId());
            }
        });

	}
	
	@OnMessage
	public void inMessage(String message) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                WebConnectionManager.getInstance().getMessage(message, connector);
            }
        });

    }
	
	@OnClose
	public void end() {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                WebConnectionManager.getInstance().removeConnector(connector);
                sysLogger.info("*** WebSocket closed from sessionId " + targetSession.getId());
            }
        });
	}


	/*private synchronized void sendMessage(String message){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    targetSession.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    sysLogger.warning(e.getMessage());
                } catch(IllegalStateException e){
                    sysLogger.warning(e.getMessage());
                }
            }
        });

	}*/

}

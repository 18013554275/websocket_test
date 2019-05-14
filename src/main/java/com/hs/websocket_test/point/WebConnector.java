package com.hs.websocket_test.point;

import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

public class WebConnector implements Serializable,UpdateListener {

    private Session session;  //
    private Integer role;     //用户角色
    private Long id;          //用户的系统登录id
    private static final Logger sysLogger = Logger.getLogger("sysLog");

    public WebConnector(Session session){
        this.session = session;
    }

    public WebConnector(Session session, Integer role, Long id){
        this(session);
        this.role = role;
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void close(){
        try {
            if(session != null){
                session.close();
            }
        } catch (IOException e) {
            sysLogger.warning(e.getMessage());
        }
    }

    @Override
    public void updateHandle(String message) {
        if(session == null){
            return;
        }
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            sysLogger.warning(e.getMessage());
        } catch(IllegalStateException e){
            sysLogger.warning(e.getMessage());
        }
    }

    @Override
    public String getUid() {
        return session.getId();
    }
}

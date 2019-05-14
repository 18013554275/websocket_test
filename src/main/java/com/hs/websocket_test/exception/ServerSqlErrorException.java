package com.hs.websocket_test.exception;

import org.jboss.logging.Logger;

/**
 * Created by ShiYuqun_PC on 2017/7/18 0018.
 */
public class ServerSqlErrorException extends Exception {
    protected Logger logger = Logger.getLogger(this.getClass());
    public ServerSqlErrorException(Exception e){
        System.out.println("\n=====================数据库操作异常=====================\n");
        logger.info(e.toString(),e);
    }
}

package com.hs.websocket_test.dao.impl;

import com.hs.websocket_test.dao.BaseDao;
import com.hs.websocket_test.entity.MemberMessage;
import org.springframework.stereotype.Repository;

@Repository
public class MemberMessageDao extends BaseDao<MemberMessage> {
    @Override
    protected String getNamespace() {
        return "MemberMessageMapper";
    }
}

package com.hs.websocket_test.dao;

import java.util.List;

import javax.annotation.Resource;

import com.hs.websocket_test.exception.ServerSqlErrorException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("daoSupport")
public class DaoSupport implements DAO {

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 保存对象
     * @param str
     * @param obj
     * @return
     * @throws ServerSqlErrorException
     */
    public Object save(String str, Object obj) throws ServerSqlErrorException {
        try{
            return sqlSessionTemplate.insert(str, obj);
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }
    }

    /**
     * 批量插入
     * @param str
     * @param objs
     * @return
     * @throws ServerSqlErrorException
     */
    public Object batchSave(String str, List objs )throws ServerSqlErrorException{
        try{
            return sqlSessionTemplate.insert(str, objs);
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }
    }

    /**
     * 修改对象
     * @param str
     * @param obj
     * @return
     * @throws ServerSqlErrorException
     */
    public Object update(String str, Object obj) throws ServerSqlErrorException {
        try{
            return sqlSessionTemplate.update(str, obj);
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }
    }

    /**
     * 批量更新
     * @param str
     * @param objs
     * @return
     * @throws ServerSqlErrorException
     */
    public void batchUpdate(String str, List objs )throws ServerSqlErrorException{
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        //批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try{
            if(objs!=null){
                for(int i=0,size=objs.size();i<size;i++){
                    sqlSession.update(str, objs.get(i));
                }
                sqlSession.flushStatements();
                sqlSession.commit();
                sqlSession.clearCache();
            }
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }finally{
            sqlSession.close();
        }
    }

    /**
     * 批量更新
     * @param str
     * @param objs
     * @return
     * @throws ServerSqlErrorException
     */
    public Object batchDelete(String str, List objs )throws ServerSqlErrorException{
        try{
            return sqlSessionTemplate.delete(str, objs);
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }
    }

    /**
     * 删除对象
     * @param str
     * @param obj
     * @return
     * @throws ServerSqlErrorException
     */
    public Object delete(String str, Object obj) throws ServerSqlErrorException {
        try{
            return sqlSessionTemplate.delete(str, obj);
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }
    }

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws ServerSqlErrorException
     */
    public Object findForObject(String str, Object obj) throws ServerSqlErrorException {
        try{
            return sqlSessionTemplate.selectOne(str, obj);
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }
    }

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws ServerSqlErrorException
     */
    public Object findForList(String str, Object obj) throws ServerSqlErrorException {
        try{
            return sqlSessionTemplate.selectList(str, obj);
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }
    }

    public Object findForMap(String str, Object obj, String key, String value) throws ServerSqlErrorException {
        try{
            return sqlSessionTemplate.selectMap(str, obj, key);
        }catch (Exception e){
            throw new ServerSqlErrorException(e);
        }
    }

}



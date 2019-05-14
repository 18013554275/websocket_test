package com.hs.websocket_test.service.base;

import java.util.List;

import com.hs.websocket_test.dao.BaseDao;
import com.hs.websocket_test.exception.ServerSqlErrorException;
import com.hs.websocket_test.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseService<T> implements IBaseService<T> {


    @Autowired
    private BaseDao<T> baseDao;

    /*
    * 新增
    */
    public void save(T entity)throws ServerSqlErrorException {
        baseDao.save(entity);
    }

    /*
     * 批量新增
     */
    public void saveDataBatch(List<T> entityList)throws ServerSqlErrorException {
    	baseDao.saveBatch(entityList);
    }
    
    /*
    * 物理删除
    */
    public void delete(Long id)throws ServerSqlErrorException{
        baseDao.delete(id);
    }

    /*
    * 逻辑删除
    */
    public void deleteLogical(Long id)throws ServerSqlErrorException{
        baseDao.deleteLogical(id);
    }

    /*
    * 批量物理删除
    */
    public void deleteBatch(String[] ids)throws ServerSqlErrorException{
        baseDao.deleteBatch(ids);
    }

    /*
    * 批量逻辑删除
    */
    public void deleteLogicalBatch(String[] ids)throws ServerSqlErrorException {
        baseDao.deleteLogicalBatch(ids);
    }

    /*
    * 修改
    */
    public void update(T entity)throws ServerSqlErrorException{
        baseDao.update(entity);
    }

    /*
    *列表
    */
    /*public List<T> list(Page page)throws ServerSqlErrorException{
        return baseDao.list(page);
    }*/

    /*
    *列表(全部)
    */
    public List<T> listAll()throws ServerSqlErrorException{

        return baseDao.listAll();
    }

    /*
    * 通过id获取数据
    */
    public T getById(Long id)throws ServerSqlErrorException{
        return baseDao.getById(id);
    }

    /*
     * 通过条件获取数据
     */
    public List<T> listByCondition(T t)throws ServerSqlErrorException{
        return baseDao.listByCondition(t);
    }

    /*
     * 通过条件获取数据
     */
    public T findByUniqueCondition(T t)throws ServerSqlErrorException{
        List<T> list = listByCondition(t);
        if(list == null || list.size() == 0)
            return null;

        return list.get(0);
    }

    /*
    * 批量删除
    */
    public void deleteBatch(Object[] ids)throws ServerSqlErrorException{
        baseDao.deleteBatch(ids);
    }

}

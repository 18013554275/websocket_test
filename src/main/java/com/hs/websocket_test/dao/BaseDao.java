package com.hs.websocket_test.dao;

import java.util.List;

import javax.annotation.Resource;
import com.hs.websocket_test.exception.ServerSqlErrorException;

public abstract class BaseDao<T> implements IBaseDao<T>{
	
	@Resource(name = "daoSupport")
	protected DaoSupport dao;
	
	protected abstract String getNamespace();
	
	/*
	* 新增
	*/
	public void save(T entity)throws ServerSqlErrorException {
		dao.save(getNamespace() + ".save", entity);
	}
	
	/*
	 *批量新增 
	 */
	public void saveBatch(List<T> list)throws ServerSqlErrorException{
		dao.batchSave(getNamespace()+ ".saveBatch", list);
	}
	
	/*
	* 物理删除
	*/
	public void delete(Long id)throws ServerSqlErrorException{
		dao.delete(getNamespace() + ".delete", id);
	}

	/*
	* 逻辑删除
	*/
	public void deleteLogical(Long id)throws ServerSqlErrorException{
		dao.update(getNamespace() + ".deleteLogical", id);
	}
	
	/*
	* 修改
	*/
	public void update(T entity)throws ServerSqlErrorException{
		dao.update(getNamespace() + ".update", entity);
	}
	
	/*
	*列表
	*/
	/*public List<T> list(Page page)throws ServerSqlErrorException{
		return (List<T>)dao.findForList(getNamespace() + ".dataListPage", page);
	}*/
	
	/*
	*列表(全部)
	*/
	@SuppressWarnings("unchecked")
	public List<T> listAll()throws ServerSqlErrorException{
		return (List<T>)dao.findForList(getNamespace() + ".listAll", null);
	}
	
	/*
	* 通过id获取数据
	*/
	@SuppressWarnings("unchecked")
	public T getById(Long id)throws ServerSqlErrorException{
		return (T)dao.findForObject(getNamespace() + ".findById", id);
	}
	
	/*
	 * 通过条件获取数据
	 */
	@SuppressWarnings("unchecked")
	public List<T> listByCondition(T t)throws ServerSqlErrorException{
		return (List<T>)dao.findForList(getNamespace() + ".findByCondition", t);
	}
	
	/*
	 * 通过条件获取数据
	 */
	public T getByUniqueCondition(T t)throws ServerSqlErrorException{
		List<T> list = listByCondition(t);
		if(list == null || list.size() == 0)
			return null;
		
		return list.get(0);
	}

    /*
    * 批量逻辑删除
    */
    public void deleteLogicalBatch(String[] ids)throws ServerSqlErrorException {
        dao.update(getNamespace() + ".deleteLogicalBatch", ids);
    }

	/*
	* 批量物理删除
	*/
	public void deleteBatch(Object[] ids)throws ServerSqlErrorException{
		dao.delete(getNamespace() + ".deleteBatch", ids);
	}
}

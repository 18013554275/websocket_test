package com.hs.websocket_test.dao;

import java.util.List;

import com.hs.websocket_test.exception.ServerSqlErrorException;
import org.springframework.stereotype.Component;


@Component
public interface IBaseDao<T> {
	
	/*
	* 新增
	*/
	public void save(T entity)throws ServerSqlErrorException;
	
	/*
	* 物理删除
	*/
	public void delete(Long id)throws ServerSqlErrorException;

	/*
	* 逻辑删除
	*/
	public void deleteLogical(Long id)throws ServerSqlErrorException;
	
	/*
	* 修改
	*/
	public void update(T entity)throws ServerSqlErrorException;
	
	/*
	*列表
	*/
	/*public List<T> list(Page page)throws ServerSqlErrorException;*/
	
	/*
	*列表(全部)
	*/
	public List<T> listAll()throws ServerSqlErrorException;
	
	/*
	* 通过id获取数据
	*/
	public T getById(Long id)throws ServerSqlErrorException;
	
	/*
	 * 通过条件获取数据
	 */
	public List<T> listByCondition(T t)throws ServerSqlErrorException;
	
	/*
	 * 通过条件获取数据
	 */
	public T getByUniqueCondition(T t)throws ServerSqlErrorException;

    /*
    * 批量逻辑删除
    */
    public void deleteLogicalBatch(String[] ids)throws ServerSqlErrorException;

	/*
	* 批量物理删除
	*/
	public void deleteBatch(Object[] ids)throws ServerSqlErrorException;

}

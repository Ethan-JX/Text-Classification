package com.ethan.basic;

import java.util.List;
import java.util.Map;


/**
 * 实现的basicDao 继承spring-data-jpa
 * @param <T>
 */
public interface BasicDao<T>{
	/**
	 * 使用原生SQL查询数据库
	 * @param sql SQL语句
	 * @return 返回List对象
	 * @throws
	 */
	public List<?> queryByNativeSQL(String sql) ;
	/**
	 * 使用原生SQL查询数据库
	 */
	public List<?> queryByNativeSQL(String sql, Object[] paramArray, int start, int pageSize);

	/**
	 * 使用原生SQL查询数据库
	 * @param sql SQL语句
	 * @param clazz 查询结果转换的对象（为空则为Map）
	 * @return 返回List对象
	 * @throws
	 */
	public List<?> queryByNativeSQL(String sql, Class<?> clazz);
	/**
	 * 使用原生SQL查询数据库
	 * @param sql SQL语句
	 * @param paramArray 参数
	 * @return 返回List对象
	 * @throws
	 */
	public List<?> queryByNativeSQL(String sql, Object[] paramArray);
	/**
	 * 使用原生SQL查询数据库
	 * @param sql SQL语句
	 * @param clazz 查询结果转换的对象（为空则为Map）
	 * @param paramArray 参数
	 * @return 返回List对象
	 * @throws
	 */
	public List<?> queryByNativeSQL(String sql, Class<?> clazz, Object[] paramArray);
	/**
	 * 执行原生SQL(不带参数)
	 */
	public int executeNativeSQL(String sql) ;
	/**
	 * 执行原生SQL(带参数)
	 */
	public int executeNativeSQL(String sql, Object[] paramArray) ;

	/**
	 * 批量执行原生sql
	 * @param sqllist
	 * @throws
	 */
	public void executeSQL(List<String> sqllist) ;

	//**********************************************************************************************************************************
	/**
	 * 取得实体类的类名
	 * @author Chen xiaosheng
	 * @return
	 */
	public Class<T> getEntityClass();

	/**
	 *@author Chen xiaosheng
	 * 在deleteEntity实现刪除
	 */
	public <T> void deleteEntity(T t) ;

	/**
	 * 根据Id执行删除操作
	 * @author Chen xiaosheng
	 * @param id
	 * @throws
	 */
	public void deleteEntityById(Object id)  ;

	/**
	 *@author Chen xiaosheng
	 * 在updateEntity实现插入和更新
	 */
	public void updateEntity(T entity ) ;

	/**
	 *@author Chen xiaosheng
	 * 在querryAll调用queryList()来查询对象所有记录(不分页)
	 */
	public List queryAll()  ;

	/**
	 * 查找对象所有记录列表
	 * @return
	 * @throws
	 */
	public List<T> queryList()  ;

	/**
	 * 查找对象所有记录列表(分页)
	 * @param start 记录开始
	 * @param pageSize 记录范围
	 * @return
	 * @throws
	 */
	public List<T> queryList(int start, int pageSize)  ;

	/**
	 * 根据Id查找对象
	 * @param id
	 * @return
	 * @throws
	 */
	public T querryById(Object id)  ;

	/***
	 * 关键字查询
	 * @throws
	 */
	public List<T> queryByKeyWords(Map<String,Object> paramList);

	/***
	 * 关键字删除
	 * @throws
	 */
	public void deleteByKeyWords(Map<String, Object> paramList);

	/**
	 * 查询对象总记录数
	 * @return
	 * @throws
	 */
	public int queryCount()  ;

	/**
	 * 关键字模糊查询
	 * @param compareEntity
	 * @param useLike
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<T> queryList(T compareEntity, boolean useLike, int start, int pageSize) ;

}
package com.ethan.basic.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ethan.basic.BasicDao;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.transform.Transformers;

@SuppressWarnings("all")
public abstract class BasicDaoImpl<T> implements BasicDao<T> {
    private final String SQL_ERRO = "sql语句：%s 出现异常";
    private final String SQL_ERRO2 = "sql语句出现异常";
    private final String UPDATE_ERRO="更新/保存数据到数据库的操作出现异常";
    private final String DELETE_ERRO="删除操作出现异常";
    private final String QUERY_ERRO="查询操作出现异常";
    private List list = null;
    private int intnumber;

    @PersistenceContext
    private EntityManager em;

    public List queryByNativeSQL(String sql) {
        try {
            list = queryByNativeSQL(sql, null, null);
        } catch (Exception e) {
        }
        return list;
    }

    public List queryByNativeSQL(String sql, Class clazz) {
        try {
            list = queryByNativeSQL(sql, clazz, null);
        } catch (Exception e) {
        }
        return list;
    }

    public List queryByNativeSQL(String sql, Object[] paramArray) {
        try {
            list = queryByNativeSQL(sql, null, paramArray);
        } catch (Exception e) {
        }
        return list;
    }

    public List queryByNativeSQL(String sql, Class clazz, Object[] paramArray) {
        Query query;
        if (clazz != null) {
            query = em.createNativeQuery(sql, clazz);
        } else {
            query = em.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        if (paramArray != null && paramArray.length > 0) {
            for (int i = 0; i < paramArray.length; i++) {
                query.setParameter(i, paramArray[i]);
            }
        }
        try {
            list = query.getResultList();
        } catch (Exception e) {
        }
        return list;
    }

    public List queryByNativeSQL(String sql, Object[] paramArray, int start, int pageSize) {
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (paramArray != null && paramArray.length > 0) {
            for (int i = 0; i < paramArray.length; i++) {
                query.setParameter(i, paramArray[i]);
            }
        }
        if (start >= 0 && pageSize > 0) {
            query.setFirstResult(start).setMaxResults(pageSize);
        }
        try {
            list = query.getResultList();
        } catch (Exception e) {
        }
        return list;
    }

    public int executeNativeSQL(String sql) {
        try {
            intnumber = executeNativeSQL(sql, null);
        } catch (Exception e) {
        }
        return intnumber;
    }

	public int executeNativeSQL(String sql, Object[] paramArray) {
		Query query = em.createNativeQuery(sql);
		if (paramArray != null && paramArray.length > 0) {
			for (int i = 0; i < paramArray.length; i++) {
				query.setParameter(i, paramArray[i]);
			}
		}
		return query.executeUpdate();
	}

    public void executeSQL(List<String> sqllist) {
        try {
            Session session = (Session) em.getDelegate();
            SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
            Connection conn = sessionFactory.getConnectionProvider().getConnection();
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);
            for (String sql : sqllist) {
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            conn.commit();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        }

    }

    //**********************************************************************************************************************************

    /**
     * 每个继承BasicDaoImpl的子类，都必须覆盖此方法，返回该子类的字节码
     *
     * @return
     * @author Chen xiaosheng
     */
    @Override
    public abstract Class<T> getEntityClass();

    /**
     * @author Chen xiaosheng
     * 实体转换（个性化设值）
     */
    protected T translation(T entity) {
        return entity;
    }

    /**
     * @author Chen xiaosheng
     * 在deleteEntity实现刪除
     */
    public <T> void deleteEntity(T t) {
        try {
            em.remove(em.merge(t));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @author Chen xiaosheng
     * 在deleteEntityById根据id来刪除
     */
    public void deleteEntityById(Object id){
        try {
            T entity = em.find(this.getEntityClass(), id);
            if (entity != null) {
                deleteEntity(entity);
            }
        }catch (Exception e){
        }

    }

    /**
     * @author Chen xiaosheng
     * 在updateEntity实现插入和更新
     */
    public void updateEntity(T entity){
        try {
            em.merge(entity);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<T> queryList(){
        try {
            list = queryList(null, false, -1, -1);
        }catch (Exception e){
        }
        return list;
    }

    public List<T> queryAll() {
        try {
            list = queryList();
        }catch (Exception e){
        }
        return list;
    }

    public T querryById(Object id){
        T t = null;
        try {
            t = translation(em.find(getEntityClass(), id));
        }catch (Exception e){
        }
        return t;

    }

    public List<T> queryList(int start, int pageSize){
        try {
           list = queryList(null, false, start, pageSize);
        }catch (Exception e){
        }
        return list;
    }

    public List<T> queryByKeyWords(Map<String, Object> paramList) {
        try {
            StringBuffer hql = new StringBuffer("select t from " + getEntityClass().getSimpleName() + " t ");

            Query query = mapParamsList2Query(hql, paramList);
            if (query.getResultList() != null && query.getResultList().size() > 0) {
                return query.getResultList();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    public void deleteByKeyWords(Map<String, Object> paramList) {
        StringBuffer hql = new StringBuffer("delete from " + getEntityClass().getSimpleName() + " t ");
        Query query = mapParamsList2Query(hql, paramList);
        query.executeUpdate();
    }

    /**
     * 组装总数查询
     */
    public int queryCount(){
        try {
            Query tq = buildQuery(null, false, "select count(1)", null);
            intnumber = Integer.valueOf(tq.getSingleResult().toString());
        }catch (Exception e){
        }
        return intnumber;
    }

    /**
     * 组装列表查询
     */
    @Override
    public List<T> queryList(T compareEntity, boolean useLike, int start, int pageSize){
        try {
            String subSQLFix = null;
            Query tq = buildQuery(compareEntity, useLike, null, subSQLFix);
            //设置需要获取的分页数据
            //start 从第几个开始取数据，pagesize 取几条条数
            if (start >= 0 && pageSize > 0) {
                tq.setFirstResult(start).setMaxResults(pageSize);
            }
            List<T> list = tq.getResultList();
            if (list != null && !list.isEmpty()) {
                for (T t : list)
                    t = translation(t);
            }
            return list;
        }catch (Exception e){
            return new ArrayList<T>();
        }
    }


    /**
     * 组装查询
     */
    protected Query buildQuery(Object entity, boolean useLike, String preSQLFix, String subSQLFix) {
        StringBuffer sql = new StringBuffer();

        sql.append(" from ").append(getEntityClass().getName()).append(" as t where 1=1");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (entity != null) {
            int count = 0;
            for (Field field : entity.getClass().getDeclaredFields()) {
                try {
                    count ++;
                    String fieldName = field.getName();
                    if (Modifier.isFinal(field.getModifiers())//Final类型
                            || Modifier.isFinal(field.getDeclaringClass().getModifiers())) {
                        continue;
                    }
                    if (!Modifier.isPublic(field.getModifiers())//强制读取私有属性
                            || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
                        field.setAccessible(true);
                    }
                    Object obj = field.get(entity);
                    if (obj != null) {
                        if (useLike && String.class.equals(field.getType())) {
                            if(count <= 1){
                                sql.append(" and upper(t.").append(fieldName);
                            }else{
                                sql.append(" or upper(t.").append(fieldName);
                            }
                            sql.append(") like upper(:" + fieldName).append(")");
                            paramMap.put(fieldName, "%" + obj + "%");
                        } else {
                            sql.append(" and t.").append(fieldName);
                            sql.append(" = ");
                            sql.append(":" + fieldName);
                            paramMap.put(fieldName, obj);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        Query q = em.createQuery(sql.toString());
        for (String key : paramMap.keySet()) {
            q.setParameter(key, paramMap.get(key));
        }
        return q;
    }

    public Query mapParamsList2Query(StringBuffer hql, Map<String, Object> paramList) {
        String baseTokenName = "token";
        Map<String, String> tokenList = new HashMap();
        if (paramList != null && paramList.size() > 0) {
            hql.append(" where ");
            int tokenNum = 0;
            String tokenName;
            for (String key : paramList.keySet()) {
                tokenName = baseTokenName + String.valueOf(tokenNum);
                if (tokenNum == 0) {
                    hql.append("  t." + key + "=:" + tokenName);
                } else {
                    hql.append("  and t." + key + "=:" + tokenName);
                }
                tokenList.put(key, tokenName);
                tokenNum++;
            }
        }
        Query query = em.createQuery(hql.toString());
        if (paramList != null && paramList.size() > 0) {
            for (String key : paramList.keySet()) {
                System.out.println(paramList.get(key));
                query.setParameter(tokenList.get(key), paramList.get(key));
            }
        }
        return query;
    }

}

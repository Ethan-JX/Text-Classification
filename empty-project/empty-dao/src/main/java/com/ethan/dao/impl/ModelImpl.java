package com.ethan.dao.impl;

import com.ethan.basic.impl.BasicDaoImpl;
import com.ethan.dao.IModelDao;
import com.ethan.entity.Model;
import org.springframework.stereotype.Repository;

/**
 * Created by Ethan on 2017/3/29.
 */
@Repository(value = "iModelDao")
public class ModelImpl extends BasicDaoImpl<Model> implements IModelDao {
    @Override
    public Class<Model> getEntityClass() {return Model.class;}
}

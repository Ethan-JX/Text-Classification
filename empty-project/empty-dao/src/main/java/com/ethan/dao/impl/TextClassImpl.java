package com.ethan.dao.impl;

import com.ethan.basic.impl.BasicDaoImpl;
import com.ethan.dao.ITextClassDao;
import com.ethan.entity.TextClass;
import org.springframework.stereotype.Repository;

/**
 * Created by Ethan on 2017/3/29.
 */
@Repository(value = "iTextClassDao")
public class TextClassImpl extends BasicDaoImpl<TextClass> implements ITextClassDao{
    @Override
    public Class<TextClass> getEntityClass() {
        return TextClass.class;
    }
}

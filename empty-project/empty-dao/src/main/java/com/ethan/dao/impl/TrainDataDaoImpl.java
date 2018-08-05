package com.ethan.dao.impl;

import com.ethan.basic.impl.BasicDaoImpl;
import com.ethan.dao.ITrainDataDao;
import com.ethan.entity.TrainData;
import org.springframework.stereotype.Repository;

/**
 * Created by Ethan on 2017/3/29.
 */
@Repository(value = "iTrainDataDao")
public class TrainDataDaoImpl extends BasicDaoImpl<TrainData> implements ITrainDataDao{
    @Override
    public Class<TrainData> getEntityClass() {
        return TrainData.class;
    }
}
